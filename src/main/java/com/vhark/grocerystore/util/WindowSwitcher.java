package com.vhark.grocerystore.util;

import com.vhark.grocerystore.GroceryStoreApp;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public final class WindowSwitcher {

    private WindowSwitcher(){};

    public static void switchWindow(javafx.scene.control.Labeled triggerElement, String fxmlResourcePath, String title) {
        triggerElement.getScene().getWindow().hide();
        WindowSwitcher.showWindow(fxmlResourcePath, title);
    }

    public static void showWindow(String fxmlResourcePath, String title) {
        FXMLLoader fxmlLoader = new FXMLLoader(GroceryStoreApp.class.getResource(fxmlResourcePath));

        try {
            Scene scene = new Scene(fxmlLoader.load());

            Stage stage = new Stage();

            stage.setTitle(title);
            stage.setMinHeight(583);
            stage.setMinWidth(913);
            stage.setMaxHeight(583);
            stage.setMaxWidth(913);

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
