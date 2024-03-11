package com.vhark.grocerystore.model;

import com.vhark.grocerystore.database.DatabaseHandler;
import com.vhark.grocerystore.database.constants.DbUsers;
import com.vhark.grocerystore.model.exceptions.FailedLogInException;
import com.vhark.grocerystore.model.singletons.UserDataSingleton;
import com.vhark.grocerystore.util.PasswordHash;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class LogInUser {
  private final String USER_ID_CODE;

  private final String USER_PASSWORD;

  public LogInUser(String USER_ID_CODE, String USER_PASSWORD) {
    this.USER_ID_CODE = USER_ID_CODE;
    this.USER_PASSWORD = USER_PASSWORD;
  }

  public void logIn() throws SQLException {
    try (Connection connection =
        DatabaseHandler.getDbConnection(
            DbUsers.USER_AUTH.getLoginName(), DbUsers.USER_AUTH.getPassword())) {
      if (isExistUser(connection) && !matchPasswordHashes(connection)) {
        throw new FailedLogInException("Invalid input log in data");
      }

      UserDataSingleton userDataSingleton = UserDataSingleton.getInstance();
      userDataSingleton.setIsEmployee(getIsEmployee(connection));
      userDataSingleton.setIdCode(USER_ID_CODE);
    }
  }

  private boolean isExistUser(Connection connection) throws SQLException {
    String sqlSelectUser = "SELECT identification_code FROM users WHERE identification_code = ?";

    try (PreparedStatement selectUserStatement = connection.prepareStatement(sqlSelectUser)) {
      selectUserStatement.setString(1, USER_ID_CODE);

      try (ResultSet resultSet = selectUserStatement.executeQuery()) {
        return resultSet.next();
      }
    }
  }

  private boolean matchPasswordHashes(Connection connection) throws SQLException {
    String sqlSelectPassword = "SELECT password_hash FROM users WHERE identification_code = ?";

    try (PreparedStatement selectPasswordStatement =
        connection.prepareStatement(sqlSelectPassword)) {
      selectPasswordStatement.setString(1, USER_ID_CODE);

      try (ResultSet resultSet = selectPasswordStatement.executeQuery()) {
        return resultSet.next()
            && PasswordHash.checkPassword(USER_PASSWORD, resultSet.getString("password_hash"));
      }
    }
  }

  private boolean getIsEmployee(Connection connection) throws SQLException {
    String sqlSelectIsEmployee = "SELECT is_employee FROM users WHERE identification_code = ?";

    try (PreparedStatement selectIsEmployeeStatement =
        connection.prepareStatement(sqlSelectIsEmployee)) {
      selectIsEmployeeStatement.setString(1, USER_ID_CODE);

      try (ResultSet resultSet = selectIsEmployeeStatement.executeQuery()) {
        return resultSet.next() && resultSet.getBoolean("is_employee");
      }
    }
  }
}
