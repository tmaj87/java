package it.justDo.misc;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipApi {

    private ZipOutputStream zipStream;

    public ZipApi(String path) throws FileNotFoundException {
        FileOutputStream outputStream = new FileOutputStream(path);
        zipStream = new ZipOutputStream(outputStream);
    }

    public void pack(String[] files) throws IOException {
        for (String file : files) {
            pack(file);
        }
    }

    public void pack(String file) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(file)) {
            createEntry(file);
            writeToZip(inputStream);
        }
    }

    private void createEntry(String file) throws IOException {
        String name = new File(file).getName();
        ZipEntry entry = new ZipEntry(name);
        zipStream.putNextEntry(entry);
    }

    private void writeToZip(FileInputStream inputStream) throws IOException {
        byte[] readBuffer = new byte[1024];
        int amountRead;
        while ((amountRead = inputStream.read(readBuffer)) > 0) {
            zipStream.write(readBuffer, 0, amountRead);
        }
    }

    public void close() throws IOException {
        zipStream.close();
    }
}
