package pl.tmaj.model;

import com.sun.jna.Native;
import com.sun.jna.win32.StdCallLibrary;

public interface Kernel32 extends StdCallLibrary {

    Kernel32 INSTANCE = (Kernel32) Native.loadLibrary("kernel32", Kernel32.class);

    int GetTickCount();
}
