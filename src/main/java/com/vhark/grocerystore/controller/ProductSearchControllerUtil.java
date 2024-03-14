package com.vhark.grocerystore.controller;

import com.vhark.grocerystore.model.dao.ProductLoader;
import com.vhark.grocerystore.model.entities.Product;
import com.vhark.grocerystore.util.PopupDialogs;
import javafx.beans.binding.Bindings;
import javafx.scene.control.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;

public final class ProductSearchControllerUtil {

    private ProductSearchControllerUtil() {}

    public static void maxProductPriceSliderInit(Slider maxPriceSlider, Label maxPriceLabel) {
        double dbMaxPrice = getMaxPrice();

        maxPriceSlider.setMin(0.0);
        maxPriceSlider.setMax(dbMaxPrice);
        maxPriceSlider.setValue(dbMaxPrice);
        maxPriceSlider.setShowTickLabels(true);
        maxPriceSlider.setMajorTickUnit(dbMaxPrice / 5);
        maxPriceSlider.setMinorTickCount(1);
        maxPriceSlider.setBlockIncrement(0.5);

        maxPriceSlider
                .valueProperty()
                .addListener(
                        ((observable, oldValue, newValue) -> {
                            Double maxPriceValue = maxPriceSlider.getValue();
                            maxPriceLabel.setText(new DecimalFormat("##.##").format(maxPriceValue));
                        }));
    }

    public static void minProductQuantitySliderInit(
            Slider minQuantitySlider, Label minQuantityLabel) {
        double maxQuantity = getMaxQuantity();

        minQuantitySlider.setMin(0.0);
        minQuantitySlider.setMax(maxQuantity);
        minQuantitySlider.setValue(0.0);
        minQuantitySlider.setShowTickLabels(true);
        minQuantitySlider.setMajorTickUnit(10.0);
        minQuantitySlider.setMinorTickCount(0);
        minQuantitySlider.setBlockIncrement(1.0);

        minQuantitySlider
                .valueProperty()
                .addListener(
                        ((observable, oldValue, newValue) -> {
                            Double minQuantityValue = minQuantitySlider.getValue();
                            minQuantityLabel.setText(new DecimalFormat("#").format(minQuantityValue));
                        }));
    }

    public static void setProductTableColumns(
            TableColumn<Product, String> nameColumn,
            TableColumn<Product, BigDecimal> priceColumn,
            TableColumn<Product, Integer> quantityColumn) {
        nameColumn.setCellValueFactory(
                cellData -> Bindings.createObjectBinding(() -> cellData.getValue().getProductName()));

        priceColumn.setCellValueFactory(
                cellData -> Bindings.createObjectBinding(() -> cellData.getValue().getPrice()));

        quantityColumn.setCellValueFactory(
                cellData -> Bindings.createObjectBinding(() -> cellData.getValue().getQuantity()));
    }

    public static void loadProducts(
            TextField searchField,
            Slider maxPriceSlider,
            Slider minQuantitySlider,
            TableView<Product> productTableView) {

        String productName = searchField.getText();
        double maxPrice = maxPriceSlider.getValue();
        double minQuantity = minQuantitySlider.getValue();

        try {
            productTableView.setItems(ProductLoader.loadProducts(productName, maxPrice, minQuantity));
        } catch (SQLException e) {
            e.printStackTrace();
            PopupDialogs.showErrorDialog(
                    "Error", "Something went wrong", "Something went wrong, try again later.");
        }
    }

    public static void resetProductFilter(
            TextField searchField,
            Slider maxPriceSlider,
            Slider minQuantitySlider,
            Label maxPriceLabel,
            Label minQuantityLabel,
            TableView<Product> productTableView) {

        searchField.clear();
        maxPriceSlider.setValue(getMaxPrice());
        minQuantitySlider.setValue(0.0);
        maxPriceLabel.setText("No limit");
        minQuantityLabel.setText("No limit");

        loadProducts(searchField, maxPriceSlider, minQuantitySlider, productTableView);
    }

    private static double getMaxPrice() {
        try {
            return ProductLoader.getMaxPrice();
        } catch (SQLException e) {
            e.printStackTrace();
            return 100.0;
        }
    }

    private static double getMaxQuantity() {
        try {
            return ProductLoader.getMaxQuantity();
        } catch (SQLException e) {
            e.printStackTrace();
            return 500.0;
        }
    }
}
