package be.ehb.project.chippit.controller;

import be.ehb.project.chippit.db.PersonDAO;
import be.ehb.project.chippit.entity.Person;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomersUIController extends Application implements Initializable, ControlledScreen  {

    //Screencontroller
    private ScreensController myController;

    //Instantiate
    LoginUIController loginUIController = new LoginUIController();
    PersonDAO personDAO = new PersonDAO();

    //Tableview Customers
    @FXML private TableView<Person> customersTable;
    @FXML private TableColumn<Person, Integer> customerID;
    @FXML private TableColumn<Person, String> customerFirstName;
    @FXML private TableColumn<Person, String> customerLastName;
    @FXML private TableColumn<Person, String> gender;

    //Boxes
    @FXML private Button countCustomers;
    @FXML private Button countMen;
    @FXML private Button countWomen;


    //Refresh button to fill all counts
    public void refreshCount(ActionEvent event){
        countCustomers.setText(String.valueOf(personDAO.countCustomers()));
        countMen.setText(String.valueOf(personDAO.countMen()));
        countWomen.setText(String.valueOf(personDAO.countWomen()));
    }


    //Fill Customers Tableview
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        customerID.setCellValueFactory(new PropertyValueFactory<Person, Integer>("personID"));
        customerFirstName.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));
        customerLastName.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));
        gender.setCellValueFactory(new PropertyValueFactory<Person, String>("gender"));

        customersTable.setItems(getCustomers());
    }

    //Get customers from Database
    private ObservableList<Person> getCustomers() {
        ObservableList<Person> p = FXCollections.observableArrayList();

        PersonDAO personDAO = new PersonDAO();

        for (Person person : personDAO.getListOfPersons("CUSTOMER")){
            p.add(person);
        }

        return p;
    }

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane root = FXMLLoader.load(getClass().getResource("../view/customersModule.fxml"));

        Scene scene = new Scene(root, 1316, 627);
        stage.setTitle("Chippit · Customers");
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
