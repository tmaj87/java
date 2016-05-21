package it.justDo.misc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipApi {

    private File destination;

    public ZipApi(String path) {
        destination = new File(path);
    }

    public void packFiles(String[] files) throws IOException {
        FileOutputStream fileStream = new FileOutputStream(destination);
        try (ZipOutputStream zipStream = new ZipOutputStream(fileStream)) {
            packAll(files, zipStream);
        }
    }

    private void packAll(String[] images, ZipOutputStream zipStream) throws IOException {
        for (String file : images) {
            packSingle(zipStream, file);
        }
    }

    private void packSingle(ZipOutputStream zipStream, String file) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(file)) {
            createZipFile(zipStream, file, inputStream);
        }
    }

    private void createZipFile(ZipOutputStream zipStream, String file, FileInputStream inputStream) throws IOException {
        String name = new File(file).getName();
        ZipEntry entry = new ZipEntry(name);
        zipStream.putNextEntry(entry);
        writeToZip(zipStream, inputStream);
    }

    private void writeToZip(ZipOutputStream zipStream, FileInputStream inputStream) throws IOException {
        byte[] readBuffer = new byte[1024];
        int amountRead;
        while ((amountRead = inputStream.read(readBuffer)) > 0) {
            zipStream.write(readBuffer, 0, amountRead);
        }
    }
}
