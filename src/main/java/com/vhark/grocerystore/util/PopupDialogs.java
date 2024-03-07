package com.vhark.grocerystore.util;

import javafx.scene.control.Alert;

public final class PopupDialogs {

    private PopupDialogs(){};

    public static void showIformationDialog(String titleBar, String headerMessage, String infoMessage) {
        showPopupDialog(Alert.AlertType.INFORMATION, titleBar, headerMessage, infoMessage);
    }

    public static void showErrorDialog(String titleBar, String headerMessage, String infoMessage) {
        showPopupDialog(Alert.AlertType.ERROR, titleBar, headerMessage, infoMessage);
    }

    public static void showWarningDialog(String titleBar, String headerMessage, String infoMessage) {
        showPopupDialog(Alert.AlertType.WARNING, titleBar, headerMessage, infoMessage);
    }

    private static void showPopupDialog(Alert.AlertType alertType, String titleBar, String headerMessage, String infoMessage) {
        Alert alert = new Alert(alertType);
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);
        alert.showAndWait();
    }
}
