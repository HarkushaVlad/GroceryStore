package com.vhark.grocerystore.model;

import java.sql.Date;

public class Purchase {

    private int purchaseId;

    private int userId;

    private int productId;

    private int quantityPurchased;

    private Date purchaseDate;

    public Purchase(int purchaseId, int userId, int productId, int quantityPurchased, Date purchaseDate) {
        this.purchaseId = purchaseId;
        this.userId = userId;
        this.productId = productId;
        this.quantityPurchased = quantityPurchased;
        this.purchaseDate = purchaseDate;
    }

    public Purchase(int userId, int productId, int quantityPurchased, Date purchaseDate) {
        this.userId = userId;
        this.productId = productId;
        this.quantityPurchased = quantityPurchased;
        this.purchaseDate = purchaseDate;
    }

    public int getPurchaseId() {
        return purchaseId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantityPurchased() {
        return quantityPurchased;
    }

    public void setQuantityPurchased(int quantityPurchased) {
        this.quantityPurchased = quantityPurchased;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
}
