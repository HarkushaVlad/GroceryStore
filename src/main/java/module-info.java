module com.vhark.grocerystore {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.vhark.grocerystore to javafx.fxml;
    exports com.vhark.grocerystore;
    exports com.vhark.grocerystore.controller;
    opens com.vhark.grocerystore.controller to javafx.fxml;
}