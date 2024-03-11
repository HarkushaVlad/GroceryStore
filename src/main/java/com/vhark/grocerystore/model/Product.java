package com.vhark.grocerystore.model;

import java.math.BigDecimal;

public class Product {
  private final int PRODUCT_ID;
  private String productName;

  private BigDecimal price;

  private int quantity;

  public Product(int productId, String productName, BigDecimal price, int quantity) {
    this.PRODUCT_ID = productId;
    this.productName = productName;
    this.price = price;
    this.quantity = quantity;
  }

  public int getProductId() {
    return PRODUCT_ID;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
}
