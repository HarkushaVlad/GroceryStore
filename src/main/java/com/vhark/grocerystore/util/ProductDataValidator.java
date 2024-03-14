package com.vhark.grocerystore.util;

import static java.lang.Double.parseDouble;

public final class ProductDataValidator {

  private ProductDataValidator() {}

  public static boolean validateProductName(String productName) {
    return productName != null && productName.matches("^[A-Za-zА-Яа-яіїє].+$");
  }

  public static boolean validateProductPrice(String productPrice) {
    return productPrice != null
        && productPrice.matches("^(0(\\.\\d{1,2})|[1-9]\\d*(\\.\\d{1,2})?)$")
        && parseDouble(productPrice) < 10000;
  }

  public static boolean validateProductQuantity(String productQuantity) {
    return productQuantity != null
        && productQuantity.matches("^\\d+$")
        && Integer.parseInt(productQuantity) >= 0 && Integer.parseInt(productQuantity) < 10000;
  }

  public static boolean validateAllProductData(
      String productName, String productPrice, String productQuantity) {
    return validateProductName(productName)
        && validateProductPrice(productPrice)
        && validateProductQuantity(productQuantity);
  }
}
