package edu.bsuir.ti_lab4.crypting;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class FIO {

    public static byte[] readFileAsByteArray(File file) throws IOException {
        var fis = new BufferedInputStream(new FileInputStream(file));
        byte[] data = new byte[fis.available()];
        fis.read(data);
        if (fis.available() > 0) {
            throw new IOException("Error with reading file!");
        }
        fis.close();
        return data;
    }

    public static void signFileWithEDS(File fileToSign, File fileToSave, long s) throws IOException {
        var fis = new BufferedInputStream(new FileInputStream(fileToSign));
        byte[] data = new byte[fis.available()];
        fis.read(data);
        if (fis.available() > 0) {
            throw new IOException("Error with reading file!");
        }
        fis.close();
        var fos = new BufferedOutputStream((new FileOutputStream(fileToSave)));
        fos.write(data);
        fos.write((", " + s).getBytes());
        fos.close();
    }

    public static byte[] readFileWithEDSasByteArray(File file, EDSKey key) throws IOException {
        var fis = new BufferedInputStream(new FileInputStream(file));
        byte[] data = new byte[fis.available()];
        fis.read(data);
        if (fis.available() > 0) {
            throw new IOException("Error with reading file!");
        }
        fis.close();
        int i = data.length - 1;
        while (i >= 0 && (char) data[i] != ',') {
            i--;
        }
        String s = "";
        for (int m = i+2; m < data.length; m++){
            s += (char) data[m];
        }
        key.setEds(Long.parseLong(s));
        return Arrays.copyOfRange(data, 0, i);
    }

}
