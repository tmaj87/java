package it.justDo.misc;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipApi {

    private ZipOutputStream zipStream;

    private ZipApi(String path) throws FileNotFoundException {
        FileOutputStream outputStream = new FileOutputStream(path);
        zipStream = new ZipOutputStream(outputStream);
    }

    public static ZipApi to(String location) throws FileNotFoundException {
        return new ZipApi(location);
    }

    public void pack(String file) throws IOException {
        pack(new String[]{file});
    }

    public void pack(String[] files) throws IOException {
        for (String file : files) {
            addToZip(file);
        }
        closeStream();
    }

    private ZipApi addToZip(String file) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(file)) {
            createEntry(file);
            writeToStream(inputStream);
        }
        return this;
    }

    private void createEntry(String file) throws IOException {
        String name = new File(file).getName();
        ZipEntry entry = new ZipEntry(name);
        zipStream.putNextEntry(entry);
    }

    private void writeToStream(FileInputStream inputStream) throws IOException {
        byte[] readBuffer = new byte[1024];
        int amountRead;
        while ((amountRead = inputStream.read(readBuffer)) > 0) {
            zipStream.write(readBuffer, 0, amountRead);
        }
    }

    private void closeStream() throws IOException {
        zipStream.close();
    }
}
