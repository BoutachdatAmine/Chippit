package be.ehb.project.chippit.controller;

import be.ehb.project.chippit.db.*;
import be.ehb.project.chippit.entity.Order;
import be.ehb.project.chippit.entity.OrderProduct;

import be.ehb.project.chippit.entity.Person;
import com.google.zxing.WriterException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class FinanceUIController extends Application implements ControlledScreen, Initializable {

    //Screencontroller
    private ScreensController myController;

    //Instantiate
    LoginUIController loginUIController = new LoginUIController();
    PersonDAO personDAO = new PersonDAO();

    //Attribute
    private static Integer id = null;

    //Order tableview
    @FXML private TableView<Order> ordersTable;
    @FXML private TableColumn<Order, Integer> ID;
    @FXML private TableColumn<Order, Integer> customerID;
    @FXML private TableColumn<Order, Double> totalPrice;

    //Order product tableview
    @FXML private TableView<OrderProduct> orderProductsTable;
    @FXML private TableColumn<OrderProduct, Integer> orderProductID;
    @FXML private TableColumn<OrderProduct, Integer> orderID;
    @FXML private TableColumn<OrderProduct, Integer> productID;
    @FXML private TableColumn<OrderProduct, Integer> quantity;

    //Select an Order id from the tableview
    @FXML
    public void selectOrder(MouseEvent event){
        Order o = ordersTable.getSelectionModel().getSelectedItem();
        id = o.getOrderID();
    }

    //Generate an invoice
    @FXML
    public void generateQr(ActionEvent event) throws IOException, WriterException {
        Order order = new Order();

        DirectoryChooser dc = new DirectoryChooser();
        File selectedDirectory = dc.showDialog(null);

        String link = "http://www.chippit.be/invoice?orderId=" + id;

        String directory = selectedDirectory.getPath().replace("\\", "\\\\");
        String path = directory + "\\\\orderQR-" + id + ".png";


       order.generateQR(link, 400, 400, path);
    }

    //Create an excel of orders and order products
    @FXML
    public void createExcel(ActionEvent event){
        ReadFromDbToExcel readFromDbToExcel = new ReadFromDbToExcel();

        DirectoryChooser dc = new DirectoryChooser();
        File selectedDirectory = dc.showDialog(null);

        String directory = selectedDirectory.getPath().replace("\\", "\\\\");

        try {
            readFromDbToExcel.ordersToExcel(directory);
            readFromDbToExcel.orderProductToExcel(directory);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //Create a csv of orders and order products
    @FXML
    public void createCsv(ActionEvent event){
        ReadFromDbToCsv readFromDbToCsv = new ReadFromDbToCsv();
        DirectoryChooser dc = new DirectoryChooser();
        File selectedDirectory = dc.showDialog(null);

        String directory = selectedDirectory.getPath().replace("\\", "\\\\");

        readFromDbToCsv.orderProductToCSV(directory);
        readFromDbToCsv.ordersToCSV(directory);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Fill order tableview
        ID.setCellValueFactory(new PropertyValueFactory<Order, Integer>("orderID"));
        customerID.setCellValueFactory(new PropertyValueFactory<Order, Integer>("customerID"));
        totalPrice.setCellValueFactory(new PropertyValueFactory<Order, Double>("totalPrice"));
        ordersTable.setItems(getOrders());

        //Fill order product tableview
        orderProductID.setCellValueFactory(new PropertyValueFactory<OrderProduct, Integer>("order_product_id"));
        orderID.setCellValueFactory(new PropertyValueFactory<OrderProduct, Integer>("order_id"));
        productID.setCellValueFactory(new PropertyValueFactory<OrderProduct, Integer>("product_id"));
        quantity.setCellValueFactory(new PropertyValueFactory<OrderProduct, Integer>("quantity"));
        orderProductsTable.setItems(getOrderProducts());
    }

    //Get a list of all orders
    private ObservableList<Order> getOrders() {
        ObservableList<Order> o = FXCollections.observableArrayList();

        OrderDAO orderDAO = new OrderDAO();

        for (Order order : orderDAO.getListOfOrders()){
            o.add(order);
        }

        return o;
    }

    //Get a list of all order products
    private ObservableList<OrderProduct> getOrderProducts() {
        ObservableList<OrderProduct> op = FXCollections.observableArrayList();

        OrderProductDAO orderProductDAO = new OrderProductDAO();

        for (OrderProduct orderProduct : orderProductDAO.getListOfOrderProducts()){
            op.add(orderProduct);
        }

        return op;
    }

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane root = FXMLLoader.load(getClass().getResource("../view/financeModule.fxml"));

        Scene scene = new Scene(root, 1316, 627);
        stage.setTitle("Chippit · Finance");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void setScreenParent(ScreensController screenPage) {
        this.myController = screenPage;
    }

    //Go back to homepage
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

}
