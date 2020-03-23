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

import static com.sun.jna.platform.win32.User32.WS_EX_TOPMOST;
import static com.sun.jna.platform.win32.WinUser.WM_DESTROY;
import static com.sun.jna.platform.win32.WinUser.WM_SESSION_CHANGE;
import static com.sun.jna.platform.win32.Wtsapi32.*;

public class IsInLockScreen implements Helper {

    @Override
    public void check() {
        new LockScreenListener();
    }
}

@Log
class LockScreenListener implements WindowProc {

    private static final String WINDOW_CLASS = UUID.randomUUID().toString();
    private static final HMODULE H_INST = Kernel32.INSTANCE.GetModuleHandle("");

    private boolean locked;

    public LockScreenListener() {
        registerClassEx();
        final HWND hWnd = createWindowAndRegister();
        MSG msg = new MSG();
        while (User32.INSTANCE.GetMessage(msg, hWnd, 0, 0) != 0) {
            try {
                User32.INSTANCE.TranslateMessage(msg);
                User32.INSTANCE.DispatchMessage(msg);
                if (locked) {
                    log.info("Screen is locked, sleeping...");
                    // dispatch event
                }
            } catch (Exception exception) {
                log.warning(exception.getMessage());
            }
        }
        onDestroy(hWnd);
    }

    private void registerClassEx() {
        WNDCLASSEX wClass = new WNDCLASSEX();
        wClass.hInstance = H_INST;
        wClass.lpfnWndProc = this;
        wClass.lpszClassName = WINDOW_CLASS;
        User32.INSTANCE.RegisterClassEx(wClass);
    }

    private HWND createWindowAndRegister() {
        final HWND hWnd = User32.INSTANCE.CreateWindowEx(WS_EX_TOPMOST, WINDOW_CLASS, "", 0, 0, 0, 0, 0, null, null, H_INST, null);
        Wtsapi32.INSTANCE.WTSRegisterSessionNotification(hWnd, NOTIFY_FOR_THIS_SESSION);
        log.info("LockScreenListener registered");
        return hWnd;
    }

    private void onDestroy(HWND hWnd) {
        Wtsapi32.INSTANCE.WTSUnRegisterSessionNotification(hWnd);
        User32.INSTANCE.UnregisterClass(WINDOW_CLASS, H_INST);
        User32.INSTANCE.DestroyWindow(hWnd);
        log.info("LockScreenListener unregistered");
    }

    @Override
    public LRESULT callback(HWND hwnd, int uMsg, WPARAM wParam, LPARAM lParam) {
        switch (uMsg) {
            case WM_DESTROY: {
                User32.INSTANCE.PostQuitMessage(0);
                return new LRESULT(0);
            }
            case WM_SESSION_CHANGE: {
                this.onSessionChange(wParam);
                return new LRESULT(0);
            }
            default:
                return User32.INSTANCE.DefWindowProc(hwnd, uMsg, wParam, lParam);
        }
    }

    protected void onSessionChange(WPARAM wParam) {
        switch (wParam.intValue()) {
            case WTS_SESSION_LOCK: {
                locked = true;
                break;
            }
            case WTS_SESSION_UNLOCK: {
                locked = false;
                break;
            }
        }
    }
}
