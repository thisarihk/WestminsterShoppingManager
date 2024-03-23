package com.example.westminstershop;

import java.io.Serializable;

public abstract class Product implements Serializable {
    private final String productId;
    private final String productName;
    private int availableItems;
    private final double price;

    // Variable to represent quantity in the shopping cart
    private int quantityInCart;

    public Product(String productId, String productName, int availableItems, double price) {
        this.productId = productId;
        this.productName = productName;
        this.availableItems = availableItems;
        this.price = price;

        // Initialize quantity in cart to 0 by default
        this.quantityInCart = 0;
    }

    // Getters and Setters for the fields
    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getAvailableItems() {
        return availableItems;
    }

    public void setAvailableItems(int availableItems) {
        this.availableItems = availableItems;
    }

    public double getPrice() {
        return price;
    }

    // Getter and Setter for quantity in cart
    public int getQuantityInCart() {
        return quantityInCart;
    }

    public void setQuantityInCart(int quantityInCart) {
        this.quantityInCart = quantityInCart;
    }
}
