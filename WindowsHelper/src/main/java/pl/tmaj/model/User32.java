package pl.tmaj.model;

import com.sun.jna.Native;
import com.sun.jna.win32.StdCallLibrary;

public interface User32 extends StdCallLibrary {

    User32 INSTANCE = (User32) Native.loadLibrary("user32", User32.class);

    boolean GetLastInputInfo(LASTINPUTINFO result);
}