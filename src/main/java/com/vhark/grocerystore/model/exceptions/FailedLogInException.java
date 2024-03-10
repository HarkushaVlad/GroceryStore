package com.vhark.grocerystore.model.exceptions;

import java.sql.SQLException;

public class FailedLogInException extends SQLException {
    public FailedLogInException(String message) {
        super(message);
    }
}
