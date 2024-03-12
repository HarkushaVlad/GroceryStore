package com.vhark.grocerystore.model;

import java.sql.Date;

public class TablePurchase extends Purchase {

    private String productName;

    private double totalCost;

    public TablePurchase(int userId, int productId, int quantityPurchased, Date purchaseDate, String productName, double totalCost) {
        super(userId, productId, quantityPurchased, purchaseDate);
        this.productName = productName;
        this.totalCost = totalCost;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}
