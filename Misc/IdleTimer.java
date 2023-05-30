package it.justDo.misc;

import com.sun.jna.Native;
import com.sun.jna.Structure;
import com.sun.jna.win32.StdCallLibrary;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class IdleTimer {

    private static final int MAX = 90;
    private static final int OFFSET = 80;
    private static int delay;

    public static void main(String[] args) {
        if (!System.getProperty("os.name").contains("Windows")) {
            System.err.println("ERROR: Only implemented on Windows");
            System.exit(1);
        }
        IdleTimer it = new IdleTimer();
        it.loop();
    }

    public IdleTimer() {
        resetDelay();
    }

    public void loop() {
        int idle;
        int last = -1;
        for (; ; ) {
            idle = getIdleTime() / 1000;
            if (idle > 0 && last != idle) { // every second
                last = idle;
                if (idle > MAX) {
                    try {
                        moveWithDelay();
                    } catch (Exception e) {
                        break;
                    }
                    resetDelay();
                }
            } else {
                increaseDelay();
                try {
                    sleep(delay * 1000L);
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    private void increaseDelay() {
        if (delay < MAX / 2) {
            delay++;
        }
    }

    private void resetDelay() {
        delay = 2;
    }

    private static void moveWithDelay() throws AWTException {
        Robot robot = new Robot();
        Point point = MouseInfo.getPointerInfo().getLocation();
        int randX = current().nextInt(-OFFSET, OFFSET);
        int randY = current().nextInt(-OFFSET, OFFSET);
        robot.mouseMove(point.x + randX, point.y + randY);
        int delay = current().nextInt(0, 1000);
        robot.delay(delay);
    }

    // --- windows internals ---

    public interface Kernel32 extends StdCallLibrary {
        Kernel32 INSTANCE = (Kernel32) Native.loadLibrary("kernel32", Kernel32.class);

        int GetTickCount();
    }

    public interface User32 extends StdCallLibrary {
        User32 INSTANCE = (User32) Native.loadLibrary("user32", User32.class);

        class LASTINPUTINFO extends Structure {
            public int cbSize = 8;
            public int dwTime;

            @Override
            protected List getFieldOrder() {
                return Arrays.asList("cbSize", "dwTime");
            }
        }

        boolean GetLastInputInfo(LASTINPUTINFO result);
    }

    public static int getIdleTime() {
        User32.LASTINPUTINFO lastInputInfo = new User32.LASTINPUTINFO();
        User32.INSTANCE.GetLastInputInfo(lastInputInfo);
        return Kernel32.INSTANCE.GetTickCount() - lastInputInfo.dwTime;
    }
}
