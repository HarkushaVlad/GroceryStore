package com.vhark.grocerystore.model;

import com.vhark.grocerystore.database.DatabaseHandler;
import com.vhark.grocerystore.database.constants.DbUsers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class ProductLoader {

  private final Connection CONNECTION;

  public ProductLoader() {
    this.CONNECTION =
        DatabaseHandler.getDbConnection(
            DbUsers.CLIENT.getLoginName(), DbUsers.CLIENT.getPassword());
  }

  public Double getMaxPrice() throws SQLException {
    String sqlSelectMaxPrice = "SELECT MAX(price) FROM products";
    return executeMaxQuery(sqlSelectMaxPrice);
  }

  public Double getMaxQuantity() throws SQLException {
    String sqlSelectMaxQuantity = "SELECT MAX(quantity) FROM products";
    return executeMaxQuery(sqlSelectMaxQuantity);
  }

  public ObservableList<Product> loadDefaultProducts() throws SQLException {
    String sqlSelectAllProducts = "SELECT * FROM products";
    return executeListQuery(sqlSelectAllProducts);
  }

  public ObservableList<Product> loadFilteredProducts(
      String name, double maxPrice, double minQuantity) throws SQLException {
    String sqlSelectFilteredProducts =
        "SELECT * FROM products WHERE product_name LIKE ? AND price <= ? AND quantity >= ?";
    return executeListQuery(sqlSelectFilteredProducts, "%" + name + "%", maxPrice, minQuantity);
  }

  private double executeMaxQuery(String sqlQuery) throws SQLException {
    try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sqlQuery);
        ResultSet resultSet = preparedStatement.executeQuery()) {

      resultSet.next();
      return resultSet.getDouble(1);
    } finally {
      closeConnection();
    }
  }

  private ObservableList<Product> executeListQuery(String sqlQuery, Object... params)
      throws SQLException {
    try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sqlQuery)) {
      for (int i = 0; i < params.length; i++) {
        preparedStatement.setObject(i + 1, params[i]);
      }

      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        return getProductObjects(resultSet);
      }
    } finally {
      closeConnection();
    }
  }

  private ObservableList<Product> getProductObjects(ResultSet resultSet) throws SQLException {
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

  private void closeConnection() {
    try {
      if (CONNECTION != null && !CONNECTION.isClosed()) {
        CONNECTION.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
