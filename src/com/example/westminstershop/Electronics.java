package com.example.westminstershop;

import java.io.Serializable;

public class Electronics extends Product implements Serializable {
    private final String brand;
    private final String warrantyPeriod;

    public Electronics(String productId, String productName, int availableItems, double price, String brand, String warrantyPeriod) {
        super(productId, productName, availableItems, price);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    // Getter and Setter for brand
    public String getBrand() {
        return brand;
    }

    // Getter and Setter for warrantyPeriod
    public String getWarrantyPeriod() {
        return warrantyPeriod;
    }

    // In the Electronics class
    @Override
    public String toString() {
        return "\n"+"Product ID: " + getProductId() +
                "\nProduct name: " + getProductName() +
                "\nNumber of available items: " + getAvailableItems() +
                "\nPrice: " + getPrice() +
                "\nProduct Brand: " + getBrand() +
                "\nProduct Warranty Period: " + getWarrantyPeriod()+
                "\nProduct Type: Electronics";
    }

}
