package com.vhark.grocerystore.model.dao;

import com.vhark.grocerystore.database.DatabaseHandler;
import com.vhark.grocerystore.database.constants.DbUsers;
import com.vhark.grocerystore.model.entities.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class ProductLoader {

  private ProductLoader() {}

  public static Double getMaxPrice() throws SQLException {
    String sqlSelectMaxPrice = "SELECT MAX(price) FROM products";
    return executeMaxQuery(sqlSelectMaxPrice);
  }

  public static Double getMaxQuantity() throws SQLException {
    String sqlSelectMaxQuantity = "SELECT MAX(quantity) FROM products";
    return executeMaxQuery(sqlSelectMaxQuantity);
  }

  public static ObservableList<Product> loadProducts(
      String productName, double maxPrice, double minQuantity) throws SQLException {

    try (Connection connection =
        DatabaseHandler.getDbConnection(
            DbUsers.CLIENT.getLoginName(), DbUsers.CLIENT.getPassword())) {

      if (productName.isEmpty() && maxPrice == getMaxPrice() && minQuantity == 0.0) {
        String sqlSelectAllProducts = "SELECT * FROM products";
        return executeListQuery(connection, sqlSelectAllProducts);
      } else {
        String sqlSelectFilteredProducts =
            "SELECT * FROM products WHERE product_name LIKE ? AND price <= ? AND quantity >= ?";
        return executeListQuery(
            connection,
            sqlSelectFilteredProducts,
            "%" + productName + "%",
            maxPrice + 0.1,
            minQuantity - 0.1);
      }
    }
  }

  private static double executeMaxQuery(String sqlQuery) throws SQLException {
    try (Connection connection =
            DatabaseHandler.getDbConnection(
                DbUsers.CLIENT.getLoginName(), DbUsers.CLIENT.getPassword());
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        ResultSet resultSet = preparedStatement.executeQuery()) {

      resultSet.next();
      return resultSet.getDouble(1);
    }
  }

  private static ObservableList<Product> executeListQuery(
      Connection connection, String sqlQuery, Object... params) throws SQLException {
    try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
      for (int i = 0; i < params.length; i++) {
        preparedStatement.setObject(i + 1, params[i]);
      }

      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        return getProductObjects(resultSet);
      }
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
