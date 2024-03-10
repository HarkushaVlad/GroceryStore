package com.vhark.grocerystore.database.constants;

import com.vhark.grocerystore.util.PropertiesLoader;

public enum DbUsers {
  USER_AUTH(
      PropertiesLoader.get("db.user_authentication.login.name"),
      PropertiesLoader.get("db.user_authentication.password")),
  CLIENT(PropertiesLoader.get("db.client.login.name"), PropertiesLoader.get("db.client.password")),
  EMPLOYEE(
      PropertiesLoader.get("db.employee.login.name"), PropertiesLoader.get("db.employee.password"));

  private final String loginName;
  private final String password;

  DbUsers(String username, String password) {
    this.loginName = username;
    this.password = password;
  }

  public String getLoginName() {
    return loginName;
  }

  public String getPassword() {
    return password;
  }
}
