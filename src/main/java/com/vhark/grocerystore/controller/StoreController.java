package com.vhark.grocerystore.controller;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.vhark.grocerystore.model.dao.MakePurchase;
import com.vhark.grocerystore.model.dao.TablePurchaseLoader;
import com.vhark.grocerystore.model.entities.Product;
import com.vhark.grocerystore.model.entities.TablePurchase;
import com.vhark.grocerystore.model.exceptions.ExcessiveQuantityException;
import com.vhark.grocerystore.model.singletons.ProductDataSingleton;
import com.vhark.grocerystore.model.singletons.UserDataSingleton;
import com.vhark.grocerystore.util.PopupDialogs;
import com.vhark.grocerystore.util.WindowSwitcher;
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

  @FXML private TableColumn<TablePurchase, Date> storePurchasesDateColumn;

  @FXML private TableColumn<TablePurchase, String> storePurchasesNameColumn;

  @FXML private TableColumn<TablePurchase, Double> storePurchasesPriceColumn;

  @FXML private TableColumn<TablePurchase, Integer> storePurchasesQuantityColumn;

  @FXML private Tab storePurchasesTabButton;

  @FXML private TableView<TablePurchase> storePurchasesTableView;

  @FXML private Button storeSettingsQuitButton;

  @FXML private Tab storeSettingsTabButton;

  @FXML
  void selectItem(MouseEvent event) {
    Product product = storeMarketTableView.getSelectionModel().getSelectedItem();

    if (product != null) {
      ProductDataSingleton productDataSingleton = ProductDataSingleton.getInstance();
      productDataSingleton.setProductId(product.getProductId());
      storeMarketSelectedProductLabel.setText("Selected: " + product.getProductName());
    }
  }

  @FXML
  void initialize() {
    ProductSearchControllerUtil.maxProductPriceSliderInit(
            storeMarketSliderMaxPrice, storeMarketMaxPriceLabel);
    ProductSearchControllerUtil.minProductQuantitySliderInit(
            storeMarketSliderMinQuantity, storeMarketMinQuantityLabel);
    numbersOfItemsFieldInit(storeMarketNumberOfItemsField);

    ProductSearchControllerUtil.setProductTableColumns(
            storeMarketNameColumn, storeMarketPriceColumn, storeMarketQuantityColumn);
    setPurchasesTableColumns();

    ProductSearchControllerUtil.loadProducts(
            storeMarketSearchField,
            storeMarketSliderMaxPrice,
            storeMarketSliderMinQuantity,
            storeMarketTableView);
    handleLoadPurchases(storePurchasesTableView);

    storeMarketSearchButton.setOnAction(
            actionEvent ->
                    ProductSearchControllerUtil.loadProducts(
                            storeMarketSearchField,
                            storeMarketSliderMaxPrice,
                            storeMarketSliderMinQuantity,
                            storeMarketTableView));

    storeMarketResetButton.setOnAction(
            actionEvent ->
                    ProductSearchControllerUtil.resetProductFilter(
                            storeMarketSearchField,
                            storeMarketSliderMaxPrice,
                            storeMarketSliderMinQuantity,
                            storeMarketMaxPriceLabel,
                            storeMarketMinQuantityLabel,
                            storeMarketTableView));

    storeMarketPurchaseButton.setOnAction(
            actionEvent ->
                    purchaseButtonClicked(
                            storeMarketNumberOfItemsField,
                            storeMarketSearchField,
                            storeMarketSliderMaxPrice,
                            storeMarketSliderMinQuantity,
                            storeMarketTableView,
                            storePurchasesTableView));

    storePurchasesReloadButton.setOnAction(
            actionEvent -> reloadButtonCLicked(storePurchasesTableView));

    storeSettingsQuitButton.setOnAction(actionEvent -> quitButtonClicked(storeSettingsQuitButton));
  }

  private void setPurchasesTableColumns() {
    storePurchasesNameColumn.setCellValueFactory(
            cellData -> Bindings.createObjectBinding(() -> cellData.getValue().getProductName()));

    storePurchasesPriceColumn.setCellValueFactory(
            cellData -> Bindings.createObjectBinding(() -> cellData.getValue().getTotalCost()));

    storePurchasesQuantityColumn.setCellValueFactory(
            cellData -> Bindings.createObjectBinding(() -> cellData.getValue().getQuantityPurchased()));

    storePurchasesDateColumn.setCellValueFactory(
            cellData -> Bindings.createObjectBinding(() -> cellData.getValue().getPurchaseDate()));
  }

  private void purchaseButtonClicked(
          TextField storeMarketNumberOfItemsField,
          TextField storeMarketSearchField,
          Slider storeMarketSliderMaxPrice,
          Slider storeMarketSliderMinQuantity,
          TableView<Product> storeMarketTableView,
          TableView<TablePurchase> storePurchasesTableView) {

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

      ProductSearchControllerUtil.loadProducts(
              storeMarketSearchField,
              storeMarketSliderMaxPrice,
              storeMarketSliderMinQuantity,
              storeMarketTableView);

      handleLoadPurchases(storePurchasesTableView);
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

  private void handleLoadPurchases(TableView<TablePurchase> storePurchasesTableView) {
    UserDataSingleton userDataSingleton = UserDataSingleton.getInstance();
    String userCode = userDataSingleton.getIdCode();

    try {
      storePurchasesTableView.setItems(TablePurchaseLoader.loadPurchases(userCode));
    } catch (SQLException e) {
      e.printStackTrace();
      PopupDialogs.showErrorDialog(
              "Error", "Something went wrong", "Something went wrong, try again later.");
    }
  }

  private void reloadButtonCLicked(TableView<TablePurchase> storePurchasesTableView) {
    handleLoadPurchases(storePurchasesTableView);

    PopupDialogs.showInformationDialog(
            "Success", "Purchases Reloaded", "The list of purchases has been successfully reloaded.");
  }

  private void quitButtonClicked(Button storeSettingsQuitButton) {
    UserDataSingleton userDataSingleton = UserDataSingleton.getInstance();
    ProductDataSingleton productDataSingleton = ProductDataSingleton.getInstance();

    userDataSingleton.setIdCode(null);
    userDataSingleton.setIsEmployee(false);

    productDataSingleton.setProductId(null);

    WindowSwitcher.switchWindow(
            storeSettingsQuitButton, "view/LogInPage.fxml", "Grocery Store Log In");
  }
}
