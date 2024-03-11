package com.vhark.grocerystore.model;

import com.vhark.grocerystore.database.DatabaseHandler;
import com.vhark.grocerystore.database.constants.DbUsers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoadProducts {
  String name;

  String maxPrice;

  String quantity;

  Boolean inStock;

  Boolean outOfStock;

  public LoadProducts(
      String name, String maxPrice, String quantity, Boolean inStock, Boolean outOfStock) {
    this.name = name;
    this.maxPrice = maxPrice;
    this.quantity = quantity;
    this.inStock = inStock;
    this.outOfStock = outOfStock;
  }

  public static ObservableList<Product> getDefaultProducts() throws SQLException {
    try (Connection connection =
        DatabaseHandler.getDbConnection(
            DbUsers.CLIENT.getLoginName(), DbUsers.CLIENT.getPassword())) {

      String sqlSelectAllProducts = "SELECT * FROM products";

      PreparedStatement SelectAllProductsStatement =
          connection.prepareStatement(sqlSelectAllProducts);

      ResultSet resultSet = SelectAllProductsStatement.executeQuery();

      return getProductObjects(resultSet);
    }
  }

  private static ObservableList<Product> getProductObjects(ResultSet resultSet)
      throws SQLException {
    ObservableList<Product> productsList = FXCollections.observableArrayList();

    while (resultSet.next()) {
      Product product =
          new Product(
              resultSet.getInt("product_id"),
              resultSet.getString("product_name"),
              resultSet.getBigDecimal("price"),
              resultSet.getInt("quantity"));

      productsList.add(product);
    }

    return productsList;
  }
}
