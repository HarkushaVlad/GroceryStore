package com.vhark.grocerystore.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.vhark.grocerystore.util.WindowSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignUpController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button SignUpButton;

    @FXML
    private TextField signUpAddressField;

    @FXML
    private TextField signUpFirstNameField;

    @FXML
    private TextField signUpIdCodeField;

    @FXML
    private Label signUpLabelSwitchToLogIn;

    @FXML
    private TextField signUpLastNameField;

    @FXML
    private TextField signUpMiddleNameField;

    @FXML
    private PasswordField signUpPasswordConfirmField;

    @FXML
    private PasswordField signUpPasswordField;

    @FXML
    void initialize() {
        signUpLabelSwitchToLogIn.setOnMouseClicked(mouseEvent -> {
            WindowSwitcher.switchWindow(signUpLabelSwitchToLogIn, "view/LogInPage.fxml", "Grocery Store Log In");
        });
    }

}