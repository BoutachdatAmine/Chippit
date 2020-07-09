package be.ehb.project.chippit.controller;

import be.ehb.project.chippit.db.PersonDAO;
import be.ehb.project.chippit.entity.Address;
import be.ehb.project.chippit.entity.Mail;
import be.ehb.project.chippit.entity.Person;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MarketingUIController extends Application implements ControlledScreen, Initializable {

    //Screencontroller
    ScreensController myController;

    //LoginUIController
    LoginUIController loginUIController = new LoginUIController();

    //PersonDAO
    PersonDAO personDAO = new PersonDAO();

    //Tableview Customers
    @FXML private TableView<Person> customersTable;
    @FXML private TableColumn<Person, Integer> customerID;
    @FXML private TableColumn<Person, String> customerLastName;
    @FXML private TableColumn<Person, String> customerEmail;

    //Tableview Employees
    @FXML private TableView<Person> employeesTable;
    @FXML private TableColumn<Person, Integer> employeeID;
    @FXML private TableColumn<Person, String> employeeLastName;
    @FXML private TableColumn<Person, String> employeeEmail;

    //Textfields and buttons
    @FXML private TextField customersTemplate;
    @FXML private TextField employeesTemplate;

    //Go to files with filechooser button
    public void goToFilesCustomers(ActionEvent event) {
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(null);

        if(selectedFile != null){
            customersTemplate.setText(selectedFile.getPath());
        } else {
            System.out.println("File is not valid.");
        }
    }

    public void goToFilesEmployees(ActionEvent event) {
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(null);

        if(selectedFile != null){
            employeesTemplate.setText(selectedFile.getPath());
        } else {
            System.out.println("File is not valid.");
        }
    }

    //Send emails
    @FXML
    public void sendEmailToEmployees(ActionEvent event){
        System.out.println("send");
    }

    @FXML
    public void sendEmailToCustomers(ActionEvent event){
        Mail mail = new Mail();
        mail.sendEmails("customer", customersTemplate.getText());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Fill tableview customers
        customerID.setCellValueFactory(new PropertyValueFactory<Person, Integer>("personID"));
        customerLastName.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));
        customerEmail.setCellValueFactory(new PropertyValueFactory<Person, String>("email"));
        customersTable.setItems(getPersons("CUSTOMER"));

        //Fill tableview employees
        employeeID.setCellValueFactory(new PropertyValueFactory<Person, Integer>("personID"));
        employeeLastName.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));
        employeeEmail.setCellValueFactory(new PropertyValueFactory<Person, String>("email"));
        employeesTable.setItems(getPersons("USERS"));
    }

    //Get a list of employees and customers
    private ObservableList<Person> getPersons(String type) {
        ObservableList<Person> p = FXCollections.observableArrayList();

        PersonDAO personDAO = new PersonDAO();

        for (Person person : personDAO.getListOfPersons(type)){
            if(person.isMarketing() == true && type == "CUSTOMER")
                p.add(person);
            else if (type == "USERS"){
                p.add(person); 
            }
        }

        return p;
    }

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane root = FXMLLoader.load(getClass().getResource("../view/marketingModule.fxml"));

        Scene scene = new Scene(root, 1316, 627);
        stage.setTitle("Chippit · Marketing");
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
