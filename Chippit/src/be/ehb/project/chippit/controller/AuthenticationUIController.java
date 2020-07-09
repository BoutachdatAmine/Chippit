package be.ehb.project.chippit.controller;

import be.ehb.project.chippit.db.PersonDAO;
import be.ehb.project.chippit.entity.AuthSms;
import be.ehb.project.chippit.entity.Person;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AuthenticationUIController extends Application implements ControlledScreen {

    //Screencontroller
    private ScreensController myController;

    //Attributes
    @FXML private TextField passwordInput;
    @FXML private Label message;

    //The filled password will be checked
    @FXML
    public void sendPassword(ActionEvent event){
        PersonDAO personDAO = new PersonDAO();
        Person p = personDAO.getUserByEMail(LoginUIController.getLoggedInUser());
        String secretKey = p.getTfa();
        AuthSms authSms = new AuthSms();
        String secretPassword = passwordInput.getText();


        if (secretPassword.equals(authSms.getTOTPCode(secretKey))) {
            message.setText("You're logged in");
            message.setStyle("-fx-text-fill: #66da87");
            passwordInput.setStyle("-fx-border-color: #373F51");
            message.setStyle("-fx-text-fill: #373F51");
            message.setVisible(true);
            //If the generated password is correct, the user will be redirected to the home module
            myController.setScreen(MainView.home);
        }else {
            passwordInput.setStyle("-fx-border-color: #e04242");
            passwordInput.setStyle("-fx-border-color: #e04242");
            message.setStyle("-fx-text-fill: #e04242");
            message.setText("Wrong password");
            passwordInput.setText("");
            message.setVisible(true);
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {};
    }

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane root = FXMLLoader.load(getClass().getResource("../view/twoAuth.fxml"));

        Scene scene = new Scene(root, 400, 425);
        stage.setTitle("Chippit · Two factor Authentication");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        this.myController = screenPage;
    }

    //Go back to login view
    @FXML
    public void goToLogin(ActionEvent event){
        this.myController.setScreen(MainView.login);
    }
}

