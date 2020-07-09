package be.ehb.project.chippit.controller;

import be.ehb.project.chippit.db.*;
import be.ehb.project.chippit.entity.Category;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ShopUIController extends Application implements Initializable, ControlledScreen {

    //Screencontroller
    private ScreensController myController;

    //LoginUIController
    LoginUIController loginUIController = new LoginUIController();
    PersonDAO personDAO = new PersonDAO();

    //Tableview Customers
    @FXML private TableView<Person> employeesTable;
    @FXML private TableColumn<Person, Integer> employeeID;
    @FXML private TableColumn<Person, String>  employeeFirstName;
    @FXML private TableColumn<Person, String> employeeLastName;
    @FXML private TableColumn<Person, String> gender;
    @FXML private TableColumn<Person, String> email;
    @FXML private TableColumn<Person, Integer> permission;

    //Tableview Categories
    @FXML private TableView<Category> categoriesTable;
    @FXML private TableColumn<Category, String> categoryName;

    //Export
    @FXML private TextField fileToSend;

    //Attribute to store selected employee and category
    private static Integer id = null;
    private static String name = null;

    //Getter
    public static String getName() {
        return name;
    }
    public static Integer getId() {
        return id;
    }

    //Delete an employee Button
    @FXML
    public void deleteEmployee(ActionEvent event){
        PersonDAO personDAO = new PersonDAO();
        personDAO.deleteUserById(id);
    }

    //Delete a category button
    @FXML
    public void deleteCategory(ActionEvent event){
        CategoryDAO categoryDAO = new CategoryDAO();
        categoryDAO.deleteCategory(name);
    }

    //Select an employee ID from tableview
    @FXML
    public void selectEmployee(MouseEvent event){
        Person p = employeesTable.getSelectionModel().getSelectedItem();
        id = p.getPersonID();
    }

    //Select a categoryname from tableview
    @FXML
    public void selectCategory(MouseEvent event){
        Category c = categoriesTable.getSelectionModel().getSelectedItem();
        name = c.getCategoryName();
    }

    //Go to files with filechooser button
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

    //Send an excel or csv with employee data to database
    @FXML
    public void sendToDatabase(ActionEvent event){
        ReadFromExcel readFromExcel = new ReadFromExcel();
        ReadFromCsv readFromCsv = new ReadFromCsv();

        String file = fileToSend.getText();
        if(file.endsWith(".xls")){
            try {
                readFromExcel.importUsersFromExcel(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (file.endsWith(".csv")){
                readFromCsv.importUsersFromCSV(file);
        }
    }

    //Create an excel with employee data
    @FXML public void createExcel(ActionEvent event){
        ReadFromDbToExcel readFromDbToExcel = new ReadFromDbToExcel();

        DirectoryChooser dc = new DirectoryChooser();
        File selectedDirectory = dc.showDialog(null);

        String directory = selectedDirectory.getPath().replace("\\", "\\\\");

        try {
            readFromDbToExcel.usersToEXcel(directory);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //Create a csv with employee data
    @FXML public void createCsv(ActionEvent event){
        ReadFromDbToCsv readFromDbToCsv = new ReadFromDbToCsv();
        DirectoryChooser dc = new DirectoryChooser();
        File selectedDirectory = dc.showDialog(null);

        String directory = selectedDirectory.getPath().replace("\\", "\\\\");

        readFromDbToCsv.usersToCSV(directory);
    }


    //Fill the Tableviews
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Fill Employees Tableview
        employeeID.setCellValueFactory(new PropertyValueFactory<Person, Integer>("personID"));
        employeeFirstName.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));
        employeeLastName.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));
        gender.setCellValueFactory(new PropertyValueFactory<Person, String>("gender"));
        email.setCellValueFactory(new PropertyValueFactory<Person, String>("email"));
        permission.setCellValueFactory(new PropertyValueFactory<Person, Integer>("permission"));
        employeesTable.setItems(getEmployees());

        //Fill Categories Tableview
        categoryName.setCellValueFactory(new PropertyValueFactory<Category, String>("categoryName"));
        categoriesTable.setItems(getCategories());
    }

    //Refresh all tableviews
    @FXML
    public void refreshTable(ActionEvent event){
        //Fill Employees Tableview
        employeeID.setCellValueFactory(new PropertyValueFactory<Person, Integer>("personID"));
        employeeFirstName.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));
        employeeLastName.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));
        gender.setCellValueFactory(new PropertyValueFactory<Person, String>("gender"));
        email.setCellValueFactory(new PropertyValueFactory<Person, String>("email"));
        permission.setCellValueFactory(new PropertyValueFactory<Person, Integer>("permission"));
        employeesTable.setItems(getEmployees());

        //Fill Categories Tableview
        categoryName.setCellValueFactory(new PropertyValueFactory<Category, String>("categoryName"));
        categoriesTable.setItems(getCategories());
    }

    //Get employees from database
    private ObservableList<Person> getEmployees() {
        ObservableList<Person> p = FXCollections.observableArrayList();

        PersonDAO personDAO = new PersonDAO();

        for (Person person : personDAO.getListOfPersons("USERS")) {
            p.add(person);
        }

        return p;
    }

    //Get categories from database
    private ObservableList<Category> getCategories() {
        ObservableList<Category> c = FXCollections.observableArrayList();

        CategoryDAO categoryDAO = new CategoryDAO();

        for (Category category : categoryDAO.getListOfCategories()) {
            c.add(category);
        }

        return c;
    }

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane root = FXMLLoader.load(getClass().getResource("../view/shopModule.fxml"));

        Scene scene = new Scene(root, 1316, 627);
        stage.setTitle("Chippit · Shop");
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
    private void createEmployee(ActionEvent event) {
        this.myController.setScreen(MainView.addUser);
    }

    @FXML
    private void createCategory(ActionEvent event) {
        this.myController.setScreen(MainView.addCategory);
    }

    @FXML
    private void updateUser(ActionEvent event) {
        this.myController.setScreen(MainView.updateUser);
    }

    @FXML
    private void updateCategory(ActionEvent event) {
        this.myController.setScreen(MainView.updateCategory);
    }
}
