package com.example.westminstershop;

public interface ShoppingManager {
    void addNewProduct();
    void deleteProduct();
    void printProducts();
    void saveProducts(String fileName);
    void loadProducts(String fileName);

}
