package com.vhark.grocerystore;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GroceryStoreApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GroceryStoreApp.class.getResource("view/logInPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 550);

        stage.setMinHeight(550);
        stage.setMinWidth(910);
        stage.setMaxHeight(550);
        stage.setMaxWidth(910);

        stage.setTitle("Grocery Store");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}