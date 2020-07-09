package be.ehb.project.chippit.controller;

import be.ehb.project.chippit.db.*;
import be.ehb.project.chippit.entity.Order;
import be.ehb.project.chippit.entity.Person;
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
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class WarehouseUIController extends Application implements Initializable, ControlledScreen {

    //Screencontroller
    private ScreensController myController;

    //Instantiate
    LoginUIController loginUIController = new LoginUIController();
    PersonDAO personDAO = new PersonDAO();

    //Export
    @FXML private TextField fileToSend;

    //Tableview Product
    @FXML private TableView<Product> productTable;
    @FXML private TableColumn<Product, Integer> productID;
    @FXML private TableColumn<Product, String> productName;
    @FXML private TableColumn<Product, Double> productPrice;
    @FXML private TableColumn<Product, Integer> quantity;
    @FXML private TableColumn<Product, String> manufacturer;


    //Attribute to store selected product
    private static Integer id = null;

    //Getter
    public static Integer getId() {
        return id;
    }

    //Generate a qr with link to website
    @FXML
    public void generateQr(ActionEvent event) throws IOException, WriterException {
        Product p = new Product();

        DirectoryChooser dc = new DirectoryChooser();
        File selectedDirectory = dc.showDialog(null);

        String link = "http://www.chippit.be/productDetail?id=" + id;

        String directory = selectedDirectory.getPath().replace("\\", "\\\\");
        String path = directory + "\\\\productQR-" + id + ".png";

        p.generateQR(link, 400, 400, path);
    }

    //Delete a product with product ID
    @FXML
    public void deleteProduct(ActionEvent event){
        ProductDAO productDAO = new ProductDAO();

        if (productDAO.deleteProduct(id) == true ){
            Alert success = new Alert(Alert.AlertType.CONFIRMATION);
            success.setTitle("Success");
            success.setHeaderText(null);
            success.setContentText("Product deleted successfully");
            success.showAndWait();
        } else {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText(null);
            error.setContentText("Product not deleted, please try again");
            error.showAndWait();
        }
    }

    //Select a product ID
    @FXML
    public void selectProduct(MouseEvent event){
        Product p = productTable.getSelectionModel().getSelectedItem();
        id = p.getProductID();
    }

    //Go to files
    @FXML
    public void goToFiles(ActionEvent event) {
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(null);

        if(selectedFile != null){
            fileToSend.setText(selectedFile.getPath());
        } else {
            System.out.println("File is not valid.");
        }
    }

    //Send products from excel or csv files to database
    @FXML
    public void sendToDatabase(ActionEvent event){
        ReadFromExcel readFromExcel = new ReadFromExcel();
        ReadFromCsv readFromCsv = new ReadFromCsv();

        String file = fileToSend.getText();
        if(file.endsWith(".xls")){
            try {
                readFromExcel.importProductFromExcel(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (file.endsWith(".csv")){
            readFromCsv.importProductsFromCSV(file);
        }
    }

    //Create an excel with product data
    @FXML public void createExcel(ActionEvent event){
        ReadFromDbToExcel readFromDbToExcel = new ReadFromDbToExcel();

        DirectoryChooser dc = new DirectoryChooser();
        File selectedDirectory = dc.showDialog(null);

        String directory = selectedDirectory.getPath().replace("\\", "\\\\");

        try {
            readFromDbToExcel.productToExcel(directory);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //Create a csv with product data
    @FXML public void createCsv(ActionEvent event){
        ReadFromDbToCsv readFromDbToCsv = new ReadFromDbToCsv();
        DirectoryChooser dc = new DirectoryChooser();
        File selectedDirectory = dc.showDialog(null);

        String directory = selectedDirectory.getPath().replace("\\", "\\\\");

        readFromDbToCsv.productsToCSV(directory);
    }


    //Fill Tableview Product
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productID.setCellValueFactory(new PropertyValueFactory<Product, Integer>("productID"));
        productName.setCellValueFactory(new PropertyValueFactory<Product, String>("productName"));
        productPrice.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
        quantity.setCellValueFactory(new PropertyValueFactory<Product, Integer>("quantity"));
        manufacturer.setCellValueFactory(new PropertyValueFactory<Product, String>("manufacturer"));

        productTable.setItems(getProducts());
    }

    //refresh the tableview if you updated or deleted a product
    @FXML
    public void refreshTable(ActionEvent event){
        productID.setCellValueFactory(new PropertyValueFactory<Product, Integer>("productID"));
        productName.setCellValueFactory(new PropertyValueFactory<Product, String>("productName"));
        productPrice.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
        quantity.setCellValueFactory(new PropertyValueFactory<Product, Integer>("quantity"));
        manufacturer.setCellValueFactory(new PropertyValueFactory<Product, String>("manufacturer"));

        productTable.setItems(getProducts());
    }

    //Get a list of products
    private ObservableList<Product> getProducts() {
        ObservableList<Product> p = FXCollections.observableArrayList();

        ProductDAO productDAO = new ProductDAO();

        for (Product product : productDAO.getListOfProducts()){
            p.add(product);
        }

        return p;
    }

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane root = FXMLLoader.load(getClass().getResource("../view/warehouseModule.fxml"));

        Scene scene = new Scene(root, 1316, 627);
        stage.setTitle("Chippit · Warehouse");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void setScreenParent(ScreensController screenPage) {
        this.myController = screenPage;
    }


    //Go back to home page
    @FXML
    private void goToHome(ActionEvent event) {
        this.myController.setScreen(MainView.home);
    }

    //If permission is 9 go to customers. Else go back to home.
    @FXML
    private void goToCustomers(ActionEvent event) {
        final int permission = personDAO.getPermissionByEmail(loginUIController.getLoggedInUser());
        if(permission == 9) {
            this.myController.setScreen(MainView.customer);
        }else{
            this.myController.setScreen(MainView.home);
        }
    }

    //If permission is 4 or 9 go to warehouse. Else go back to home.
    @FXML
    private void goToWarehouse(ActionEvent event) {
        final int permission = personDAO.getPermissionByEmail(loginUIController.getLoggedInUser());
        if(permission == 0 || permission == 9) {
            this.myController.setScreen(MainView.warehouse);
        }else{
            this.myController.setScreen(MainView.home);
        }
    }

    //If permission is 1 or 9 go to finance. Else go back to home.
    @FXML
    private void goToFinance(ActionEvent event) {
        final int permission = personDAO.getPermissionByEmail(loginUIController.getLoggedInUser());
        if( permission == 1 || permission == 9) {
            this.myController.setScreen(MainView.finance);
        }else{
            this.myController.setScreen(MainView.home);
        }
    }

    //If permission is 3 or 9 go to marketing. Else go back to home.
    @FXML
    private void goToMarketing(ActionEvent event) {
        final int permission = personDAO.getPermissionByEmail(loginUIController.getLoggedInUser());
        if(permission == 3 || permission == 9) {
            this.myController.setScreen(MainView.marketing);
        }else{
            this.myController.setScreen(MainView.home);
        }
    }


    //If permission is 9 go to shop. Else go back to home.
    @FXML
    private void goToShop(ActionEvent event) {
        final int permission = personDAO.getPermissionByEmail(loginUIController.getLoggedInUser());
        if(permission == 9){
            this.myController.setScreen(MainView.shop);
        }else{
            this.myController.setScreen(MainView.home);
        }
    }

    @FXML
    private void createProduct(ActionEvent event) {
        this.myController.setScreen(MainView.addProduct);
    }

    @FXML
    private void updateProduct(ActionEvent event) {
        this.myController.setScreen(MainView.updateProduct);
    }
}
