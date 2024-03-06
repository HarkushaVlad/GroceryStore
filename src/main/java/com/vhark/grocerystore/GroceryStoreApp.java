package com.vhark.grocerystore;

import com.vhark.grocerystore.util.WindowSwitcher;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GroceryStoreApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        WindowSwitcher.showWindow("view/LogInPage.fxml", "Grocery Store Log In");
    }

    public static void main(String[] args) {
        launch();
    }
}