package com.vhark.grocerystore.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.vhark.grocerystore.model.dao.SignUpNewUser;
import com.vhark.grocerystore.model.exceptions.UserExistsException;
import com.vhark.grocerystore.util.PopupDialogs;
import com.vhark.grocerystore.util.WindowSwitcher;
import com.vhark.grocerystore.util.userDataValidator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignUpController {

  @FXML private ResourceBundle resources;

  @FXML private URL location;

  @FXML private Button SignUpButton;

  @FXML private TextField signUpAddressField;

  @FXML private TextField signUpFirstNameField;

  @FXML private TextField signUpIdCodeField;

  @FXML private Label signUpLabelSwitchToLogIn;

  @FXML private TextField signUpLastNameField;

  @FXML private TextField signUpMiddleNameField;

  @FXML private PasswordField signUpPasswordConfirmField;

  @FXML private PasswordField signUpPasswordField;

  @FXML
  void initialize() {
    signUpLabelSwitchToLogIn.setOnMouseClicked(
        mouseEvent -> {
          WindowSwitcher.switchWindow(
              signUpLabelSwitchToLogIn, "view/LogInPage.fxml", "Grocery Store Log In");
        });

    SignUpButton.setOnAction(actionEvent -> handleSignUp());
  }

  private void handleSignUp() {
    String firstName = signUpFirstNameField.getText().trim();
    String lastName = signUpLastNameField.getText().trim();
    String middleName = signUpMiddleNameField.getText().trim();
    String idCode = signUpIdCodeField.getText().trim();
    String address = signUpAddressField.getText().trim();
    String password = signUpPasswordField.getText();
    String passwordConfirmation = signUpPasswordConfirmField.getText();

    if (signUpValidation(
        firstName, lastName, middleName, idCode, address, password, passwordConfirmation)) {
      SignUpNewUser signUpNewUser =
          new SignUpNewUser(firstName, lastName, middleName, idCode, address, password);

      try {
        signUpNewUser.addNewUserToDb();
        PopupDialogs.showInformationDialog(
                "Registration Successful",
                "Congratulations!",
                "Your registration was successful. You can now proceed to log in with your credentials.");
        WindowSwitcher.switchWindow(
            signUpLabelSwitchToLogIn, "view/LogInPage.fxml", "Grocery Store Log In");
      } catch (UserExistsException e) {
        PopupDialogs.showErrorDialog(
            "Error", "User already exists", "A user with this identification code already exists.");
      } catch (IllegalArgumentException e) {
        PopupDialogs.showErrorDialog(
            "Error", "Invalid input data", "Please check your input data.");
      } catch (SQLException e) {
        e.printStackTrace();
        PopupDialogs.showErrorDialog(
            "Error", "Something went wrong", "Something went wrong, try again later.");
      }
    }
  }

  private boolean signUpValidation(
      String firstName,
      String lastName,
      String middleName,
      String idCode,
      String address,
      String password,
      String passwordConfirmation) {
    if (!userDataValidator.validateUserName(firstName)
        || !userDataValidator.validateUserName(lastName)
        || !userDataValidator.validateUserName(middleName)) {
      applyErrorStyle(signUpFirstNameField);
      applyErrorStyle(signUpLastNameField);
      applyErrorStyle(signUpMiddleNameField);

      PopupDialogs.showErrorDialog(
          "Input Error",
          "One of your names is too short.",
          "All your names should be at least 2 characters long and contain only letters.");

      return false;
    }

    if (!userDataValidator.validateUserIdCode(idCode)) {
      applyErrorStyle(signUpIdCodeField);

      PopupDialogs.showErrorDialog(
          "Input Error",
          "Invalid ID Code",
          "ID Code should have the format ********-***** (where * is a digit).");

      return false;
    }

    if (!userDataValidator.validateUserAddress(address)) {
      applyErrorStyle(signUpAddressField);

      PopupDialogs.showErrorDialog("Input Error", "Address isn't real", "Address should be real.");

      return false;
    }

    if (!userDataValidator.validateUserPassword(password)) {
      applyErrorStyle(signUpPasswordField);

      PopupDialogs.showErrorDialog(
          "Input Error",
          "Invalid Password",
          "Password should be at least 5 characters long and contain at least one digit.");

      return false;
    }

    if (!userDataValidator.comparePasswords(password, passwordConfirmation)) {
      applyErrorStyle(signUpPasswordConfirmField);

      PopupDialogs.showErrorDialog(
          "Input Error",
          "Password Confirmation Mismatch",
          "Password confirmation does not match the entered password.");

      return false;
    }

    return true;
  }

  private void applyErrorStyle(TextField textField) {
    textField.setStyle(textField.getStyle() + " -fx-border-color: red;");
  }
}
