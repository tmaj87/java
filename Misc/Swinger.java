package hi;

import javax.swing.*;
import java.awt.*;

public class Swinger extends JFrame {

    private static final Integer WIN_WIDTH = 600;
    private static final Integer WIN_HEIGHT = 300;
    private static final Integer GAP_SIZE = 6;

    private String WIN_TITLE = this.getClass().getSimpleName();
    private Integer WIN_X;
    private Integer WIN_Y;

    public Swinger() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        WIN_X = screen.width / 2 - WIN_WIDTH / 2;
        WIN_Y = screen.height / 2 - WIN_HEIGHT / 2;

        initWindow();
//        initButtons();
        initPicture();
        setVisible(true);
    }

    private void initWindow() {
        setTitle(WIN_TITLE);
        setSize(WIN_WIDTH, WIN_HEIGHT); // could be done via Dimension
        setLocation(WIN_X, WIN_Y); // could be done via Point
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
    }

    private void initPicture() {
        add(new SwingerPicture());
        pack();
    }

    private void initButtons() {
        setLayout(new GridLayout(8, 1, GAP_SIZE, GAP_SIZE)); // second param - columns - will be ignored

        for (int i = 0; i < 26; i++) {
            add(new JButton(Character.toString((char) (i + 97))));
        }
    }

    public static void main(String[] args) {
        new Swinger();
    }
}
