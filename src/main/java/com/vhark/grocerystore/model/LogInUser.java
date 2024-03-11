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
  String userIdCode;

  String userPassword;

  public LogInUser(String userIdCode, String userPassword) {
    this.userIdCode = userIdCode;
    this.userPassword = userPassword;
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
      userDataSingleton.setIdCode(userIdCode);
    }
  }

  private boolean isExistUser(Connection connection) throws SQLException {
    String sqlSelectUser = "SELECT identification_code FROM users WHERE identification_code = ?";

    PreparedStatement selectUserStatement = connection.prepareStatement(sqlSelectUser);

    selectUserStatement.setString(1, userIdCode);

    ResultSet resultSet = selectUserStatement.executeQuery();

    return resultSet.next();
  }

  private boolean matchPasswordHashes(Connection connection) throws SQLException {
    String sqlSelectPassword = "SELECT password_hash FROM users WHERE identification_code = ?";

    PreparedStatement selectPasswordStatement = connection.prepareStatement(sqlSelectPassword);

    selectPasswordStatement.setString(1, userIdCode);

    ResultSet resultSet = selectPasswordStatement.executeQuery();
    resultSet.next();

    return PasswordHash.checkPassword(userPassword, resultSet.getString("password_hash"));
  }

  private boolean getIsEmployee(Connection connection) throws SQLException {
    String sqlSelectIsEmployee = "SELECT is_employee FROM users WHERE identification_code = ?";

    PreparedStatement selectIsEmployeeStatement = connection.prepareStatement(sqlSelectIsEmployee);

    selectIsEmployeeStatement.setString(1, userIdCode);

    ResultSet resultSet = selectIsEmployeeStatement.executeQuery();
    resultSet.next();

    return resultSet.getBoolean("is_employee");
  }
}
