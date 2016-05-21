package hi;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AutoIt {
    static final int MAX_RAND = 600;
    static final int INC_BY = 2;

    Robot bot = new Robot();
    Random rand = new Random();

    public AutoIt() throws AWTException {
        bot.setAutoWaitForIdle(true);
        bot.setAutoDelay(10);

//        randomMove();
//        smoothMove();
        shaking();
    }

    public static void main(String[] args) throws AWTException {
        new AutoIt();
    }

    private void randomMove() {
        bot.setAutoDelay(100);

        for (int i = 0 ; i < 50 ; i++) {
            int random_1 = rand.nextInt(MAX_RAND);
            int random_2 = rand.nextInt(MAX_RAND);

            bot.mouseMove(random_1, random_2);
        }
    }

    private void smoothMove() {
        Map<String, Integer> pos = new HashMap<String, Integer>();
        pos.put("x", 10);
        pos.put("y", 10);

        for (int i = 0 ; i < 100 ; i++) {
            pos.put("x", pos.get("x") + INC_BY);
            pos.put("y", pos.get("y") + INC_BY);
            bot.mouseMove(pos.get("x"), pos.get("y"));
        }
    }

    private void shaking() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int was_x = screen.width / 2;
        int was_y = screen.height / 2;

        while (true) {
            bot.mouseMove(was_x, was_y);

            was_x += rand.nextInt(INC_BY * 2) - INC_BY;
            was_y += rand.nextInt(INC_BY * 2) - INC_BY;
        }
    }
}
