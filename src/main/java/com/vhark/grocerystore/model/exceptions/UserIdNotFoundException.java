package com.vhark.grocerystore.model.exceptions;

import java.sql.SQLException;

public class UserIdNotFoundException extends SQLException {
  public UserIdNotFoundException(String message) {
    super(message);
  }
}
