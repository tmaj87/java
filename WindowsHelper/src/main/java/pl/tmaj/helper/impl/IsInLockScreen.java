package pl.tmaj.helper.impl;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.*;
import com.sun.jna.platform.win32.WinUser.MSG;
import com.sun.jna.platform.win32.WinUser.WNDCLASSEX;
import com.sun.jna.platform.win32.WinUser.WindowProc;
import com.sun.jna.platform.win32.Wtsapi32;
import lombok.extern.java.Log;
import pl.tmaj.helper.Helper;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.sun.jna.platform.win32.User32.WS_EX_TOPMOST;
import static com.sun.jna.platform.win32.WinUser.WM_SESSION_CHANGE;
import static com.sun.jna.platform.win32.Wtsapi32.*;

@Log
public class IsInLockScreen implements WindowProc, Helper {

    private static final String WINDOW_CLASS = UUID.randomUUID().toString();
    private static final HMODULE H_INST = Kernel32.INSTANCE.GetModuleHandle("");

    public AtomicBoolean locked = new AtomicBoolean();

    @Override
    public void check() {
        registerClassEx();
        HWND handle = createWindow();
        MSG message = new MSG();
        while (User32.INSTANCE.GetMessage(message, handle, 0, 0) != 0) {
            try {
                User32.INSTANCE.TranslateMessage(message);
                User32.INSTANCE.DispatchMessage(message);
            } catch (Exception exception) {
                log.warning(exception.getMessage());
            }
        }
        destroyWindow(handle);
    }

    @Override
    public LRESULT callback(HWND handle, int message, WPARAM wParam, LPARAM lParam) {
        codeCave(message, wParam);
        return User32.INSTANCE.DefWindowProc(handle, message, wParam, lParam);
    }

    private void codeCave(int message, WPARAM wParam) {
        if (message == WM_SESSION_CHANGE) {
            if (wParam.intValue() == WTS_SESSION_LOCK) {
                locked.set(true);
            } else if (wParam.intValue() == WTS_SESSION_UNLOCK) {
                locked.set(false);
            }
        }
    }

    private void registerClassEx() {
        WNDCLASSEX wClass = new WNDCLASSEX();
        wClass.hInstance = H_INST;
        wClass.lpfnWndProc = this;
        wClass.lpszClassName = WINDOW_CLASS;
        User32.INSTANCE.RegisterClassEx(wClass);
    }

    private HWND createWindow() {
        HWND hWnd = User32.INSTANCE.CreateWindowEx(WS_EX_TOPMOST, WINDOW_CLASS, "", 0, 0, 0, 0, 0, null, null, H_INST, null);
        Wtsapi32.INSTANCE.WTSRegisterSessionNotification(hWnd, NOTIFY_FOR_THIS_SESSION);
        log.info("LockScreenListener registered");
        return hWnd;
    }

    private void destroyWindow(HWND hWnd) {
        Wtsapi32.INSTANCE.WTSUnRegisterSessionNotification(hWnd);
        User32.INSTANCE.UnregisterClass(WINDOW_CLASS, H_INST);
        User32.INSTANCE.DestroyWindow(hWnd);
        log.info("LockScreenListener unregistered");
    }
}
