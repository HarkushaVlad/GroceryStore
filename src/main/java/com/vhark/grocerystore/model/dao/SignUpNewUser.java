package com.vhark.grocerystore.model.dao;

import com.vhark.grocerystore.database.DatabaseHandler;
import com.vhark.grocerystore.database.constants.DbUsers;
import com.vhark.grocerystore.model.entities.User;
import com.vhark.grocerystore.model.exceptions.UserExistsException;
import com.vhark.grocerystore.util.PasswordHash;
import com.vhark.grocerystore.util.userDataValidator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class SignUpNewUser {
  private final User NEW_USER;

  private final String USER_PASSWORD;

  public SignUpNewUser(
      String firstName,
      String lastName,
      String middleName,
      String idCode,
      String address,
      String password) {
    this.USER_PASSWORD = password;

    NEW_USER =
        new User(
            firstName,
            lastName,
            middleName,
            idCode,
            address,
            PasswordHash.generateHash(password),
            false);
  }

  public void addNewUserToDb() throws SQLException {
    if (!validateUserData()) {
      throw new IllegalArgumentException("Invalid data input");
    }

    try (Connection connection =
        DatabaseHandler.getDbConnection(
            DbUsers.USER_AUTH.getLoginName(), DbUsers.USER_AUTH.getPassword())) {
      if (isExistUser(connection)) {
        throw new UserExistsException("A user with this identification code already exists");
      }

      String sqlInsertNewUser =
          "INSERT INTO users(first_name, last_name, middle_name, identification_code, address, password_hash, is_employee) VALUES(?, ?, ?, ?, ?, ?, ?)";

      try (PreparedStatement insertNewUserStatement =
          connection.prepareStatement(sqlInsertNewUser)) {
        insertNewUserStatement.setString(1, NEW_USER.getFirstName());
        insertNewUserStatement.setString(2, NEW_USER.getLastName());
        insertNewUserStatement.setString(3, NEW_USER.getMiddleName());
        insertNewUserStatement.setString(4, NEW_USER.getIdCode());
        insertNewUserStatement.setString(5, NEW_USER.getAddress());
        insertNewUserStatement.setString(6, NEW_USER.getPasswordHash());
        insertNewUserStatement.setBoolean(7, false);

        insertNewUserStatement.execute();
      }
    }
  }

  private boolean validateUserData() {
    return userDataValidator.validateUserName(NEW_USER.getFirstName())
        && userDataValidator.validateUserName(NEW_USER.getLastName())
        && userDataValidator.validateUserName(NEW_USER.getMiddleName())
        && userDataValidator.validateUserIdCode(NEW_USER.getIdCode())
        && userDataValidator.validateUserAddress(NEW_USER.getAddress())
        && userDataValidator.validateUserPassword(USER_PASSWORD);
  }

  private boolean isExistUser(Connection connection) throws SQLException {
    String sqlSelectUser = "SELECT identification_code FROM users WHERE identification_code = ?";

    try (PreparedStatement selectUserStatement = connection.prepareStatement(sqlSelectUser)) {
      selectUserStatement.setString(1, NEW_USER.getIdCode());

      try (ResultSet resultSet = selectUserStatement.executeQuery()) {
        return resultSet.next();
      }
    }
  }
}
