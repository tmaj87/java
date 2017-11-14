package pl.tmaj;

public class App {

    private static final String PHRASE = "We are Anonymous. We do not forgive. We do not forget.";
    private static final int KEY = 11;

    public static void main(String[] args) {
        String filename = "image";
        ImageDotPngEncryption imageEncryption = new ImageDotPngEncryption(filename, PHRASE, KEY);
        ImageDotPngDecryption imageDecryption = new ImageDotPngDecryption(filename, KEY);
    }
}
