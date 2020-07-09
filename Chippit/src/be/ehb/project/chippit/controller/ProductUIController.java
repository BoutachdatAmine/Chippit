package be.ehb.project.chippit.controller;

import be.ehb.project.chippit.db.CategoryDAO;
import be.ehb.project.chippit.db.PersonDAO;
import be.ehb.project.chippit.db.ProductDAO;
import be.ehb.project.chippit.entity.*;
import com.google.zxing.WriterException;
import com.sun.javafx.scene.control.behavior.ComboBoxListViewBehavior;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class ProductUIController extends Application implements Initializable, ControlledScreen {

    //ScreenController
    ScreensController myController;

    //Controls to create Product
    @FXML public TextField productName;
    @FXML public TextField productQuantity;
    @FXML public TextField productManufacturer;
    @FXML public TextField productPrice;
    @FXML public TextField productKeywords;
    @FXML public TextField productImg1;
    @FXML public TextField productImg2;
    @FXML public TextField productImg3;
    @FXML public TextArea productDescription;
    @FXML public ComboBox<Category> categoryName;
    @FXML public CheckBox active;

    //Filechooser button
    @FXML public Button fileChooser;

    //Go to files with filechooser button
    public void goToFiles(ActionEvent event) {
        FileChooser fc = new FileChooser();
        List<File> selectedFile = fc.showOpenMultipleDialog(null);

        if(selectedFile != null){
            for(File path: selectedFile)
                productImg1.setText(selectedFile.get(0).getPath());
                productImg2.setText(selectedFile.get(1).getPath());
                productImg3.setText(selectedFile.get(2).getPath());
        } else {
            System.out.println("File is not valid.");
        }
    }

    //Create a product
    public void createProduct(ActionEvent event){

        if(productName.getText().isEmpty() || productQuantity.getText().isEmpty() || productManufacturer.getText().isEmpty()
            || productPrice.getText().isEmpty() || productKeywords.getText().isEmpty() || productImg1.getText().isEmpty()
            || productImg2.getText().isEmpty() || productImg2.getText().isEmpty() || productDescription.getText().isEmpty() ||
            categoryName.getValue() == null){

            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText(null);
            error.setContentText("Product not created, please fill all fields!");
            error.showAndWait();

        }else{
            // Get extension of the images
            int pathLength1 = productImg1.getText().length() -3;
            int pathLength2 = productImg2.getText().length() -3;
            int pathLength3 = productImg3.getText().length() -3;
            String extensionImg1 = productImg1.getText().substring(pathLength1, pathLength1+3);
            String extensionImg2 = productImg2.getText().substring(pathLength2, pathLength2+3);
            String extensionImg3 = productImg3.getText().substring(pathLength3, pathLength3+3);


            ProductDAO productDAO = new ProductDAO();
            Product p = new Product();

            //Give the attributes to the product
            p.setProductName(productName.getText());
            p.setPrice(Double.parseDouble(productPrice.getText()));
            p.setManufacturer(productManufacturer.getText());
            p.setQuantity(Integer.parseInt(productQuantity.getText()));
            p.setDescription(productDescription.getText());
            List<String> keywordArray = Arrays.asList(productKeywords.getText().split(","));
            p.setKeyword1(keywordArray.get(0));
            p.setKeyword2(keywordArray.get(1));
            p.setKeyword3(keywordArray.get(2));
            //  Replace spaces in product name by dashes & add 1,2,3 for images
            p.setImage1(productName.getText().replace(" ", "-")+ "-1." + extensionImg1);
            p.setImage2(productName.getText().replace(" ", "-")+ "-2." + extensionImg1);
            p.setImage3(productName.getText().replace(" ", "-")+ "-3." + extensionImg1);
            if(active.isSelected())
            {
                active.setSelected(true);
                p.setActive(true);
            }
            else
            {
                active.setSelected(false);
                p.setActive(false);
            }
            Category category = categoryName.getValue();

            //Create a product
            productDAO.createProduct(p, category.getCategoryName());

            Alert success = new Alert(Alert.AlertType.CONFIRMATION);
            success.setTitle("Success");
            success.setHeaderText(null);
            success.setContentText("Product created Successfully");
            success.showAndWait();

            //Add crop image
            //Connection to FTP to send images
            CropImage cropImage = new CropImage();
            short count = 1;
            cropImage.crop(productImg1.getText(), p.getProductName().replace(" ", "-"), count, extensionImg1);
            count++;
            cropImage.crop(productImg2.getText(), p.getProductName().replace(" ", "-"), count, extensionImg2);
            count++;
            cropImage.crop(productImg3.getText(), p.getProductName().replace(" ", "-"), count, extensionImg3);

            //Empty fields
            productName.setText("");
            productQuantity.setText("");
            productManufacturer.setText("");
            productPrice.setText("");
            productKeywords.setText("");
            productImg1.setText("");
            productImg2.setText("");
            productImg3.setText("");
            productDescription.setText("");
            categoryName.setValue(null);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Fill combobox with categories
        CategoryDAO categoryDAO = new CategoryDAO();
        ObservableList<Category> category = FXCollections.observableArrayList(categoryDAO.getListOfCategories());
        categoryName.setItems(category);
    }

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane root = FXMLLoader.load(getClass().getResource("../view/addProduct.fxml"));

        Scene scene = new Scene(root, 633, 695);
        stage.setTitle("Chippit • Add product");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        this.myController = screenPage;
    }

    //Go back to warehouse module
    @FXML
    public void goToWarehouse(ActionEvent event) {
        this.myController.setScreen(MainView.warehouse);
    }
}



