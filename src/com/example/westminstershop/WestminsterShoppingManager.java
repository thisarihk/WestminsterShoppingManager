package com.example.westminstershop;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

/**
 * The WestminsterShoppingManager class manages the shopping system, allowing users to add, delete,
 * print, save, and load products. It also provides a simple text-based menu and a GUI interface.
 */
public class WestminsterShoppingManager implements ShoppingManager, Serializable {
    static WestminsterShoppingManager westminsterShoppingManager = new WestminsterShoppingManager();
    static ArrayList<Product> savedProducts = new ArrayList<>();
    Scanner input = new Scanner(System.in);
    public ArrayList<Product> getArrayList() {
        return savedProducts;
    }

    /**
     * Adds a new product to the system.
     * Users can choose between adding a clothing product or an electronic product.
     */
    @Override
    public void addNewProduct() {
        if (savedProducts.size() < 50) {
            System.out.println("----------------------------------------------");
            System.out.println("1) Add a Clothing product \n2) Add an Electronic product");
            System.out.println("----------------------------------------------");
            System.out.print("Enter Your Choice (1 or 2): ");
            String option2 = input.next();
            System.out.println("----------------------------------------------");

            while (!option2.equals("1") && !option2.equals("2")) {
                System.out.println("Invalid Option\nPlease Try Again!");
                System.out.print("Enter Your Choice (1 or 2):");
                option2 = input.next();
                System.out.println("----------------------------------------------");
            }

            String prodId;
            boolean validId;
            String idPattern = "^[A-Za-z0-9]{1,10}$";  //https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html
            do {
                validId = true; // Assume the ID is valid initially

                System.out.print("Enter Product ID: ");
                prodId = input.next();

                // Check if the prodId matches the pattern
                if (!prodId.matches(idPattern)) {
                    System.out.println("Invalid Product ID! Please enter a valid ID.");
                    validId = false;
                } else {
                    for (Product savedProduct : savedProducts) {
                        if (savedProduct.getProductId().equals(prodId)) {
                            System.out.println("Product already Exists! Please Try Again");
                            validId = false;
                            break;
                        }
                    }
                }
            } while (!validId);

            System.out.print("Enter Product Name: ");
            String productName = input.next();

            // Validate available items input
            int numberOfAvailableItems;
            while (true) {
                System.out.print("Enter Number of Available Items: ");
                try {
                    numberOfAvailableItems = Integer.parseInt(input.next());
                    if (numberOfAvailableItems < 0) {
                        throw new IllegalArgumentException("Invalid input. Please enter a valid number.");
                    }
                    input.nextLine(); // Consume the newline character
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number for available items.");
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }


            // Validate price input
            double price;
            while (true) {
                System.out.print("Enter Price: ");
                try {
                    price = Double.parseDouble(input.next());
                    if (price < 0) {
                        throw new IllegalArgumentException("Invalid input. Please enter a non-negative number for Price.");
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number for price.");
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }


            if (option2.equals("1")) {
                String size;
                boolean validSize;

                do {
                    System.out.print("Enter Product Size (XS, S, M, L, XL): ");
                    size = input.next().toUpperCase(); // Convert to uppercase for case-insensitive comparison
                    validSize = size.matches("XS|S|M|L|XL"); //https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/regex/Pattern.html


                    if (!validSize) {
                        System.out.println("Invalid size! Please enter a valid size (XS, S, M, L, XL).");
                    }
                } while (!validSize);

                System.out.print("Enter Product Colour: ");
                String colour = input.next();
                Clothing clothing = new Clothing(prodId, productName, numberOfAvailableItems, price, size, colour);
                savedProducts.add(clothing);
            } else {
                System.out.print("Enter Product Brand: ");
                String productBrand = input.next();
                input.nextLine();
                System.out.print("Enter Warranty Period (in months): ");
                String warrantyPeriod = input.next();
                Electronics electronics = new Electronics(prodId, productName, numberOfAvailableItems, price, productBrand, warrantyPeriod);
                savedProducts.add(electronics);
            }

            System.out.println("\nProduct Added Successfully!");
        } else {
            System.out.println("----------------------------------------------");
            System.out.println("\nThe maximum number of products has been exceeded.");
        }
    }


    /**
     * Deletes a product from the system based on the provided product ID.
     * It displays the details of the deleted product and updates the total number of products.
     */
    @Override
    public void deleteProduct() {
        if (savedProducts.isEmpty()) {
            System.out.println("No products available to delete.");
            System.out.println("\nTotal number of products left in the system: 0");
            return;
        }

        System.out.print("\nEnter Product ID: ");
        String deleteProductID = input.next();
        input.nextLine();

        boolean productFound = false;

        for (int x = 0; x < savedProducts.size(); x++) {
            if (savedProducts.get(x).getProductId().equals(deleteProductID)) {
                Product deletedProduct = savedProducts.remove(x);
                productFound = true;

                System.out.println("Product successfully deleted!");
                System.out.println(deletedProduct.toString());
                break;
            }
        }

        if (!productFound) {
            System.out.println("\nProduct Not Found!");
        }

        System.out.println("\nTotal number of products left in the system: " + savedProducts.size());
    }


    /**
     * Prints the list of products sorted alphabetically by product ID.
     * It checks if there are products available before printing.
     */
    @Override
    public void printProducts() {
        if (savedProducts.isEmpty()) {
            System.out.println("No products available.");
            return;
        }
        // adding all the items to "arraylist" from savedProducts arrayList
        ArrayList<Product> arraylist = new ArrayList<>(savedProducts);
        // Sort the products alphabetically by product ID
        Collections.sort(arraylist, Comparator.comparing(Product::getProductId));

        for (Product product : arraylist) {
            System.out.println(product.toString());
        }
    }

    /**
     * Saves the current list of products to the specified file.
     *
     * @param fileName The name of the file to save the products to.
     */

    @Override
    public void saveProducts(String fileName) {
        try {
            // Proceed with saving products
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            for (Product product : WestminsterShoppingManager.savedProducts) {
                objectOutputStream.writeObject(product);
            }

            objectOutputStream.close();
            System.out.println("Changes saved successfully!");

        } catch (IOException e) {
            System.out.println("An error occurred while saving progress!!! \n" + e);
        }
    }

    /**
     * Loads products from a specified file and replaces the current list of products.
     *
     * @param fileName The name of the file to load products from.
     */
    @Override
    public void loadProducts(String fileName) {
        WestminsterShoppingManager.savedProducts.clear();

        try (FileInputStream fileInputStream = new FileInputStream(fileName);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {

            while (true) {
                try {
                    savedProducts.add((Product) objectInputStream.readObject());
                } catch (EOFException e) {
                    break;  // End of file reached
                }
            }

            System.out.println("Products loaded successfully!");

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("An error occurred while loading products: " + e);
        }
    }


    /**
     * Runs the GUI interface for the shopping system.
     */
    public void runGUI() {
        SwingUtilities.invokeLater(() -> {
            SignUp signUp = new SignUp(WestminsterShoppingManager.this);
            SignUp.start();
        });
    }

    /**
     * Prints the main menu options for the Westminster Shopping system.
     * It reads the user's input and calls the corresponding method based on the selected option.
     */
    public static void printMenu() {
        System.out.println("\n--------------------------------------------");
        System.out.println("Westminster Shopping Menu");
        System.out.println("\n1) Add a new Product");
        System.out.println("2) Delete a Product");
        System.out.println("3) Print Products");
        System.out.println("4) Save Products");
        System.out.println("5) Open the GUI");
        System.out.println("6) Exit the system");
        System.out.println("----------------------------------------------");

        Scanner input = new Scanner(System.in);

        int option;
        while (true) {
            System.out.print("Please enter an option (1-6): ");
            try {
                option = Integer.parseInt(input.nextLine());
                if (option >= 1 && option <= 6) {
                    break;
                } else {
                    System.out.println("Invalid Option, Please Try Again!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid Input. Please enter a number.");
            }
        }

        if (option == 6) {
            System.out.println("\n------------------Thank you!------------------");
            return; // Exit the method
        }

        switch (option) {
            case 1 -> {
                westminsterShoppingManager.addNewProduct();
                printMenu();
            }
            case 2 -> {
                westminsterShoppingManager.deleteProduct();
                printMenu();
            }
            case 3 -> {
                westminsterShoppingManager.printProducts();
                printMenu();
            }
            case 4 -> {
                westminsterShoppingManager.saveProducts("com/example/westminstershop/Products.txt");
                printMenu();
            }
            case 5 -> westminsterShoppingManager.runGUI();
            default -> {
                System.out.println("Invalid Option, Please Try Again!");
                printMenu();
            }
        }
    }

    public static void main(String[] args) {
        File temp = new File("com/example/westminstershop/Products.txt"); //To check if data exists from a previous run
        if (temp.exists()) {
            System.out.println("\nSaved progress has been reloaded.");
            westminsterShoppingManager.loadProducts("com/example/westminstershop/Products.txt");
        }
        printMenu();
    }
}
