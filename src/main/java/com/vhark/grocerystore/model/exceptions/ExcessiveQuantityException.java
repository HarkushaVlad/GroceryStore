package com.vhark.grocerystore.model.exceptions;

import java.sql.SQLException;

public class ExcessiveQuantityException extends SQLException {
  public ExcessiveQuantityException(String message) {
    super(message);
  }
}
