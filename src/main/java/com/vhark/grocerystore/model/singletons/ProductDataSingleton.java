package com.vhark.grocerystore.model.singletons;

public class ProductDataSingleton {

  private static final ProductDataSingleton instance = new ProductDataSingleton();

  private String productId;

  private ProductDataSingleton() {}
  ;

  public static ProductDataSingleton getInstance() {
    return instance;
  }

  public String getProductId() {
    return productId;
  }

  public void setProductId(String idCode) {
    this.productId = idCode;
  }
}
