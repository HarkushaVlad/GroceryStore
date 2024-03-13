package com.vhark.grocerystore.model.dao;

import com.vhark.grocerystore.database.DatabaseHandler;
import com.vhark.grocerystore.database.constants.DbUsers;
import com.vhark.grocerystore.model.entities.TablePurchase;
import com.vhark.grocerystore.model.entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class TablePurchaseLoader {

  private TablePurchaseLoader() {}

  public static ObservableList<TablePurchase> loadPurchases(String userCode) throws SQLException {
    try (Connection connection =
        DatabaseHandler.getDbConnection(
            DbUsers.CLIENT.getLoginName(), DbUsers.CLIENT.getPassword())) {

      String sqlSelectAllUserPurchases =
          "SELECT user_id, pur.product_id, product_name, price * quantity_purchased as total_cost, "
              + "quantity_purchased, purchase_date "
              + "FROM purchases pur JOIN products pro ON pur.product_id = pro.product_id WHERE user_id = ?";

      try (PreparedStatement selectAllUserPurchasesStatement =
          connection.prepareStatement(sqlSelectAllUserPurchases)) {
        selectAllUserPurchasesStatement.setInt(1, User.getUserIdByCode(connection, userCode));

        try (ResultSet resultSet = selectAllUserPurchasesStatement.executeQuery()) {
          return getPurchaseObjects(resultSet);
        }
      }
    }
  }

  private static ObservableList<TablePurchase> getPurchaseObjects(ResultSet resultSet)
      throws SQLException {
    ObservableList<TablePurchase> purchasesList = FXCollections.observableArrayList();

    while (resultSet.next()) {
      TablePurchase purchase =
          new TablePurchase(
              resultSet.getInt("user_id"),
              resultSet.getInt("product_id"),
              resultSet.getInt("quantity_purchased"),
              resultSet.getDate("purchase_date"),
              resultSet.getString("product_name"),
              resultSet.getDouble("total_cost"));

      purchasesList.add(purchase);
    }

    return purchasesList;
  }
}
