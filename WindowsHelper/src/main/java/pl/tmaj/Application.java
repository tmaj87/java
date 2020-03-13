package pl.tmaj;

import lombok.extern.java.Log;
import pl.tmaj.helper.impl.HotCorners;
import pl.tmaj.helper.impl.IsIdle;

import java.awt.*;
import java.util.List;

@Log
public class Application {

    private static final int SLEEPY_SLEEPY = 2 * 60 * 1000;

    public static void main(String[] args) throws InterruptedException {
        while (true) {
            try {
                log.info("initiating windows helper");
                final Robot robot = new Robot();
                final WindowsHelper helper = new WindowsHelper(List.of(new IsIdle(robot), new HotCorners(robot)));
                while (helper.isRunning()) {
                    Thread.sleep(SLEEPY_SLEEPY);
                }
            } catch (Exception exception) {
                log.warning(exception.getMessage());
            } finally {
                Thread.sleep(SLEEPY_SLEEPY);
            }
        }
    }
}
