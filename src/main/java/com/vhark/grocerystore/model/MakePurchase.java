package com.vhark.grocerystore.model;

import com.vhark.grocerystore.database.DatabaseHandler;
import com.vhark.grocerystore.database.constants.DbUsers;
import com.vhark.grocerystore.model.exceptions.ExcessiveQuantityException;
import com.vhark.grocerystore.model.exceptions.UserIdNotFoundException;
import com.vhark.grocerystore.model.singletons.UserDataSingleton;

import java.sql.*;
import java.time.LocalDate;

public final class MakePurchase {

  private final String USER_ID_CODE;

  private final String PRODUCT_ID;

  private final int QUANTITY;

  public MakePurchase(String USER_ID_CODE, String PRODUCT_ID, int QUANTITY) {
    this.USER_ID_CODE = USER_ID_CODE;
    this.PRODUCT_ID = PRODUCT_ID;
    this.QUANTITY = QUANTITY;
  }

  public void addPurchaseToDb() throws SQLException {
    String sqlInsertPurchase =
        "INSERT INTO purchases(user_id, product_id, quantity_purchased, purchase_date) VALUES(?, ?, ?, ?)";

    try (Connection connection =
            DatabaseHandler.getDbConnection(
                DbUsers.CLIENT.getLoginName(), DbUsers.CLIENT.getPassword());
        PreparedStatement insertPurchaseStatement =
            connection.prepareStatement(sqlInsertPurchase); ) {

      insertPurchaseStatement.setInt(1, User.getUserIdByCode(connection, USER_ID_CODE));
      insertPurchaseStatement.setString(2, PRODUCT_ID);
      insertPurchaseStatement.setInt(3, QUANTITY);

      LocalDate currentDate = LocalDate.now();
      String formattedDate = currentDate.toString();
      insertPurchaseStatement.setDate(4, Date.valueOf(formattedDate));

      insertPurchaseStatement.execute();

      updateQuantity(connection);
    }
  }

  private void updateQuantity(Connection connection) throws SQLException {
    int resQuantity = getActualQuantity(connection);

    String sqlUpdateQuantity = "UPDATE products SET quantity = ? WHERE product_id = ?";
    try (PreparedStatement updateQuantityStatement =
        connection.prepareStatement(sqlUpdateQuantity)) {
      updateQuantityStatement.setInt(1, resQuantity);
      updateQuantityStatement.setString(2, PRODUCT_ID);

      updateQuantityStatement.executeUpdate();
    }
  }

  private int getActualQuantity(Connection connection) throws SQLException {
    String sqlInsertPurchase = "SELECT quantity FROM products WHERE product_id = ?";

    try (PreparedStatement insertPurchaseStatement =
        connection.prepareStatement(sqlInsertPurchase)) {
      insertPurchaseStatement.setString(1, PRODUCT_ID);

      ResultSet resultSet = insertPurchaseStatement.executeQuery();
      resultSet.next();

      int resQuantity = resultSet.getInt("quantity") - QUANTITY;

      if (resQuantity < 0) {
        throw new ExcessiveQuantityException(
            "The quantity of selected products exceeds the available stock");
      } else {
        return resQuantity;
      }
    }
  }
}
