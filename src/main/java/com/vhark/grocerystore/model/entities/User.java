package com.vhark.grocerystore.model.entities;

import com.vhark.grocerystore.model.exceptions.UserIdNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
  private String firstName;
  private String lastName;
  private String middleName;
  private String idCode;
  private String address;
  private String passwordHash;
  private boolean isEmployee;

  public User(
      String firstName,
      String lastName,
      String middleName,
      String idCode,
      String address,
      String passwordHash,
      boolean isEmployee) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.middleName = middleName;
    this.idCode = idCode;
    this.address = address;
    this.passwordHash = passwordHash;
    this.isEmployee = isEmployee;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getMiddleName() {
    return middleName;
  }

  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  public String getIdCode() {
    return idCode;
  }

  public void setIdCode(String idCode) {
    this.idCode = idCode;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPasswordHash() {
    return passwordHash;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  public boolean isEmployee() {
    return isEmployee;
  }

  public void setEmployee(boolean employee) {
    isEmployee = employee;
  }

  public static int getUserIdByCode(Connection connection, String userCode) throws SQLException {
    String sqlSelectUserId = "SELECT user_id FROM users WHERE identification_code = ?";

    try (PreparedStatement selectUserIdStatement = connection.prepareStatement(sqlSelectUserId)) {
      selectUserIdStatement.setString(1, userCode);

      try (ResultSet resultSet = selectUserIdStatement.executeQuery()) {
        if (resultSet.next()) {
          return resultSet.getInt("user_id");
        } else {
          throw new UserIdNotFoundException(
                  "User ID not found for identification code: " + userCode);
        }
      }
    }
  }
}
