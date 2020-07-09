package be.ehb.project.chippit.controller;

import be.ehb.project.chippit.db.LoginDAO;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;


public class LoginUIController extends Application implements ControlledScreen {

    //Screencontroller
    private ScreensController myController;


    //Login attributes
    @FXML public TextField inputEmail;
    @FXML public PasswordField inputPassword;
    @FXML public Label loginStatusLabel;

    //Variable to store login
    private static String loggedInUser = null;

    public static String getLoggedInUser() {
        return loggedInUser;
    }

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane root = FXMLLoader.load(getClass().getResource("../view/login.fxml"));

        Scene scene = new Scene(root, 400, 425);
        stage.setTitle("Chippit · Log in");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    //Check the usermail and password
    public void login(ActionEvent event) throws IOException {
        LoginDAO loginDAO = new LoginDAO();


        //If the Email and Password are correct, the user is logged in.
        if (loginDAO.checkPassword(inputEmail.getText(), inputPassword.getText())) {
            //Add email to variable
            loggedInUser = String.valueOf(inputEmail.getText());

            loginStatusLabel.setText("You're logged in");
            loginStatusLabel.setStyle("-fx-text-fill: #66da87");
            inputPassword.setStyle("-fx-border-color: #373F51");
            loginStatusLabel.setStyle("-fx-text-fill: #373F51");
            loginStatusLabel.setVisible(true);
            myController.setScreen(MainView.authentication);

        }
        else{ // Else, the user has entered a wrong email or password
            inputEmail.setStyle("-fx-border-color: #e04242");
            inputPassword.setStyle("-fx-border-color: #e04242");
            loginStatusLabel.setStyle("-fx-text-fill: #e04242");
            loginStatusLabel.setText("Wrong email or password");
            inputPassword.setText("");
            loginStatusLabel.setVisible(true);
        }
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        this.myController = screenPage;
    }

}

