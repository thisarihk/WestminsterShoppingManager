package com.example.westminstershop;

import java.util.ArrayList;
import java.util.List;

/**
 * The ShoppingCart class represents a shopping cart in the Westminster Shopping application.
 * It allows users to add products to the cart.
 */
public class ShoppingCart {
    private final ArrayList<Product> products;

    /**
     * Constructs a ShoppingCart object.
     * Initializes the list of products in the cart.
     */
    public ShoppingCart() {
        this.products = new ArrayList<>();
    }

    /**
     * Adds a product to the shopping cart.
     *
     * @param product The product to be added to the cart.
     */
    public void addProduct(Product product) {
        this.products.add(product);
        System.out.println(product.getProductName() + " added to the cart.");
    }

    /**
     * Gets the list of products in the shopping cart.
     *
     * @return The list of products in the cart.
     */
    public List<Product> getCart() {
        return products;
    }
}
