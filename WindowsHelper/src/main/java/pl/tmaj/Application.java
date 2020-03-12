package pl.tmaj;

import pl.tmaj.helper.impl.HotCorners;
import pl.tmaj.helper.impl.IdleCheck;

import java.awt.*;
import java.util.List;

import static java.time.LocalDateTime.now;

public class Application {

    private static final int SLEEPY_SLEEPY = 2 * 60 * 1000;

    public static void main(String[] args) throws InterruptedException {
        while (true) {
            try {
                System.out.println("initiating windows helper " + now());
                final Robot robot = new Robot();
                final WindowsHelper helper = new WindowsHelper(List.of(new IdleCheck(robot), new HotCorners(robot)));
                helper.init();
            } catch (Exception exception) {
                System.out.println("crash " + now() + " " + exception.getMessage());
                Thread.sleep(SLEEPY_SLEEPY);
            }
        }
    }
}
