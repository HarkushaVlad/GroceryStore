package com.vhark.grocerystore.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.vhark.grocerystore.model.singletons.UserDataSingleton;
import com.vhark.grocerystore.model.dao.LogInUser;
import com.vhark.grocerystore.model.exceptions.FailedLogInException;
import com.vhark.grocerystore.util.PopupDialogs;
import com.vhark.grocerystore.util.WindowSwitcher;
import com.vhark.grocerystore.util.userDataValidator;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LogInController {

  @FXML private ResourceBundle resources;

  @FXML private URL location;

  @FXML private Button logInButton;

  @FXML private TextField logInIdCodeField;

  @FXML private Label logInLabelSwitchToSignUp;

  @FXML private PasswordField logInPasswordField;

  @FXML
  void initialize() {
    logInLabelSwitchToSignUp.setOnMouseClicked(
        mouseEvent -> {
          WindowSwitcher.switchWindow(
              logInLabelSwitchToSignUp, "view/SignUpPage.fxml", "Grocery Store Sign Up");
        });

    logInButton.setOnAction(actionEvent -> handleLogIn());
  }

  private void handleLogIn() {
    String idCode = logInIdCodeField.getText();
    String password = logInPasswordField.getText();

    if (logInValidation(idCode, password)) {
      LogInUser logInUser = new LogInUser(idCode, password);

      try {
        logInUser.logIn();

        UserDataSingleton userDataSingleton = UserDataSingleton.getInstance();

        if (userDataSingleton.getIsEmployee()) {
          // employee scene
        } else {
          WindowSwitcher.switchWindow(logInButton, "view/store.fxml", "Grocery Store");
        }
      } catch (FailedLogInException e) {
        applyErrorStyle(logInIdCodeField);
        applyErrorStyle(logInPasswordField);

        PopupDialogs.showErrorDialog(
            "Error",
            "Invalid credentials",
            "Please provide the correct identification code or password.");
      } catch (SQLException e) {
        e.printStackTrace();
        PopupDialogs.showErrorDialog(
            "Error", "Something went wrong", "Something went wrong, try again later.");
      }
    }
  }

  private boolean logInValidation(String idCode, String password) {
    if (!userDataValidator.validateUserIdCode(idCode)) {
      applyErrorStyle(logInIdCodeField);

      PopupDialogs.showErrorDialog(
          "Input Error",
          "Invalid ID Code",
          "ID Code should have the format ********-***** (where * is a digit).");

      return false;
    }

    if (!userDataValidator.validateUserPassword(password)) {
      applyErrorStyle(logInPasswordField);

      PopupDialogs.showErrorDialog(
          "Input Error",
          "Invalid Password",
          "Password should be at least 5 characters long and contain at least one digit.");

      return false;
    }

    return true;
  }

  private void applyErrorStyle(TextField textField) {
    textField.setStyle(textField.getStyle() + " -fx-border-color: red;");
  }
}
