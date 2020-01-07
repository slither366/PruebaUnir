package venta.fotos_cliente;


import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;
import java.io.FileOutputStream;

public class FtpFileDownload {

    public static void main(String[] args) {
        FTPClient client = new FTPClient();
        FileOutputStream fos = null;
        try {
            client.connect("10.11.1.204");
            client.login("mfftp01", "mfftp01");
            // Create an OutputStream for the file
            String filename = "eula.1028.txt";
            fos = new FileOutputStream("D:\\copy\\" + filename);
            // Fetch file from server
            client.retrieveFile("/" + filename, fos);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                client.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
