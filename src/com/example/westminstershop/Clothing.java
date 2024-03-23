package com.example.westminstershop;

import java.io.Serializable;

/**
 * The Clothing class represents a clothing product in the Westminster Shopping application.
 * It extends the Product class and includes additional attributes for size and color.
 */
public class Clothing extends Product implements Serializable {
    private final String size;
    private final String color;

    /**
     * Constructs a Clothing object with the specified attributes.
     *
     * @param productId      The unique identifier of the clothing product.
     * @param productName    The name of the clothing product.
     * @param availableItems The number of available items in stock.
     * @param price          The price of the clothing product.
     * @param size           The size of the clothing (e.g., small, medium, large).
     * @param color          The color of the clothing product.
     */
    public Clothing(String productId, String productName, int availableItems, double price, String size, String color) {
        super(productId, productName, availableItems, price);
        this.size = size;
        this.color = color;
    }

    /**
     * Gets the color of the clothing product.
     *
     * @return The color of the clothing.
     */
    public String getColor() {
        return color;
    }

    /**
     * Gets the size of the clothing product.
     *
     * @return The size of the clothing.
     */
    public String getSize() {
        return size;
    }

    /**
     * Returns a string representation of the Clothing object.
     * Includes details such as product ID, name, available items, price, size, color, and product type.
     *
     * @return A string representation of the Clothing object.
     */
    @Override
    public String toString() {
        return "\n" + "Product ID: " + getProductId() +
                "\nProduct name: " + getProductName() +
                "\nNumber of available items: " + getAvailableItems() +
                "\nPrice: " + getPrice() +
                "\nClothing Size: " + getSize() +
                "\nClothing Colour: " + getColor() +
                "\nProduct Type: Clothing";
    }
}
