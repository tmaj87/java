package pl.tmaj.helper.impl;

import lombok.RequiredArgsConstructor;
import pl.tmaj.helper.Helper;
import pl.tmaj.model.Kernel32;
import pl.tmaj.model.LASTINPUTINFO;
import pl.tmaj.model.User32;

import java.awt.*;
import java.util.Optional;

import static java.awt.MouseInfo.getPointerInfo;
import static java.util.concurrent.ThreadLocalRandom.current;

@RequiredArgsConstructor
public class IsIdle implements Helper {

    private static final int MAX_IDLE_TIME = 60;
    private static final int MAX_DEVIATION = 10;

    private final Robot robot;

    @Override
    public void check() {
        if (getIdleTime() > MAX_IDLE_TIME + rnd()) {
            shake();
        }
    }

    private int getIdleTime() {
        LASTINPUTINFO lastInputInfo = new LASTINPUTINFO();
        User32.INSTANCE.GetLastInputInfo(lastInputInfo);
        return (Kernel32.INSTANCE.GetTickCount() - lastInputInfo.dwTime) / 1000;
    }

    private void shake() {
        Optional.ofNullable(getPointerInfo()).ifPresent(pointerInfo -> {
            Point position = pointerInfo.getLocation();
            int x = position.x + halfOfRnd();
            int y = position.y + halfOfRnd();
            robot.mouseMove(x, y);
        });
    }

    private int rnd() {
        return current().nextInt(1, MAX_DEVIATION);
    }

    private int halfOfRnd() {
        return rnd() - MAX_DEVIATION / 2;
    }
}
