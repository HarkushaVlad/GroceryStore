package com.vhark.grocerystore.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.vhark.grocerystore.GroceryStoreApp;
import com.vhark.grocerystore.util.WindowSwitcher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LogInController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button logInButton;

    @FXML
    private TextField logInIdCodeField;

    @FXML
    private Label logInLabelSwitchToSignUp;

    @FXML
    private PasswordField logInPasswordField;

    @FXML
    void initialize() {
        logInLabelSwitchToSignUp.setOnMouseClicked(mouseEvent -> {
            WindowSwitcher.switchWindow(logInLabelSwitchToSignUp, "view/SignUpPage.fxml", "Grocery Store Sign Up");
        });
    }

}
