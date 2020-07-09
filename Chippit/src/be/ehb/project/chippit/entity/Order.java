package be.ehb.project.chippit.entity;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class Order {
    private int orderID;
    private int customerID;
    private double totalPrice;

    public Order() {
    }

    public Order(int orderID, int customerID, double totalPrice) {
        this.orderID = orderID;
        this.customerID = customerID;
        this.totalPrice = totalPrice;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }


    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customer) {
        this.customerID = customer;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    //Generate a QR for an order
    public static void generateQR(String order, int width, int height, String filepath) throws WriterException,
            IOException {

        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        //Represents a 2D matrix of bits. X is the column position and y is the row position.
        BitMatrix bitMatrix = qrCodeWriter.encode(order, BarcodeFormat.QR_CODE, width, height);

        //Factory method for file systems. getDefault() returns the default FileSystem.
        Path path = FileSystems.getDefault().getPath(filepath);

        //Writes a BitMatrix to BufferedImage, file or stream. BufferedImage is used to handle and manipulate the
        // image data.
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderID=" + orderID +
                ", customer=" + customerID +
                ", totalPrice=" + totalPrice +
                '}'+ "\n";
    }
}
