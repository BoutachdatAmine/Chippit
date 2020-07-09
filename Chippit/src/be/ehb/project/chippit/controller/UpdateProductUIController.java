package be.ehb.project.chippit.controller;

import be.ehb.project.chippit.db.CategoryDAO;
import be.ehb.project.chippit.db.ProductDAO;
import be.ehb.project.chippit.entity.Category;
import be.ehb.project.chippit.entity.Product;
import com.google.zxing.WriterException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class UpdateProductUIController extends Application implements ControlledScreen, Initializable {

    //Screencontroller
    private ScreensController myController;

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

    //Fill combobox with categories
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CategoryDAO categoryDAO = new CategoryDAO();
        ObservableList<Category> category = FXCollections.observableArrayList(categoryDAO.getListOfCategories());
        categoryName.setItems(category);
    }

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

    //Button that sets the textfields with old product data
    @FXML
    public void setOldData(ActionEvent event){
        ProductDAO productDAO  = new ProductDAO();
        WarehouseUIController warehouseUIController = new WarehouseUIController();
        Product oldProduct = productDAO.getProductById(warehouseUIController.getId());

        //Fill textfields with old data
        productName.setText(oldProduct.getProductName());
        productPrice.setText(String.valueOf(oldProduct.getPrice()));
        productManufacturer.setText(oldProduct.getManufacturer());
        productQuantity.setText(String.valueOf(oldProduct.getQuantity()));
        productDescription.setText(oldProduct.getDescription());
        List<String> keywordArray = new ArrayList<>();
        keywordArray.add(oldProduct.getKeyword1());
        keywordArray.add(oldProduct.getKeyword2());
        keywordArray.add(oldProduct.getKeyword3());
        productKeywords.setText(String.valueOf(keywordArray).replace("[", "").replace("]", ""));
        productImg1.setText(oldProduct.getImage1());
        productImg2.setText(oldProduct.getImage2());
        productImg3.setText(oldProduct.getImage3());

    }

    //Update a product
    @FXML
    public void updateProduct(ActionEvent event){
        WarehouseUIController warehouseUIController = new WarehouseUIController();

        if(categoryName.getValue() == null ){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText(null);
            error.setContentText("Product not updated");
            error.showAndWait();
        }else {

            // Get extension of the images
            int pathLength1 = productImg1.getText().length() -3;
            int pathLength2 = productImg2.getText().length() -3;
            int pathLength3 = productImg3.getText().length() -3;
            String extensionImg1 = productImg1.getText().substring(pathLength1, pathLength1+3);
            String extensionImg2 = productImg2.getText().substring(pathLength2, pathLength2+3);
            String extensionImg3 = productImg3.getText().substring(pathLength3, pathLength3+3);

            ProductDAO productDAO = new ProductDAO();
            Product newProduct = new Product();

            //Give the attributes to the product
            newProduct.setProductName(productName.getText());
            newProduct.setPrice(Double.parseDouble(productPrice.getText()));
            newProduct.setManufacturer(productManufacturer.getText());
            newProduct.setQuantity(Integer.parseInt(productQuantity.getText()));
            newProduct.setDescription(productDescription.getText());
            List<String> keywordArray = Arrays.asList(productKeywords.getText().split(","));
            newProduct.setKeyword1(keywordArray.get(0));
            newProduct.setKeyword2(keywordArray.get(1));
            newProduct.setKeyword3(keywordArray.get(2));
            //  Replace spaces in product name by dashes & add 1,2,3 for images
            newProduct.setImage1(productName.getText().replace(" ", "-")+ "-1." + extensionImg1);
            newProduct.setImage2(productName.getText().replace(" ", "-")+ "-2." + extensionImg2);
            newProduct.setImage3(productName.getText().replace(" ", "-")+ "-3." + extensionImg3);
            if(active.isSelected())
            {
                active.setSelected(true);
                newProduct.setActive(true);
            }
            else
            {
                active.setSelected(false);
                newProduct.setActive(false);
            }
            Category category = categoryName.getValue();

            //Update a product
            productDAO.updateProduct(warehouseUIController.getId(), newProduct, category.getCategoryName());
            Alert success = new Alert(Alert.AlertType.CONFIRMATION);
            success.setTitle("Success");
            success.setHeaderText(null);
            success.setContentText("Product updated successfully");
            success.showAndWait();


            //Make everything empty
            productName.setText("");
            productPrice.setText("");
            productQuantity.setText("");
            productImg1.setText("");
            productImg2.setText("");
            productImg3.setText("");
            productDescription.setText("");
            productKeywords.setText("");
            productManufacturer.setText("");
        }


    }

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane root = FXMLLoader.load(getClass().getResource("../view/updateProduct.fxml"));

        Scene scene = new Scene(root, 1316, 627);
        stage.setTitle("Chippit · Update Product");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void setScreenParent(ScreensController screenPage) {
        this.myController = screenPage;
    }

    //Go back to warehouse module
    @FXML
    private void goToWarehouse(ActionEvent event) {
            this.myController.setScreen(MainView.warehouse);
    }
}