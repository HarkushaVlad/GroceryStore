module com.vhark.grocerystore {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.vhark.grocerystore to javafx.fxml;
    exports com.vhark.grocerystore;
}