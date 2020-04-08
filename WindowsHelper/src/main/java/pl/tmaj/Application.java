package pl.tmaj;

import lombok.extern.java.Log;
import pl.tmaj.helper.impl.HotCorners;
import pl.tmaj.helper.impl.IsIdle;
import pl.tmaj.helper.impl.IsInLockScreen;

import java.awt.*;
import java.util.List;

import static java.util.Objects.nonNull;

@Log
public class Application {

    public static final int SLEEPY_SLEEPY = 2 * 60 * 1000;

    public static void main(String[] args) {
        WindowsHelper helper = null;
        try {
            log.info("initiating windows helper");
            final Robot robot = new Robot();
            final IsInLockScreen inLockScreen = new IsInLockScreen();
            helper = new WindowsHelper(List.of(inLockScreen, new IsIdle(robot, inLockScreen), new HotCorners(robot)));
            while (helper.isRunning()) {
                Thread.sleep(SLEEPY_SLEEPY);
            }
        } catch (Exception exception) {
            log.warning(exception.getMessage());
        } finally {
            if (nonNull(helper)) {
                helper.kill();
            }
        }
    }
}
