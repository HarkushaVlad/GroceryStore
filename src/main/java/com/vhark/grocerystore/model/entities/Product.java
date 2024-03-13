package com.vhark.grocerystore.model.entities;

import java.math.BigDecimal;

public class Product {
  private String productId;

  private String productName;

  private BigDecimal price;

  private int quantity;

  public Product(String productId, String productName, BigDecimal price, int quantity) {
    this.productId = productId;
    this.productName = productName;
    this.price = price;
    this.quantity = quantity;
  }

  public Product(String productName, BigDecimal price, int quantity) {
    this.productName = productName;
    this.price = price;
    this.quantity = quantity;
  }

  public String getProductId() {
    return productId;
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
