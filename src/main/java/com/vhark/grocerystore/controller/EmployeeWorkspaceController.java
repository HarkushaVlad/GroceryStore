package com.vhark.grocerystore.controller;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.vhark.grocerystore.model.dao.ProductEditor;
import com.vhark.grocerystore.model.entities.Product;
import com.vhark.grocerystore.model.singletons.ProductDataSingleton;
import com.vhark.grocerystore.model.singletons.UserDataSingleton;
import com.vhark.grocerystore.util.PopupDialogs;
import com.vhark.grocerystore.util.ProductDataValidator;
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

import static java.lang.Integer.parseInt;

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

  @FXML private Button workspaceDeleteProductButton;

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

  @FXML private Label workspaceProductsEditSelectedProductNameLabel1;

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
      workspaceProductsEditSelectedProductNameLabel1.setText(
          "Selected: " + product.getProductName());
      workspaceProductsEditProductPriceLabel.setText("Product price: " + product.getPrice());
      workspaceProductsEditProductQuantityLabel.setText(
          "Product quantity: " + product.getQuantity());
    }
  }

  private void slidersInit() {
    ProductSearchControllerUtil.maxProductPriceSliderInit(
            workspaceProductsSliderMaxPrice, workspaceProductsMaxPriceLabel);
    ProductSearchControllerUtil.minProductQuantitySliderInit(
            workspaceProductsSliderMinQuantity, workspaceProductsMinQuantityLabel);
  }

  @FXML
  void initialize() {
    slidersInit();

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

    workspaceDeleteProductButton.setOnAction(
        actionEvent -> deleteProductButtonClicked(workspaceProductsSearchButton));

    workspaceProductsSaveEditButton.setOnAction(
        actionEvent ->
            saveEditButtonClicked(
                workspaceProductsEditProductNameField,
                workspaceProductsEditProductPriceField,
                workspaceProductsEditProductQuantityField,
                workspaceProductsSearchButton));

    workspaceProductsAddProductButton.setOnAction(
        actionEvent ->
            addProductButtonClicked(
                workspaceProductsAddProductNameField,
                workspaceProductsAddProductPriceField,
                workspaceProductsAddProductQuantityField,
                workspaceProductsSearchButton));

    workspaceSettingsQuitButton.setOnAction(
        actionEvent -> quitButtonClicked(workspaceSettingsQuitButton));
  }

  private void deleteProductButtonClicked(Button productsSearchButton) {
    String productId = ProductDataSingleton.getInstance().getProductId();

    if (isProductNotSelected(productId)) {
      return;
    }

    try {
      ProductEditor.deleteProduct(productId);

      productsSearchButton.fire();

      PopupDialogs.showInformationDialog(
          "Success", "Product Delete", "Product  has been successfully deleted");
    } catch (SQLException e) {
      e.printStackTrace();
      PopupDialogs.showErrorDialog(
          "Error", "Something went wrong", "Something went wrong, try again later.");
    }
  }

  private void saveEditButtonClicked(
      TextField productNameField,
      TextField productPriceField,
      TextField productQuantityField,
      Button productsSearchButton) {
    String productId = ProductDataSingleton.getInstance().getProductId();
    String productName = productNameField.getText();
    String productPrice = productPriceField.getText();
    String productQuantity = productQuantityField.getText();

    if (productId == null) {
      PopupDialogs.showErrorDialog(
          "Error", "No Product Selected", "Please select a product before proceeding.");
      return;
    }

    if (isInvalidProductDataInput(productNameField, productPriceField, productQuantityField)) {
      return;
    }

    try {
      ProductEditor.editProduct(productId, productName, productPrice, productQuantity);

      slidersInit();
      productsSearchButton.fire();

      PopupDialogs.showInformationDialog(
          "Success",
          "Product Update",
          "Product data (" + productName + ") has been successfully updated");

      clearProductDataInputFields(productNameField, productPriceField, productQuantityField);

      removeErrorStyle(productNameField);
      removeErrorStyle(productPriceField);
      removeErrorStyle(productQuantityField);
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
      PopupDialogs.showErrorDialog("Error", "Illegal arguments", "Invalid product data input");
    } catch (SQLException e) {
      e.printStackTrace();
      PopupDialogs.showErrorDialog(
          "Error", "Something went wrong", "Something went wrong, try again later.");
    }
  }

  private void addProductButtonClicked(
      TextField productNameField,
      TextField productPriceField,
      TextField productQuantityField,
      Button productsSearchButton) {
    String productName = productNameField.getText();
    String productPrice = productPriceField.getText();
    String productQuantity = productQuantityField.getText();

    if (isInvalidProductDataInput(productNameField, productPriceField, productQuantityField)) {
      return;
    }

    try {
      ProductEditor.addProduct(productName, productPrice, productQuantity);

      slidersInit();
      productsSearchButton.fire();

      PopupDialogs.showInformationDialog(
          "Success", "Product Add", "Product " + productName + " has been successfully added");

      clearProductDataInputFields(productNameField, productPriceField, productQuantityField);

      removeErrorStyle(productNameField);
      removeErrorStyle(productPriceField);
      removeErrorStyle(productQuantityField);
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
      PopupDialogs.showErrorDialog("Error", "Illegal arguments", "Invalid product data input");
    } catch (SQLException e) {
      e.printStackTrace();
      PopupDialogs.showErrorDialog(
          "Error", "Something went wrong", "Something went wrong, try again later.");
    }
  }

  private boolean isProductNotSelected(String productId) {
    if (productId == null) {
      PopupDialogs.showErrorDialog(
          "Error", "No Product Selected", "Please select a product before proceeding.");
      return true;
    }

    return false;
  }

  private boolean isInvalidProductDataInput(
      TextField productNameField, TextField productPriceField, TextField productQuantityField) {
    String productName = productNameField.getText();
    String productPrice = productPriceField.getText();
    String productQuantity = productQuantityField.getText();

    if (!ProductDataValidator.validateProductName(productName)) {
      applyErrorStyle(productNameField);

      PopupDialogs.showErrorDialog(
          "Validation Error",
          "Invalid Product Name",
          "Product name should start with a letter and have at least two characters.");

      return true;
    }

    if (!ProductDataValidator.validateProductPrice(productPrice)) {
      applyErrorStyle(productPriceField);

      PopupDialogs.showErrorDialog(
          "Validation Error",
          "Invalid Product Price",
          "Price should be a number with up to two decimal places and lower than 10000.");

      return true;
    }

    if (!ProductDataValidator.validateProductQuantity(productQuantity)) {
      applyErrorStyle(productQuantityField);

      PopupDialogs.showErrorDialog(
          "Validation Error",
          "Invalid Product Quantity",
          "Quantity should be a non-negative integer and lower than 10000.");

      return true;
    }

    return false;
  }

  private void clearProductDataInputFields(
      TextField productNameField, TextField productPriceField, TextField productQuantityField) {
    productNameField.clear();
    productPriceField.clear();
    productQuantityField.clear();
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

  private void applyErrorStyle(TextField textField) {
    textField.setStyle(textField.getStyle() + " -fx-border-color: red;");
  }

  private void removeErrorStyle(TextField textField) {
    String currentStyle = textField.getStyle();
    String newStyle = currentStyle.replace("-fx-border-color: red;", "");
    textField.setStyle(newStyle);
  }
}
