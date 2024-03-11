package com.vhark.grocerystore.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class StoreController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private CheckBox storeMarketCheckBoxInStock;

    @FXML
    private CheckBox storeMarketCheckBoxOutOfStock;

    @FXML
    private Label storeMarketMaxPriceLabel;

    @FXML
    private TableColumn<?, ?> storeMarketNameColumn;

    @FXML
    private TextField storeMarketNumberOfItemsField;

    @FXML
    private TableColumn<?, ?> storeMarketPriceColumn;

    @FXML
    private Button storeMarketPurchaseButton;

    @FXML
    private TableColumn<?, ?> storeMarketQuantityColumn;

    @FXML
    private Button storeMarketReloadButton;

    @FXML
    private Button storeMarketReloadButton1;

    @FXML
    private Button storeMarketSearchButton;

    @FXML
    private TextField storeMarketSearchField;

    @FXML
    private Tab storeMarketTabButton;

    @FXML
    private TableView<?> storeMarketTableView;

    @FXML
    private Slider storeMatketSlider;

    @FXML
    private TableColumn<?, ?> storePurchasesDateColumn;

    @FXML
    private TableColumn<?, ?> storePurchasesNameColumn;

    @FXML
    private TableColumn<?, ?> storePurchasesPriceColumn;

    @FXML
    private TableColumn<?, ?> storePurchasesQuantityColumn;

    @FXML
    private Tab storePurchasesTabButton;

    @FXML
    private TableView<?> storePurchasesTableView;

    @FXML
    private Button storeSettingsQuitButton;

    @FXML
    private Tab storeSettingsTabButton;

    @FXML
    void initialize() {

    }

}
