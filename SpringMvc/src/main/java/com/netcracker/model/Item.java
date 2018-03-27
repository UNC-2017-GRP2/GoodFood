package com.netcracker.model;

import java.math.BigInteger;

public class Item {
    private BigInteger productId;
    private String productName;
    private String productDescription;
    private String productCategory;
    private BigInteger productCost;
    private String productImage;
    private int productQuantity;

    public Item(BigInteger productId, String productName, String productDescription, String productCategory, BigInteger productCost, String productImage, int productQuantity) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productCategory = productCategory;
        this.productCost = productCost;
        this.productImage = productImage;
        this.productQuantity = productQuantity;
    }

    public Item(){}


    public BigInteger getProductId() {
        return productId;
    }

    public String getProductName() { return productName; }

    public String getProductDescription() { return productDescription; }

    public String getProductCategory() { return productCategory; }

    public BigInteger getProductCost() {
        return productCost;
    }

    public String getProductImage() {
        return productImage;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductId(BigInteger productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public void setProductCost(BigInteger productCost) {
        this.productCost = productCost;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    @Override
    public String toString() {
        return "Item{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", productCategory='" + productCategory + '\'' +
                ", productCost=" + productCost +
                '}';
    }
}
