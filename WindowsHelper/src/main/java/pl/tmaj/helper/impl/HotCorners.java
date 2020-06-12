package pl.tmaj.helper.impl;

import lombok.RequiredArgsConstructor;
import pl.tmaj.helper.Helper;

import java.awt.*;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment;
import static java.awt.MouseInfo.getPointerInfo;
import static java.awt.event.KeyEvent.VK_TAB;
import static java.awt.event.KeyEvent.VK_WINDOWS;

@RequiredArgsConstructor
public class HotCorners implements Helper {

    private static final int ACTIVATION_RANGE = 6;

    private final Robot robot;
    private boolean bottomRightCornerVisited;

    @Override
    public void check() {
        bottomRight();
    }

    private void bottomRight() {
        if (inRightBottomCorner()) {
            if (!bottomRightCornerVisited) {
                launchTaskView();
                bottomRightCornerVisited = true;
            }
        } else if (bottomRightCornerVisited) {
            bottomRightCornerVisited = false;
        }
    }

    private boolean inRightBottomCorner() {
        Point point = getPointerInfo().getLocation();
        Rectangle bounds = getBoundsForPoint(point);
        return isBottomRightCorner().test(point, bounds);
    }

    private static Rectangle getBoundsForPoint(Point point) {
        return Stream.of(getLocalGraphicsEnvironment().getScreenDevices())
                .map(getInBoundRectangleOrNull(point))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(getLocalGraphicsEnvironment().getMaximumWindowBounds());
    }

    private static Function<GraphicsDevice, Rectangle> getInBoundRectangleOrNull(Point point) {
        return device -> {
            for (GraphicsConfiguration configuration : device.getConfigurations()) {
                final Rectangle bounds = configuration.getBounds();
                if (bounds.contains(point)) {
                    return bounds;
                }
            }
            return null;
        };
    }

    private BiPredicate<Point, Rectangle> isBottomRightCorner() {
        return (point, rectangle) ->
                point.x - rectangle.x > rectangle.width - ACTIVATION_RANGE
                        && point.y - rectangle.y > rectangle.height - ACTIVATION_RANGE;
    }

    private void launchTaskView() {
        robot.keyPress(VK_WINDOWS);
        robot.keyPress(VK_TAB);
        robot.keyRelease(VK_TAB);
        robot.keyRelease(VK_WINDOWS);
    }
}
