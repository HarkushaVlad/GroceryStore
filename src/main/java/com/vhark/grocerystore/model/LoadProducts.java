package com.vhark.grocerystore.model;

import com.vhark.grocerystore.database.DatabaseHandler;
import com.vhark.grocerystore.database.constants.DbUsers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class LoadProducts {
  private final String NAME;

  private final String MAX_PRICE;

  private final String QUANTITY;

  private final Boolean IN_STOCK;

  private final Boolean OUT_OF_STOCK;

  public LoadProducts(
      String NAME, String MAX_PRICE, String QUANTITY, Boolean IN_STOCK, Boolean OUT_OF_STOCK) {
    this.NAME = NAME;
    this.MAX_PRICE = MAX_PRICE;
    this.QUANTITY = QUANTITY;
    this.IN_STOCK = IN_STOCK;
    this.OUT_OF_STOCK = OUT_OF_STOCK;
  }

  public static ObservableList<Product> getDefaultProducts() throws SQLException {
    String sqlSelectAllProducts = "SELECT * FROM products";

    try (Connection connection =
            DatabaseHandler.getDbConnection(
                DbUsers.CLIENT.getLoginName(), DbUsers.CLIENT.getPassword());
        PreparedStatement selectAllProductsStatement =
            connection.prepareStatement(sqlSelectAllProducts);
        ResultSet resultSet = selectAllProductsStatement.executeQuery()) {

      return getProductObjects(resultSet);
    }
  }

  private static ObservableList<Product> getProductObjects(ResultSet resultSet)
      throws SQLException {
    ObservableList<Product> productsList = FXCollections.observableArrayList();

    while (resultSet.next()) {
      Product product =
          new Product(
              resultSet.getString("product_id"),
              resultSet.getString("product_name"),
              resultSet.getBigDecimal("price"),
              resultSet.getInt("quantity"));

      productsList.add(product);
    }

    return productsList;
  }
}
