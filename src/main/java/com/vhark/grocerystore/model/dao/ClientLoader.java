package com.vhark.grocerystore.model.dao;

import com.vhark.grocerystore.database.DatabaseHandler;
import com.vhark.grocerystore.database.constants.DbUsers;
import com.vhark.grocerystore.model.entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class ClientLoader {

  private ClientLoader() {}

  public static ObservableList<User> loadClients() throws SQLException {
    String sqlSelectAllClients = "SELECT * FROM users WHERE is_employee = FALSE";

    try (Connection connection =
            DatabaseHandler.getDbConnection(
                DbUsers.EMPLOYEE.getLoginName(), DbUsers.EMPLOYEE.getPassword());
        PreparedStatement selectAllClientsStatement =
            connection.prepareStatement(sqlSelectAllClients);
        ResultSet resultSet = selectAllClientsStatement.executeQuery()) {

      return getClientsObjects(resultSet);
    }
  }

  private static ObservableList<User> getClientsObjects(ResultSet resultSet) throws SQLException {
    ObservableList<User> clientsList = FXCollections.observableArrayList();

    while (resultSet.next()) {
      User client =
          new User(
              resultSet.getString("user_id"),
              resultSet.getString("first_name"),
              resultSet.getString("last_name"),
              resultSet.getString("middle_name"),
              resultSet.getString("identification_code"),
              resultSet.getString("address"),
              resultSet.getString("password_hash"),
              resultSet.getBoolean("is_employee"));

      clientsList.add(client);
    }

    return clientsList;
  }
}
