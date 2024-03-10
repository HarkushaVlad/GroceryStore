package com.vhark.grocerystore.database;

import com.vhark.grocerystore.database.constants.DbConfiguration;
import com.vhark.grocerystore.util.PopupDialogs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseHandler {

  private DatabaseHandler() {}
  ;

  public static Connection getDbConnection(String DbUserLoginName, String DbUserPassword) {
    String url =
        String.format(
            "jdbc:mysql://%s:%s/%s",
            DbConfiguration.HOST.getValue(),
            DbConfiguration.PORT.getValue(),
            DbConfiguration.NAME.getValue());

    try {
      return DriverManager.getConnection(url, DbUserLoginName, DbUserPassword);
    } catch (SQLException e) {
      PopupDialogs.showErrorDialog(
          "Connection Error", "Connection Failed", "Failed to establish a connection");

      throw new RuntimeException(e);
    }
  }
}
