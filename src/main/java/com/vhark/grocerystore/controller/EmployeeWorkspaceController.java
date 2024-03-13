package com.vhark.grocerystore.controller;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

import com.vhark.grocerystore.model.entities.Product;
import com.vhark.grocerystore.model.singletons.ProductDataSingleton;
import com.vhark.grocerystore.model.singletons.UserDataSingleton;
import com.vhark.grocerystore.util.WindowSwitcher;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class EmployeeWorkspaceController {

  @FXML private ResourceBundle resources;

  @FXML private URL location;

  @FXML private Label workspaceClientAddressLabel;

  @FXML private Label workspaceClientFirstNameLabel;

  @FXML private TableColumn<?, ?> workspaceClientIdCodeColumn;

  @FXML private Label workspaceClientIdCodeLabel;

  @FXML private Label workspaceClientLastNameLabel;

  @FXML private Label workspaceClientMiddleNameLabel;

  @FXML private TableColumn<?, ?> workspaceClientPurchasesDateColumn;

  @FXML private TableColumn<?, ?> workspaceClientPurchasesNameColumn;

  @FXML private TableColumn<?, ?> workspaceClientPurchasesPriceColumn;

  @FXML private TableColumn<?, ?> workspaceClientPurchasesQuantityColumn;

  @FXML private TableView<?> workspaceClientPurchasesTableView;

  @FXML private Tab workspaceClientsTabButton;

  @FXML private TableView<?> workspaceClientsTableView;

  @FXML private Button workspaceDeleteClientButton;

  @FXML private Button workspaceProductsAddProductButton;

  @FXML private TextField workspaceProductsAddProductNameField;

  @FXML private TextField workspaceProductsAddProductPriceField;

  @FXML private TextField workspaceProductsAddProductQuantityField;

  @FXML private TextField workspaceProductsEditProductNameField;

  @FXML private TextField workspaceProductsEditProductPriceField;

  @FXML private Label workspaceProductsEditProductPriceLabel;

  @FXML private TextField workspaceProductsEditProductQuantityField;

  @FXML private Label workspaceProductsEditProductQuantityLabel;

  @FXML private Label workspaceProductsEditSelectedProductNameLabel;

  @FXML private Label workspaceProductsMaxPriceLabel;

  @FXML private Label workspaceProductsMinQuantityLabel;

  @FXML private TableColumn<Product, String> workspaceProductsNameColumn;

  @FXML private TableColumn<Product, BigDecimal> workspaceProductsPriceColumn;

  @FXML private TableColumn<Product, Integer> workspaceProductsQuantityColumn;

  @FXML private Button workspaceProductsResetButton;

  @FXML private Button workspaceProductsSaveEditButton;

  @FXML private Button workspaceProductsSearchButton;

  @FXML private TextField workspaceProductsSearchField;

  @FXML private Slider workspaceProductsSliderMaxPrice;

  @FXML private Slider workspaceProductsSliderMinQuantity;

  @FXML private Tab workspaceProductsTabButton;

  @FXML private TableView<Product> workspaceProductsTableView;

  @FXML private Button workspaceSettingsQuitButton;

  @FXML private Tab workspaceSettingsTabButton;

  @FXML
  void selectItem(MouseEvent event) {
    Product product = workspaceProductsTableView.getSelectionModel().getSelectedItem();

    if (product != null) {
      ProductDataSingleton productDataSingleton = ProductDataSingleton.getInstance();
      productDataSingleton.setProductId(product.getProductId());
      workspaceProductsEditSelectedProductNameLabel.setText(
          "Selected: " + product.getProductName());
    }
  }

  @FXML
  void initialize() {
    ProductSearchControllerUtil.maxProductPriceSliderInit(
        workspaceProductsSliderMaxPrice, workspaceProductsMaxPriceLabel);
    ProductSearchControllerUtil.minProductQuantitySliderInit(
        workspaceProductsSliderMinQuantity, workspaceProductsMinQuantityLabel);

    ProductSearchControllerUtil.setProductTableColumns(
        workspaceProductsNameColumn, workspaceProductsPriceColumn, workspaceProductsQuantityColumn);

    ProductSearchControllerUtil.loadProducts(
        workspaceProductsSearchField,
        workspaceProductsSliderMaxPrice,
        workspaceProductsSliderMinQuantity,
        workspaceProductsTableView);

    workspaceProductsSearchButton.setOnAction(
        actionEvent ->
            ProductSearchControllerUtil.loadProducts(
                workspaceProductsSearchField,
                workspaceProductsSliderMaxPrice,
                workspaceProductsSliderMinQuantity,
                workspaceProductsTableView));

    workspaceProductsResetButton.setOnAction(
        actionEvent ->
            ProductSearchControllerUtil.resetProductFilter(
                workspaceProductsSearchField,
                workspaceProductsSliderMaxPrice,
                workspaceProductsSliderMinQuantity,
                workspaceProductsMaxPriceLabel,
                workspaceProductsMinQuantityLabel,
                workspaceProductsTableView));

    workspaceSettingsQuitButton.setOnAction(
        actionEvent -> quitButtonClicked(workspaceSettingsQuitButton));
  }

  private void quitButtonClicked(Button workspaceSettingsQuitButton) {
    UserDataSingleton userDataSingleton = UserDataSingleton.getInstance();
    ProductDataSingleton productDataSingleton = ProductDataSingleton.getInstance();

    userDataSingleton.setIdCode(null);
    userDataSingleton.setIsEmployee(false);

    productDataSingleton.setProductId(null);

    WindowSwitcher.switchWindow(
        workspaceSettingsQuitButton, "view/LogInPage.fxml", "Grocery Store Log In");
  }
}
