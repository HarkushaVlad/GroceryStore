package com.vhark.grocerystore.model.exceptions;

import java.sql.SQLException;

public class ZeroQuantityException extends SQLException {
  public ZeroQuantityException(String message) {
    super(message);
  }
}
