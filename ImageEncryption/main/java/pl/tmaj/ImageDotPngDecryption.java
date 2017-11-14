package pl.tmaj;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

public class ImageDotPngDecryption {

    public ImageDotPngDecryption(String path, int key) {
        File file = new File(path + ".png");
        try {
            BufferedImage image = ImageIO.read(file);
            byte[] bytes = getBytesFromImage(image);
            int width = image.getWidth();
            int colorBytes = image.getColorModel().getNumColorComponents();
            String decrypted = decrypt(bytes, width, colorBytes, key);
            printMessage(decrypted);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] getBytesFromImage(BufferedImage image) {
        return ((DataBufferByte) image.getData().getDataBuffer()).getData();
    }

    private String decrypt(byte[] bytes, int width, int colorBytes, int every) {
        int maxWidth = bytes.length / every;
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < maxWidth; i++) {
            final int pointer = i * every;
            if (bytes[pointer] == -1 || bytes[pointer] == 0) {
                break;
            }
            final int widthCorrection = pointer / (width * colorBytes); // when even width
            final int addon = (pointer / colorBytes + widthCorrection) % 2 == 0 ? 1 : 0;
            builder.append((char) (bytes[pointer] + addon));
        }
        return builder.toString();
    }

    private void printMessage(String decrypted) {
        System.out.println(decrypted);
    }
}
