package hi;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class SwingerPicture extends JPanel {

    private static final String PICTURE_URL ="http://www.livehacking.com/web/wp-content/uploads/2012/12/java-square.png";

    private BufferedImage picture;

    public SwingerPicture() {
        super(); // is it required?

        try {
            URL pictureUrl = new URL(PICTURE_URL);
            picture = ImageIO.read(pictureUrl);
            Dimension dimension = new Dimension(picture.getWidth(), picture.getHeight());
            setPreferredSize(dimension);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(picture, 0, 0, this);
    }
}
