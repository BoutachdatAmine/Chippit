package be.ehb.project.chippit.controller;

import be.ehb.project.chippit.db.PersonDAO;
import be.ehb.project.chippit.entity.Address;
import be.ehb.project.chippit.entity.AuthSms;
import be.ehb.project.chippit.entity.PasswordGenerator;
import be.ehb.project.chippit.entity.Person;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdateUserUIController extends Application implements ControlledScreen, Initializable {

    //Screencontroller
    private ScreensController myController;

    //Controls to create Employee
    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private TextField email;
    @FXML private DatePicker birthdate;
    @FXML private TextField password;
    @FXML private TextField address1;
    @FXML private TextField address2;
    @FXML private TextField postalCode;
    @FXML private TextField city;
    @FXML private TextField country;
    @FXML private ComboBox<String> gender;
    @FXML private TextField phone;
    @FXML private ComboBox<Integer> permission;
    @FXML private TextField googlePassword;


    //Button that sets the textfields with old product data
    @FXML
    public void setOldData(ActionEvent event){
        PersonDAO personDAO = new PersonDAO();
        ShopUIController shopUIController = new ShopUIController();
        Person oldUser = personDAO.getUserByID(shopUIController.getId());
        //Fill textfields with old data
        firstName.setText(oldUser.getFirstName());
        lastName.setText(oldUser.getLastName());
        email.setText(oldUser.getEmail());
        password.setText(oldUser.getPassword());
        phone.setText(oldUser.getPhone());
        Address oldAddress = oldUser.getAddress();
        address1.setText(oldAddress.getAddress1());
        address2.setText(oldAddress.getAddress2());
        postalCode.setText(oldAddress.getPostalCode());
        city.setText(oldAddress.getCity());
        country.setText(oldAddress.getCountry());
        googlePassword.setText(oldUser.getTfa());
    }


    //Generate a random password
    @FXML
    public void generatePassword(ActionEvent event){
        PasswordGenerator pw = new PasswordGenerator();
        String randomPassword = pw.generateRandomPassword();
        password.setText(randomPassword);
    }

    //Generate a google secret Key
    @FXML
    public void generateGooglePassword(ActionEvent event){
        AuthSms authSms = new AuthSms();
        String tfaPassword = authSms.generateSecretKey();
        googlePassword.setText(tfaPassword);
    }

    //Update an employee
    @FXML
    public void updateUser(ActionEvent event){
        PersonDAO personDAO = new PersonDAO();
        ShopUIController shopUIController = new ShopUIController();

        String hashedPassword = org.mindrot.jbcrypt.BCrypt.hashpw(password.getText(), org.mindrot.jbcrypt.BCrypt.gensalt());
        String hashedTfa = org.mindrot.jbcrypt.BCrypt.hashpw(googlePassword.getText(), org.mindrot.jbcrypt.BCrypt.gensalt());

        //If any textfield is empty, the user will get an error message.
        if (lastName.getText().isEmpty() || firstName.getText().isEmpty() || phone.getText().isEmpty() ||
                country.getText().isEmpty() || password.getText().isEmpty() || googlePassword.getText().isEmpty() ||
                postalCode.getText().isEmpty() || address1.getText().isEmpty() || address2.getText().isEmpty() ||
                city.getText().isEmpty() || gender.getValue() == null || permission.getValue() == null ||
                birthdate.getValue() == null){

            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText(null);
            error.setContentText("User not updated, please fill all fields!");
            error.showAndWait();
        } else { //Else if all textfields are filled, the new user wil be created.

            Address newAddress = new Address(country.getText(), city.getText(), postalCode.getText(), address1.getText(), address2.getText());
            Person newUser = new Person(
                    firstName.getText(),
                    lastName.getText(),
                    phone.getText(),
                    gender.getValue(),
                    email.getText(),
                    birthdate.getEditor().getText(),
                    permission.getValue(),
                    hashedPassword,
                    1,
                    newAddress,
                    hashedTfa);
            personDAO.updateUser(shopUIController.getId(), newUser);

            Alert success = new Alert(Alert.AlertType.CONFIRMATION);
            success.setTitle("Success");
            success.setHeaderText(null);
            success.setContentText("User updated successfully");
            success.showAndWait();

            //Make everything empty
            firstName.setText("");
            lastName.setText("");
            email.setText("");
            password.setText("");
            address1.setText("");
            address2.setText("");
            postalCode.setText("");
            city.setText("");
            country.setText("");
            phone.setText("");
            googlePassword.setText("");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //List for combobox Gender
        ObservableList<String> genderList = FXCollections.observableArrayList("M", "F");
        gender.setItems(genderList);

        //List for combobox permission
        ObservableList<Integer> permissionList =  FXCollections.observableArrayList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        permission.setItems(permissionList);
    }


    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane root = FXMLLoader.load(getClass().getResource("../view/updateUser.fxml"));

        Scene scene = new Scene(root, 1316, 627);
        stage.setTitle("Chippit · Update User");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void setScreenParent(ScreensController screenPage) {
        this.myController = screenPage;
    }

    //Go back to shop module
    @FXML
    public void goToShop(ActionEvent event) {
        this.myController.setScreen(MainView.shop);
    }
}