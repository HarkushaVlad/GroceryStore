package com.vhark.grocerystore.model;

import com.vhark.grocerystore.database.DatabaseHandler;
import com.vhark.grocerystore.database.constants.DbUsers;
import com.vhark.grocerystore.model.exceptions.UserExistsException;
import com.vhark.grocerystore.util.PasswordHash;
import com.vhark.grocerystore.util.userDataValidator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class SignUpNewUser {
  User newUser;

  String userPassword;

  public SignUpNewUser(
      String firstName,
      String lastName,
      String middleName,
      String idCode,
      String address,
      String password) {
    this.userPassword = password;

    newUser =
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

      PreparedStatement insertNewUserStatement = connection.prepareStatement(sqlInsertNewUser);

      insertNewUserStatement.setString(1, newUser.getFirstName());
      insertNewUserStatement.setString(2, newUser.getLastName());
      insertNewUserStatement.setString(3, newUser.getMiddleName());
      insertNewUserStatement.setString(4, newUser.getIdCode());
      insertNewUserStatement.setString(5, newUser.getAddress());
      insertNewUserStatement.setString(6, newUser.getPasswordHash());
      insertNewUserStatement.setBoolean(7, false);

      insertNewUserStatement.execute();
    }
  }

  private boolean validateUserData() {
    return userDataValidator.validateUserName(newUser.getFirstName())
        && userDataValidator.validateUserName(newUser.getLastName())
        && userDataValidator.validateUserName(newUser.getMiddleName())
        && userDataValidator.validateUserIdCode(newUser.getIdCode())
        && userDataValidator.validateUserAddress(newUser.getAddress())
        && userDataValidator.validateUserPassword(userPassword);
  }

  private boolean isExistUser(Connection connection) throws SQLException {
    String sqlSelectUser = "SELECT identification_code FROM users WHERE identification_code = ?";

    PreparedStatement selectUserStatement = connection.prepareStatement(sqlSelectUser);

    selectUserStatement.setString(1, newUser.getIdCode());

    ResultSet resultSet = selectUserStatement.executeQuery();

    return resultSet.next();
  }
}
