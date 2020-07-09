package be.ehb.project.chippit.entity;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import javax.imageio.ImageIO;

public class FTPConnection {
    public void copyToFTP (File localFile, String remoteFile) {
        //Data to connect with Raspberry PI
        String server = "94.111.40.176";
        int port = 21;
        String user = "chippit";
        String pass = "chippit";

        System.out.println(localFile);

        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);


            String firstRemoteFile = "project/public/img/small-img/" + remoteFile;
            InputStream inputStream = new FileInputStream(localFile);

            System.out.println("Start uploading first file");
            boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);
            inputStream.close();
            if (done) {
                System.out.println("The first file is uploaded successfully.");
            } else
                System.out.println("Error, clould not save the file");
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
