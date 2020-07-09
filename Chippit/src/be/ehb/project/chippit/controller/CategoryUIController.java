package be.ehb.project.chippit.controller;

import be.ehb.project.chippit.db.CategoryDAO;
import be.ehb.project.chippit.entity.Category;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class CategoryUIController extends Application implements ControlledScreen {

    //Screencontroller
    ScreensController myController;

    //Textfield for entering categoryName
    @FXML private TextField categoryName;

    //Create a category
    @FXML
    public void createCategory(ActionEvent event){
        CategoryDAO categoryDAO = new CategoryDAO();
        Category category = new Category(categoryName.getText());

        if (categoryName.getText().isEmpty()){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText(null);
            error.setContentText("Category not created, please fill all fields!");
            error.showAndWait();
        }
        else {
            categoryDAO.createCategory(category); 
            Alert success = new Alert(Alert.AlertType.CONFIRMATION);
            success.setTitle("Success");
            success.setHeaderText(null);
            success.setContentText("Category created successfully");
            success.showAndWait();
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane root = FXMLLoader.load(getClass().getResource("../view/addCategory.fxml"));

        Scene scene = new Scene(root, 441, 307);
        stage.setTitle("Chippit • Add Category");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        this.myController = screenPage;
    }

    //Go back to the warehouse module
    @FXML
    public void goToWarehouse(ActionEvent event) {
        this.myController.setScreen(MainView.warehouse);
    }
}
