package com.vhark.grocerystore.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.vhark.grocerystore.util.PopupDialogs;
import com.vhark.grocerystore.util.WindowSwitcher;
import com.vhark.grocerystore.util.userDataValidator;
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

        SignUpButton.setOnAction(actionEvent -> {
            String firstName = signUpFirstNameField.getText();
            String lastName = signUpLastNameField.getText();
            String middleName = signUpMiddleNameField.getText();
            String address = signUpAddressField.getText();
            String idCode = signUpIdCodeField.getText();
            String password = signUpPasswordField.getText();
            String passwordConfirmation = signUpPasswordConfirmField.getText();

            signUp(firstName, lastName, middleName, address, idCode, password, passwordConfirmation);
        });
    }

    private void signUp(String firstName, String lastName, String middleName,
                        String address, String idCode, String password, String passwordConfirmation) {
        if (signUpValidation(firstName, lastName, middleName, address, idCode, password, passwordConfirmation)) {
            // add user to db
        }
    }

    private boolean signUpValidation(String firstName, String lastName, String middleName,
                                     String address, String idCode, String password, String passwordConfirmation) {
        if (!userDataValidator.validateUserName(firstName) ||
                !userDataValidator.validateUserName(lastName) ||
                !userDataValidator.validateUserName(middleName)) {
            signUpFirstNameField.setStyle(signUpFirstNameField.getStyle() + " -fx-border-color: red;");
            signUpLastNameField.setStyle(signUpLastNameField.getStyle() + " -fx-border-color: red;");
            signUpMiddleNameField.setStyle(signUpMiddleNameField.getStyle() + " -fx-border-color: red;");

            PopupDialogs.showErrorDialog(
                    "Input Error",
                    "One of your names is too short.",
                    "All your names should be at least 2 characters long and contain only letters."
            );

            return false;
        }

        if (!userDataValidator.validateUserAddress(address)) {
            signUpAddressField.setStyle(signUpAddressField.getStyle() + " -fx-border-color: red;");

            PopupDialogs.showErrorDialog(
                    "Input Error",
                    "Address isn't real",
                    "Address should be real."
            );

            return false;
        }

        if (!userDataValidator.validateUserIdCode(idCode)) {
            signUpIdCodeField.setStyle(signUpIdCodeField.getStyle() + " -fx-border-color: red;");

            PopupDialogs.showErrorDialog(
                    "Input Error",
                    "Invalid ID Code",
                    "ID Code should have the format ********-***** (where * is a digit)."
            );

            return false;
        }

        if (!userDataValidator.validateUserPassword(password)) {
            signUpPasswordField.setStyle(signUpPasswordField.getStyle() + " -fx-border-color: red;");

            PopupDialogs.showErrorDialog(
                    "Input Error",
                    "Invalid Password",
                    "Password should be at least 5 characters long and contain at least one digit."
            );

            return false;
        }

        if (!userDataValidator.comparePasswords(password, passwordConfirmation)) {
            signUpPasswordConfirmField.setStyle(signUpPasswordConfirmField.getStyle() + " -fx-border-color: red;");

            PopupDialogs.showErrorDialog(
                    "Input Error",
                    "Password Confirmation Mismatch",
                    "Password confirmation does not match the entered password."
            );

            return false;
        }

        return true;
    }
}
