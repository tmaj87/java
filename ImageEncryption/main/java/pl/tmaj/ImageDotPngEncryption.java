package pl.tmaj;

import javax.imageio.ImageIO;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import static java.awt.Transparency.OPAQUE;
import static java.awt.image.ColorModel.getRGBdefault;
import static java.awt.image.DataBuffer.TYPE_BYTE;
import static java.awt.image.Raster.createInterleavedRaster;

public class ImageDotPngEncryption {

    private static final int COLOR_BYTES = 3;

    public ImageDotPngEncryption(String path, String text, int key) {
        int width = 20;
        int height = 20;
        byte[] bytes = generateBackground(width, height);

        byte[] encrypted = encrypt(bytes, text, key);
        BufferedImage image = getImage(width, height, encrypted);
        printImage(image, path);
    }

    private byte[] encrypt(byte[] bytes, String text, int every) {
        int maxWidth = text.length();

        if (maxWidth * every > bytes.length) {
            throw new RuntimeException();
        }
        for (int i = 0; i < maxWidth; i++) {
            final int pointer = i * every;
            bytes[pointer] += (byte) text.charAt(i);
        }

        return bytes;
    }

    private BufferedImage getImage(int width, int height, byte[] bytes) {
        DataBuffer buffer = new DataBufferByte(bytes, bytes.length);
        ColorModel model = new ComponentColorModel(getRGBdefault().getColorSpace(), false, true, OPAQUE, TYPE_BYTE);
        WritableRaster raster = createInterleavedRaster(buffer, width, height, COLOR_BYTES * width, COLOR_BYTES, new int[]{2, 1, 0}, null);
        return new BufferedImage(model, raster, true, null);
    }

    private void printImage(BufferedImage image, String path) {
        try {
            File file = new File(path + ".png");
            ImageIO.write(image, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] generateBackground(int width, int height) {
        byte[] byteArray = new byte[COLOR_BYTES * width * height];
        int pointer = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                byte value;
                if ((x + y) % 2 == 0) {
                    value = -1;
                } else {
                    value = 0;
                }
                byteArray[pointer++] = value; // red
                byteArray[pointer++] = value; // green
                byteArray[pointer++] = value; // blue
            }
        }

        return byteArray;
    }
}