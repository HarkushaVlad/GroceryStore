package com.vhark.grocerystore.model.dao;

import com.vhark.grocerystore.database.DatabaseHandler;
import com.vhark.grocerystore.database.constants.DbUsers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class ClientEditor {

  private ClientEditor() {}

  public static void deleteClient(String clientUserId) throws SQLException {
    String sqlDeleteClientPurchases = "DELETE FROM purchases WHERE user_id = ?";
    String sqlDeleteClient = "DELETE FROM users WHERE user_id = ?";

    try (Connection connection =
        DatabaseHandler.getDbConnection(
            DbUsers.EMPLOYEE.getLoginName(), DbUsers.EMPLOYEE.getPassword())) {

      try (PreparedStatement deletePurchasesStatement =
              connection.prepareStatement(sqlDeleteClientPurchases);
          PreparedStatement deleteUserStatement = connection.prepareStatement(sqlDeleteClient)) {

        deletePurchasesStatement.setString(1, clientUserId);
        deletePurchasesStatement.execute();

        deleteUserStatement.setString(1, clientUserId);
        deleteUserStatement.execute();
      }
    }
  }
}
