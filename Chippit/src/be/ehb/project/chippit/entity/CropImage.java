package be.ehb.project.chippit.entity;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CropImage {
    private static String OS = null;
    public static String getOsName(){
        if(OS == null){
            OS = System.getProperty("os.name");
        }
        return OS;
    }
    public static boolean isWindows(){
        return getOsName().startsWith("Windows");
    }
    public static boolean isMac(){
        return getOsName().startsWith("Mac");
    }

    public void crop(String localPath, String brandname, short count, String extenstion) {
        try {
            FTPConnection ftpConnection = new FTPConnection();
            BufferedImage originalImgage = ImageIO.read(new File(localPath));

            System.out.println("Original Image Dimension: " + originalImgage.getWidth() + "x" + originalImgage.getHeight());

            BufferedImage SubImgage = originalImgage.getSubimage(((originalImgage.getWidth() - 533) / 2), ((originalImgage.getHeight() - 533) / 2), 533, 533);
            System.out.println("Cropped Image Dimension: " + SubImgage.getWidth() + "x" + SubImgage.getHeight());
            File outputfile = null;
            if (isWindows())
                outputfile = new File("src/generatedFiles/a-cropped-local." + extenstion);
            else if (isMac())
                outputfile = new File("src/generatedFiles/a-cropped-local." + extenstion);
            ImageIO.write(SubImgage, extenstion, outputfile);
            ftpConnection.copyToFTP(outputfile, brandname + '-' + count + '.' + extenstion);

            System.out.println("Image cropped successfully: " + outputfile.getPath());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}