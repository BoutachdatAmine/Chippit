package be.ehb.project.chippit.entity;

import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Arrays;

public class Product {
    private int productID;
    private String productName;
    private double price;
    private String manufacturer;
    private int quantity;
    private String description;
    private String keyword1;
    private String keyword2;
    private String keyword3;
    private String image1;
    private String image2;
    private String image3;
    private boolean active;

    public Product() {
    }

    public Product(int productID, String productName, double price, String manufacturer, int quantity, String keyword1, String keyword2, String keyword3, String image1, String image2, String image3, boolean active) {
        this.productID = productID;
        this.productName = productName;
        this.price = price;
        this.manufacturer = manufacturer;
        this.quantity = quantity;
        this.keyword1 = keyword1;
        this.keyword2 = keyword2;
        this.keyword3 = keyword3;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.active = active;
    }

    public Product(int productID, String productName, double price, String manufacturer, int quantity, String description, String keyword1, String keyword2, String keyword3, String image1, String image2, String image3, boolean active) {
        this.productID = productID;
        this.productName = productName;
        this.price = price;
        this.manufacturer = manufacturer;
        this.quantity = quantity;
        this.description = description;
        this.keyword1 = keyword1;
        this.keyword2 = keyword2;
        this.keyword3 = keyword3;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.active = active;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeyword1() {
        return keyword1;
    }

    public void setKeyword1(String keyword1) {
        this.keyword1 = keyword1;
    }

    public String getKeyword2() {
        return keyword2;
    }

    public void setKeyword2(String keyword2) {
        this.keyword2 = keyword2;
    }

    public String getKeyword3() {
        return keyword3;
    }

    public void setKeyword3(String keyword3) {
        this.keyword3 = keyword3;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


    //Generate a QR for the created product
    public static void generateQR(String product, int width, int height, String filepath) throws WriterException,
                IOException {

        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        //Represents a 2D matrix of bits. X is the column position and y is the row position.
        BitMatrix bitMatrix = qrCodeWriter.encode(product, BarcodeFormat.QR_CODE, width, height);

        //Factory method for file systems. getDefault() returns the default FileSystem.
        Path path = FileSystems.getDefault().getPath(filepath);

        //Writes a BitMatrix to BufferedImage, file or stream. BufferedImage is used to handle and manipulate the
        // image data.
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
        }

    @Override
    public String toString() {
        return "Product{" +
                "productID=" + productID +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", manufacturer='" + manufacturer + '\'' +
                ", quantity=" + quantity +
                ", description='" + description + '\'' +
                ", keyword1='" + keyword1 + '\'' +
                ", keyword2='" + keyword2 + '\'' +
                ", keyword3='" + keyword3 + '\'' +
                ", image1='" + image1 + '\'' +
                ", image2='" + image2 + '\'' +
                ", image3='" + image3 + '\'' +
                ", active=" + active +
                '}'+ "\n";
    }
}
