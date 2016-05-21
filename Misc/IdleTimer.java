package it.justDo.misc;

import com.sun.jna.Native;
import com.sun.jna.Structure;
import com.sun.jna.win32.StdCallLibrary;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class IdleTimer {

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

    public static void main(String[] args) throws Exception {
        if (!System.getProperty("os.name").contains("Windows")) {
            System.err.println("ERROR: Only implemented on Windows");
            System.exit(1);
        }

        idleCheckLoop();
    }

    private static void randomMouseMove() throws AWTException {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();

        Random random = new Random();
        mouseMoveWithDelay(random.nextInt(screenWidth), random.nextInt(screenHeight), 4);
    }

    private static void mouseMoveWithDelay(int dest_x, int dest_y, int delay) throws AWTException {
        Robot robot = new Robot();

        Point point = MouseInfo.getPointerInfo().getLocation();
        int base_x = (int) point.getX();
        int base_y = (int) point.getY();

        for (int i = 0; i < 100; i++) {
            int x = ((dest_x * i) / 100) + (base_x * (100 - i) / 100);
            int y = ((dest_y * i) / 100) + (base_y * (100 - i) / 100);
            robot.mouseMove(x, y);
            robot.delay(delay);
        }
    }

    private static void idleCheckLoop() throws AWTException {
        int idleSeconds;
        int lastSecond = -1;
        for (; ; ) {
            idleSeconds = getIdleTime() / 1000;
            if (idleSeconds > 0 && lastSecond != idleSeconds) {
                System.out.println(idleSeconds + " seconds idle");
                lastSecond = idleSeconds;
                if (idleSeconds > 5) {
                    randomMouseMove();
                }
            }
        }
    }
}
