package com.vhark.grocerystore.model.dao;

import com.vhark.grocerystore.database.DatabaseHandler;
import com.vhark.grocerystore.database.constants.DbUsers;
import com.vhark.grocerystore.util.ProductDataValidator;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static java.lang.Integer.parseInt;

public final class ProductEditor {
  private ProductEditor() {}

  public static void editProduct(
      String productId, String productName, String productPrice, String productQuantity)
      throws SQLException {

    if (productId != null
        && ProductDataValidator.validateAllProductData(
            productName, productPrice, productQuantity)) {

      String sqlUpdateProduct =
          "UPDATE products SET product_name = ?, price = ?, quantity = ? WHERE product_id = ?";

      try (Connection connection =
          DatabaseHandler.getDbConnection(
              DbUsers.EMPLOYEE.getLoginName(), DbUsers.EMPLOYEE.getPassword())) {

        try (PreparedStatement updateProductStatement =
            connection.prepareStatement(sqlUpdateProduct)) {
          updateProductStatement.setString(1, productName);
          updateProductStatement.setBigDecimal(2, new BigDecimal(productPrice));
          updateProductStatement.setInt(3, parseInt(productQuantity));
          updateProductStatement.setInt(4, parseInt(productId));
          updateProductStatement.execute();
        }
      }
    } else {
      throw new IllegalArgumentException("Invalid product data input");
    }
  }
}
