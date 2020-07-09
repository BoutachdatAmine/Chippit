package be.ehb.project.chippit.controller;

import be.ehb.project.chippit.db.PersonDAO;
import be.ehb.project.chippit.entity.*;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import java.net.URL;
import java.util.ResourceBundle;


public class PersonUIController extends Application implements ControlledScreen, Initializable {

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


    //Generate a random password
    @FXML
    public void generatePassword(ActionEvent event){
        PasswordGenerator pw = new PasswordGenerator();
        String randomPassword = pw.generateRandomPassword();
        password.setText(randomPassword);
    }

    //Generate a google secret key
    @FXML
    public void generateGooglePassword(ActionEvent event){
        AuthSms authSms = new AuthSms();
        String tfaPassword = authSms.generateSecretKey();
        googlePassword.setText(tfaPassword);
    }

    //Create an employee, show alert if there is a problem. Else empty all textfields
    @FXML
    public void createUser(ActionEvent event){
        PersonDAO personDAO = new PersonDAO();

        String hashedPassword = BCrypt.hashpw(password.getText(), BCrypt.gensalt());
        String hashedTfa = BCrypt.hashpw(googlePassword.getText(), BCrypt.gensalt());

        //If any textfield is empty, the user will get an error message.
        if (lastName.getText().isEmpty() || firstName.getText().isEmpty() || phone.getText().isEmpty() ||
                country.getText().isEmpty() || password.getText().isEmpty() || googlePassword.getText().isEmpty() ||
                postalCode.getText().isEmpty() || address1.getText().isEmpty() || address2.getText().isEmpty() ||
                city.getText().isEmpty() || gender.getValue() == null || permission.getValue() == null ||
                birthdate.getValue() == null){

            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText(null);
            error.setContentText("User not created, please fill all fields!");
            error.showAndWait();
        } else { //Else if all textfields are filled, the new user wil be created.
            Address a = new Address(country.getText(), city.getText(), postalCode.getText(), address1.getText(), address2.getText());
            Person p = new Person(firstName.getText(),
                    lastName.getText(),
                    phone.getText(),
                    gender.getValue(),
                    email.getText(),
                    birthdate.getEditor().getText(),
                    permission.getValue(),
                    hashedPassword,
                    0,
                    a,
                    hashedTfa);
            personDAO.createUser(p);
            Alert success = new Alert(Alert.AlertType.CONFIRMATION);
            success.setTitle("Success");
            success.setHeaderText(null);
            success.setContentText("User created successfully");
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
            gender.setValue(null);
            permission.setValue(null);
            birthdate.setValue(null);
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
        AnchorPane root = FXMLLoader.load(getClass().getResource("../view/addUser.fxml"));


        Scene scene = new Scene(root, 633, 695);
        stage.setTitle("Chippit • Add User");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        this.myController = screenPage;
    }

    //Go back to shop module
    @FXML
    public void goToShop(ActionEvent event) {
        this.myController.setScreen(MainView.shop);
    }
}