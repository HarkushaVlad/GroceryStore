package com.vhark.grocerystore.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.vhark.grocerystore.util.WindowSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
