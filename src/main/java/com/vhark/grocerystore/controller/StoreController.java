package com.vhark.grocerystore.controller;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import com.vhark.grocerystore.model.ProductLoader;
import com.vhark.grocerystore.model.MakePurchase;
import com.vhark.grocerystore.model.Product;
import com.vhark.grocerystore.model.exceptions.ExcessiveQuantityException;
import com.vhark.grocerystore.model.singletons.ProductDataSingleton;
import com.vhark.grocerystore.model.singletons.UserDataSingleton;
import com.vhark.grocerystore.util.PopupDialogs;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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

  @FXML private Label storeMarketMaxPriceLabel;

  @FXML private Label storeMarketMinQuantityLabel;

  @FXML private TableColumn<Product, String> storeMarketNameColumn;

  @FXML private TextField storeMarketNumberOfItemsField;

  @FXML private TableColumn<Product, BigDecimal> storeMarketPriceColumn;

  @FXML private Button storeMarketPurchaseButton;

  @FXML private TableColumn<Product, Integer> storeMarketQuantityColumn;

  @FXML private Button storeMarketResetButton;

  @FXML private Button storePurchasesReloadButton;

  @FXML private Button storeMarketSearchButton;

  @FXML private TextField storeMarketSearchField;

  @FXML private Label storeMarketSelectedProductLabel;

  @FXML private Tab storeMarketTabButton;

  @FXML private TableView<Product> storeMarketTableView;

  @FXML private Slider storeMarketSliderMaxPrice;

  @FXML private Slider storeMarketSliderMinQuantity;

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

    if (product == null) {
      return;
    }

    ProductDataSingleton productDataSingleton = ProductDataSingleton.getInstance();

    productDataSingleton.setProductId(product.getProductId());
    String productName = product.getProductName();

    storeMarketSelectedProductLabel.setText("Selected: " + productName);
  }

  @FXML
  void initialize() {
    maxPriceSliderInit(storeMarketSliderMaxPrice, storeMarketMaxPriceLabel);
    minQuantitySliderInit(storeMarketSliderMinQuantity, storeMarketMinQuantityLabel);
    numbersOfItemsFieldInit(storeMarketNumberOfItemsField);
    setMarketTableColumns();
    handleLoadDefaultProducts();

    storeMarketPurchaseButton.setOnAction(
        actionEvent -> purchaseButtonClicked(storeMarketNumberOfItemsField));

    storeMarketSearchButton.setOnAction(
        actionEvent ->
            searchButtonClicked(
                storeMarketSearchField, storeMarketSliderMaxPrice, storeMarketSliderMinQuantity));

    storeMarketResetButton.setOnAction(
        actionEvent ->
            resetButtonClicked(
                storeMarketSearchField,
                storeMarketSliderMaxPrice,
                storeMarketSliderMinQuantity,
                storeMarketMaxPriceLabel,
                storeMarketMinQuantityLabel));
  }

  private void setMarketTableColumns() {
    storeMarketNameColumn.setCellValueFactory(
        cellData -> Bindings.createObjectBinding(() -> cellData.getValue().getProductName()));

    storeMarketPriceColumn.setCellValueFactory(
        cellData -> Bindings.createObjectBinding(() -> cellData.getValue().getPrice()));

    storeMarketQuantityColumn.setCellValueFactory(
        cellData -> Bindings.createObjectBinding(() -> cellData.getValue().getQuantity()));
  }

  private void handleLoadDefaultProducts() {
    try {
      ProductLoader productLoader = new ProductLoader();
      storeMarketTableView.setItems(productLoader.loadDefaultProducts());
    } catch (SQLException e) {
      e.printStackTrace();
      PopupDialogs.showErrorDialog(
          "Error", "Something went wrong", "Something went wrong, try again later.");
    }
  }

  private void purchaseButtonClicked(TextField storeMarketNumberOfItemsField) {
    UserDataSingleton userDataSingleton = UserDataSingleton.getInstance();
    ProductDataSingleton productDataSingleton = ProductDataSingleton.getInstance();
    String numberOfItems = storeMarketNumberOfItemsField.getText();

    if (productDataSingleton.getProductId() == null) {
      PopupDialogs.showErrorDialog(
          "Error", "No Product Selected", "Please select a product before proceeding.");
      return;
    }

    if (numberOfItems.isEmpty() || numberOfItems.startsWith("0")) {
      PopupDialogs.showErrorDialog(
          "Error", "Quantity Not Entered", "Please enter the quantity before proceeding.");
      return;
    }

    int quantity = Integer.parseInt(numberOfItems);

    MakePurchase makePurchase =
        new MakePurchase(
            userDataSingleton.getIdCode(), productDataSingleton.getProductId(), quantity);

    try {
      makePurchase.addPurchaseToDb();
      PopupDialogs.showInformationDialog(
          "Success", "Purchase Completed", "Your purchase was successfully completed.");
      storeMarketNumberOfItemsField.clear();
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

  private void searchButtonClicked(
      TextField storeMarketSearchField,
      Slider storeMarketSliderMaxPrice,
      Slider storeMarketSliderMinQuantity) {
    String name = storeMarketSearchField.getText();
    double maxPrice = storeMarketSliderMaxPrice.getValue();
    double minQuantity = storeMarketSliderMinQuantity.getValue();

    try {
      ProductLoader productLoader = new ProductLoader();
      storeMarketTableView.setItems(
          productLoader.loadFilteredProducts(name, maxPrice, minQuantity));
    } catch (SQLException e) {
      e.printStackTrace();
      PopupDialogs.showErrorDialog(
          "Error", "Something went wrong", "Something went wrong, try again later.");
    }
  }

  private void resetButtonClicked(
      TextField storeMarketSearchField,
      Slider storeMarketSliderMaxPrice,
      Slider storeMarketSliderMinQuantity,
      Label storeMarketMaxPriceLabel,
      Label storeMarketMinQuantityLabel) {
    handleLoadDefaultProducts();
    storeMarketSearchField.clear();
    storeMarketSliderMaxPrice.setValue(getMaxPrice());
    storeMarketSliderMinQuantity.setValue(0.0);
    storeMarketMaxPriceLabel.setText("No limit");
    storeMarketMinQuantityLabel.setText("No limit");
  }

  private void numbersOfItemsFieldInit(TextField storeMarketNumberOfItemsField) {
    storeMarketNumberOfItemsField
        .textProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (!newValue.matches("\\d*")) {
                storeMarketNumberOfItemsField.setText(newValue.replaceAll("\\D", ""));
              }
            });
  }

  private void maxPriceSliderInit(
      Slider storeMarketSliderMaxPrice, Label storeMarketMaxPriceLabel) {
    double dbMaxPrice = getMaxPrice();

    storeMarketSliderMaxPrice.setMin(0.0);
    storeMarketSliderMaxPrice.setMax(dbMaxPrice);
    storeMarketSliderMaxPrice.setValue(dbMaxPrice);
    storeMarketSliderMaxPrice.setShowTickLabels(true);
    storeMarketSliderMaxPrice.setMajorTickUnit(1.0);
    storeMarketSliderMaxPrice.setMinorTickCount(1);
    storeMarketSliderMaxPrice.setBlockIncrement(0.05);

    storeMarketSliderMaxPrice
        .valueProperty()
        .addListener(
            ((observable, oldValue, newValue) -> {
              Double maxPriceValue = storeMarketSliderMaxPrice.getValue();
              storeMarketMaxPriceLabel.setText(new DecimalFormat("##.##").format(maxPriceValue));
            }));
  }

  private void minQuantitySliderInit(
      Slider storeMarketSliderMinQuantity, Label storeMarketMinQuantityLabel) {
    double maxQuantity = getMaxQuantity();

    storeMarketSliderMinQuantity.setMin(0.0);
    storeMarketSliderMinQuantity.setMax(maxQuantity);
    storeMarketSliderMinQuantity.setValue(0.0);
    storeMarketSliderMinQuantity.setShowTickLabels(true);
    storeMarketSliderMinQuantity.setMajorTickUnit(5.0);
    storeMarketSliderMinQuantity.setMinorTickCount(10);
    storeMarketSliderMinQuantity.setBlockIncrement(1.0);

    storeMarketSliderMinQuantity
        .valueProperty()
        .addListener(
            ((observable, oldValue, newValue) -> {
              Double minQuantityValue = storeMarketSliderMinQuantity.getValue();
              storeMarketMinQuantityLabel.setText(new DecimalFormat("#").format(minQuantityValue));
            }));
  }

  private double getMaxPrice() {
    try {
      ProductLoader productLoader = new ProductLoader();
      return productLoader.getMaxPrice();
    } catch (SQLException e) {
      e.printStackTrace();
      return 100.0;
    }
  }

  private double getMaxQuantity() {
    try {
      ProductLoader productLoader = new ProductLoader();
      return productLoader.getMaxQuantity();
    } catch (SQLException e) {
      e.printStackTrace();
      return 500.0;
    }
  }
}
