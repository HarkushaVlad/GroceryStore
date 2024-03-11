package com.vhark.grocerystore.controller;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.vhark.grocerystore.model.LoadProducts;
import com.vhark.grocerystore.model.MakePurchase;
import com.vhark.grocerystore.model.Product;
import com.vhark.grocerystore.model.exceptions.ExcessiveQuantityException;
import com.vhark.grocerystore.model.exceptions.ZeroQuantityException;
import com.vhark.grocerystore.model.singletons.ProductDataSingleton;
import com.vhark.grocerystore.model.singletons.UserDataSingleton;
import com.vhark.grocerystore.util.PopupDialogs;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class StoreController {

  @FXML private ResourceBundle resources;

  @FXML private URL location;

  @FXML private CheckBox storeMarketCheckBoxInStock;

  @FXML private CheckBox storeMarketCheckBoxOutOfStock;

  @FXML private Label storeMarketMaxPriceLabel;

  @FXML private TableColumn<Product, String> storeMarketNameColumn;

  @FXML private TextField storeMarketNumberOfItemsField;

  @FXML private TableColumn<Product, BigDecimal> storeMarketPriceColumn;

  @FXML private Button storeMarketPurchaseButton;

  @FXML private TableColumn<Product, Integer> storeMarketQuantityColumn;

  @FXML private Button storeMarketReloadButton;

  @FXML private Button storePurchasesReloadButton;

  @FXML private Button storeMarketSearchButton;

  @FXML private TextField storeMarketSearchField;

  @FXML private Label storeMarketSelectedProductLabel;

  @FXML private Tab storeMarketTabButton;

  @FXML private TableView<Product> storeMarketTableView;

  @FXML private Slider storeMatketSlider;

  @FXML private TableColumn<?, ?> storePurchasesDateColumn;

  @FXML private TableColumn<?, ?> storePurchasesNameColumn;

  @FXML private TableColumn<?, ?> storePurchasesPriceColumn;

  @FXML private TableColumn<?, ?> storePurchasesQuantityColumn;

  @FXML private Tab storePurchasesTabButton;

  @FXML private TableView<?> storePurchasesTableView;

  @FXML private Button storeSettingsQuitButton;

  @FXML private Tab storeSettingsTabButton;

  @FXML
  void selectItem(MouseEvent event) {
    Product product = storeMarketTableView.getSelectionModel().getSelectedItem();

    ProductDataSingleton productDataSingleton = ProductDataSingleton.getInstance();

    productDataSingleton.setProductId(product.getProductId());
    String productName = product.getProductName();

    storeMarketSelectedProductLabel.setText("Selected: " + productName);
  }

  @FXML
  void initialize() {
    numbersOfItemsInit();
    setMarketTableColumns();
    loadDefaultProducts();

    storeMarketPurchaseButton.setOnAction(actionEvent -> handleMakePurchase());
  }

  private void setMarketTableColumns() {
    storeMarketNameColumn.setCellValueFactory(
        cellData -> Bindings.createObjectBinding(() -> cellData.getValue().getProductName()));
    storeMarketPriceColumn.setCellValueFactory(
        cellData -> Bindings.createObjectBinding(() -> cellData.getValue().getPrice()));
    storeMarketQuantityColumn.setCellValueFactory(
        cellData -> Bindings.createObjectBinding(() -> cellData.getValue().getQuantity()));
  }

  private void loadDefaultProducts() {
    try {
      storeMarketTableView.setItems(LoadProducts.getDefaultProducts());
    } catch (SQLException e) {
      e.printStackTrace();
      PopupDialogs.showErrorDialog(
          "Error", "Something went wrong", "Something went wrong, try again later.");
    }
  }

  private void handleMakePurchase() {
    UserDataSingleton userDataSingleton = UserDataSingleton.getInstance();
    ProductDataSingleton productDataSingleton = ProductDataSingleton.getInstance();

    if (productDataSingleton.getProductId() == null) {
      PopupDialogs.showErrorDialog(
          "Error", "No Product Selected", "Please select a product before proceeding.");
      return;
    }

    if (storeMarketNumberOfItemsField.getText().isEmpty()) {
      PopupDialogs.showErrorDialog(
          "Error", "Quantity Not Entered", "Please enter the quantity before proceeding.");
      return;
    }

    int quantity = Integer.parseInt(storeMarketNumberOfItemsField.getText());

    MakePurchase makePurchase =
        new MakePurchase(
            userDataSingleton.getIdCode(), productDataSingleton.getProductId(), quantity);

    try {
      makePurchase.addPurchaseToDb();
      PopupDialogs.showInformationDialog(
          "Success", "Purchase Completed", "Your purchase was successfully completed.");
    } catch (ZeroQuantityException e) {
      PopupDialogs.showErrorDialog(
          "Error", "Zero Quantity", "Quantity should be greater than zero.");
    } catch (ExcessiveQuantityException e) {
      PopupDialogs.showErrorDialog(
          "Error",
          "Excessive Quantity",
          "The quantity of selected products exceeds the available stock");
    } catch (SQLException e) {
      e.printStackTrace();
      PopupDialogs.showErrorDialog(
          "Error", "Something went wrong", "Something went wrong, try again later.");
    }
  }

  private void numbersOfItemsInit() {
    storeMarketNumberOfItemsField
        .textProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (!newValue.matches("\\d*")) {
                storeMarketNumberOfItemsField.setText(newValue.replaceAll("\\D", ""));
              }
            });
  }
}
