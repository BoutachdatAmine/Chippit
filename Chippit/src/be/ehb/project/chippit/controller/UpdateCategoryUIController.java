package be.ehb.project.chippit.controller;

import be.ehb.project.chippit.db.CategoryDAO;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.SQLException;

public class UpdateCategoryUIController extends Application implements ControlledScreen {

    //Screencontroller
    private ScreensController myController;

    //Attribute
    @FXML private TextField categoryName;

    //Button that sets the textfield with old categoryName
    @FXML
    public void setOldData(ActionEvent event){
        ShopUIController shopUIController = new ShopUIController();
        categoryName.setText(shopUIController.getName());
    }

    //Update a category
    @FXML
    public void updateCategory(ActionEvent event){
        CategoryDAO categoryDAO = new CategoryDAO();
        ShopUIController shopUIController = new ShopUIController();

        try {
            categoryDAO.updateCategory(categoryName.getText(), shopUIController.getName());
            Alert success = new Alert(Alert.AlertType.CONFIRMATION);
            success.setTitle("Success");
            success.setHeaderText(null);
            success.setContentText("Category updated successfully");
            success.showAndWait();
        } catch (SQLException throwables) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText(null);
            error.setContentText("Category not updated");
            error.showAndWait();
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane root = FXMLLoader.load(getClass().getResource("../view/updateCategory.fxml"));

        Scene scene = new Scene(root, 1316, 627);
        stage.setTitle("Chippit · Update Category");
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
