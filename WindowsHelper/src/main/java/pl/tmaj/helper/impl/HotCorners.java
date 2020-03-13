package pl.tmaj.helper.impl;

import pl.tmaj.helper.Helper;

import java.awt.*;

import static java.awt.MouseInfo.getPointerInfo;
import static java.awt.Toolkit.getDefaultToolkit;
import static java.awt.event.KeyEvent.VK_TAB;
import static java.awt.event.KeyEvent.VK_WINDOWS;

public class HotCorners implements Helper {

    private final Robot robot;
    private boolean bottomRightCornerVisited;

    public HotCorners(Robot robot) {
        this.robot = robot;
    }

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
        } else {
            if (bottomRightCornerVisited) {
                bottomRightCornerVisited = false;
            }
        }
    }

    private boolean inRightBottomCorner() { // TODO: add multi display support
        Dimension screen = getDefaultToolkit().getScreenSize();
        Point position = getPointerInfo().getLocation();
        return position.x + 1 == screen.width && position.y + 1 == screen.height;
    }

    private void launchTaskView() {
        robot.keyPress(VK_WINDOWS);
        robot.keyPress(VK_TAB);
        robot.keyRelease(VK_TAB);
        robot.keyRelease(VK_WINDOWS);
    }
}
