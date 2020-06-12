package pl.tmaj;

import pl.tmaj.helper.impl.HotCorners;
import pl.tmaj.helper.impl.IsIdle;

import java.awt.*;
import java.util.List;

import static java.util.Objects.nonNull;

public class Application {

    public static final int SLEEPY_SLEEPY = 2 * 60 * 1000;

    public static void main(String[] args) {
        WindowsHelper helper = null;
        try {
            final Robot robot = new Robot();
            helper = new WindowsHelper(List.of(new IsIdle(robot), new HotCorners(robot)));
            while (helper.isRunning()) {
                Thread.sleep(SLEEPY_SLEEPY);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (nonNull(helper)) {
                helper.kill();
            }
        }
    }
}
