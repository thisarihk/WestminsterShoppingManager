package com.example.westminstershop;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * The ShoppingApplication class represents the GUI application for the Westminster Shopping Center.
 * It provides functionality to display product details, manage the shopping cart, and handle user interactions.
 */
public class ShoppingApplication {
    private static List<Product> productsList;
    private static ShoppingCart shoppingCart;
    private JTextArea productDetailsTextArea;
    private JFrame shoppingCartFrame;
    private JTable shoppingCartTable;


    /**
     * Sets the initial product list for the shopping application.
     *
     * @param productArrayList The initial list of products to be displayed in the application.
     */
    public static void setProductList(ArrayList<Product> productArrayList) {
        productsList = new ArrayList<>(productArrayList);
        shoppingCart = new ShoppingCart();
    }

    //Creating shopping Cart frame and the table
    private void shoppingCartFrame() {
        shoppingCartFrame = new JFrame("Shopping Cart");
        shoppingCartFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        String[] columnNames = {"Product", "Quantity", "Price(€)"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        shoppingCartTable = new JTable(model);

        // Set custom cell renderer to center-align the data
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < shoppingCartTable.getColumnCount(); i++) {
            shoppingCartTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane tableScrollPane = new JScrollPane(shoppingCartTable);

        // Add padding to the table by setting an EmptyBorder
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));

        JTextArea shoppingCartTextArea = new JTextArea();
        shoppingCartTextArea.setEditable(false);
        shoppingCartTextArea.setLineWrap(true);

        JPanel shoppingCartLayout = new JPanel(new BorderLayout());
        shoppingCartLayout.add(tableScrollPane, BorderLayout.CENTER);
        shoppingCartLayout.add(shoppingCartTextArea, BorderLayout.SOUTH);

        shoppingCartFrame.setContentPane(shoppingCartLayout);
        shoppingCartFrame.setSize(800, 400);
        // set frame not resizable
        shoppingCartFrame.setResizable(false);
        shoppingCartFrame.setLocationRelativeTo(null);
    }


    /**
     * Displays the shopping cart in a separate frame.
     */
    private void displayShoppingCart() {
        if (shoppingCartFrame == null) {
            shoppingCartFrame();
        }
        refreshCartTable();
        shoppingCartFrame.setVisible(true);
    }


    /**
     * Starts the Westminster Shopping application by creating the main GUI frame and initializing components.
     */
    public void start() {
        // Create the main JFrame for the Westminster Shopping Center
        JFrame frame = new JFrame("Westminster Shopping Center");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the product table view
        JTable tableView = createProductTable();

        // Create the top panel containing the shopping cart button
        JPanel topPanel = topPanel();

        // Create the frame for displaying the shopping cart
        shoppingCartFrame();

        // Create the filter panel for selecting product categories
        JPanel filterPanel = dropDown(tableView, shoppingCartTable);

        // Add the product table to a scroll pane for better visibility
        JScrollPane tableScrollPane = new JScrollPane(tableView);

        // Create a text area for displaying product details
        productDetailsTextArea = new JTextArea();
        JScrollPane productDetailsScrollPane = new JScrollPane(productDetailsTextArea);

        // Create the bottom bar containing the "Add to Shopping Cart" button
        JPanel bottomBar = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton addToCartButton = new JButton("Add to Shopping Cart");
        addToCartButton.addActionListener(e -> insertIntoCart(tableView, tableView.getSelectedRow()));
        bottomBar.add(addToCartButton);

        // Create the main panel with a border layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);
        mainPanel.add(productDetailsScrollPane, BorderLayout.SOUTH);

        // Create a container panel to organize the top, center, and bottom panels
        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.add(topPanel, BorderLayout.NORTH);
        containerPanel.add(createCenteredPanel(filterPanel), BorderLayout.CENTER);
        containerPanel.add(mainPanel, BorderLayout.SOUTH);

        // Create the main layout panel with a border layout
        JPanel layout = new JPanel(new BorderLayout());
        layout.add(containerPanel, BorderLayout.CENTER);
        layout.add(bottomBar, BorderLayout.SOUTH);

        frame.setContentPane(layout);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JPanel createCenteredPanel(JPanel panel) {
        JPanel centeredPanel = new JPanel(new GridBagLayout());
        centeredPanel.add(panel);
        return centeredPanel;
    }


    // Method to create and configure the product table
    private JTable createProductTable() {

        String[] columnNames = {"Product ID", "Name", "Category", "Price(€)", "Info"};

        // Create a non-editable DefaultTableModel for the table
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);

        // Set preferred column widths
        int[] columnWidths = {50, 50, 50, 50, 300};
        for (int i = 0; i < columnWidths.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
        }

        // Set custom cell renderer to center-align the data in each column
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Disable default cell editor to make cells non-editable
        table.setDefaultEditor(Object.class, null);

        // Update the table data with the current product list
        refreshTableData(table);

        // Add a list selection listener to respond when a row is selected
        table.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0 && selectedRow < productsList.size()) {
                // Retrieve the product ID from the selected row
                String prodId = table.getValueAt(selectedRow, 0).toString();

                // Find the selected product from the product list using the ID
                Product selectedProduct = productsList.stream().filter(prod -> {
                    if (prod.getProductId().equals(prodId)) {
                        return true;
                    }
                    return false;
                }).findFirst().get();

                // Display details of the selected product
                showProductDetails(selectedProduct);
            }
        });

        return table;
    }

    //Creating Shopping Cart Button
    private JPanel topPanel() {
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton shoppingCartButton = new JButton("Shopping Cart");
        shoppingCartButton.addActionListener(e -> displayShoppingCart());
        topBar.add(shoppingCartButton);
        return topBar;
    }


    //Handle Drop Down menu for product selection
    private JPanel dropDown(JTable tableView, JTable shoppingCartTable) {
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        String[] categories = {"All", "Clothing","Electronics"};
        JComboBox<String> categoryComboBox = new JComboBox<>(categories);
        categoryComboBox.setSelectedIndex(0);

        categoryComboBox.addActionListener(e -> handleCategory((String) Objects.requireNonNull(categoryComboBox.getSelectedItem()), tableView, shoppingCartTable));

        JLabel categoryLabel = new JLabel("Select Product Category:");
        filterPanel.add(categoryLabel);
        filterPanel.add(categoryComboBox);

        return filterPanel;
    }

    // Method to update the table data (useful if productList changes)
    private void refreshTableData(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        for (Product product : productsList) {
            Object[] rowData = {
                    product.getProductId(),
                    product.getProductName(),
                    printCategory(product),
                    String.format("%.2f", product.getPrice()),
                    showProductInfo(product)
            };
            model.addRow(rowData);
        }
    }

    // Update the productList to reflect changes in available items
    private void refreshProductList(Product updatedProduct) {
        for (int i = 0; i < productsList.size(); i++) {
            Product product = productsList.get(i);
            if (product.getProductId().equals(updatedProduct.getProductId())) {
                productsList.set(i, updatedProduct);
                break;
            }
        }
    }

    //Update the Cart Table
    private void refreshCartTable() {
        DefaultTableModel model = (DefaultTableModel) shoppingCartTable.getModel();
        model.setRowCount(0);

        // Create a map to store the quantity of each product category
        Map<String, Integer> categoryQuantityMap = new HashMap<>();

        for (Product product : shoppingCart.getCart()) {
            String productInfo;
            double productPrice;

            if (product instanceof Electronics electronicsProduct) {
                productInfo = electronicsProduct.getProductId() + ", " + electronicsProduct.getProductName() + ", " + electronicsProduct.getBrand() + ", " + electronicsProduct.getWarrantyPeriod();
                productPrice = electronicsProduct.getPrice();
            } else if (product instanceof Clothing clothingProduct) {
                productInfo = clothingProduct.getProductId() + ", " + clothingProduct.getProductName() + ", " + clothingProduct.getSize() + ", " + clothingProduct.getColor();
                productPrice = clothingProduct.getPrice();
            } else {
                // Handle other product types if needed
                continue;
            }

            Object[] rowData = {productInfo, product.getQuantityInCart(), productPrice};
            model.addRow(rowData);

            // Update the quantity for the product category in the map
            categoryQuantityMap.put(product.getProductId(), categoryQuantityMap.getOrDefault(product.getProductId(), 0) + product.getQuantityInCart());
        }

        double totalCost = calculateTotalPrice(shoppingCart.getCart());
        double totalDiscount = calculateDiscountAmount(shoppingCart.getCart(), categoryQuantityMap);
        double finalTotalCost = totalCost - totalDiscount;
        JTextArea shoppingCartTextArea = (JTextArea) ((BorderLayout) shoppingCartFrame.getContentPane().getLayout()).getLayoutComponent(BorderLayout.SOUTH);

        shoppingCartTextArea.setText(String.format("                                                                                    " +
                "                                                                                                     Total                          €%.2f\n" +
                "                                                                                    " +
                "                                                                              Total Discount(20%%)                         €%.2f\n" +
                "                                                                                     " +
                "                                                                                             Final Total                        €%.2f", totalCost, totalDiscount, finalTotalCost));
    }


    /**
     * Handles the selection of a product category from the drop-down menu.
     * Updates the displayed products based on the selected category.
     *
     * @param selectedOption     The selected product category.
     * @param tableView          The main product table.
     * @param shoppingCartTable  The shopping cart table.
     */
    private void handleCategory(String selectedOption, JTable tableView, JTable shoppingCartTable) {
        DefaultTableModel mainTableModel = (DefaultTableModel) tableView.getModel();
        mainTableModel.setRowCount(0);

        DefaultTableModel cartTableModel = (DefaultTableModel) shoppingCartTable.getModel();
        cartTableModel.setRowCount(0);

        switch (selectedOption) {
            case "All" -> {
                insertProductsIntoTable(productsList, mainTableModel);
                insertProductsIntoTable(shoppingCart.getCart(), cartTableModel);
            }
            case "Clothing" -> insertProductsIntoTable(getClothes(), mainTableModel);
            case "Electronics" -> insertProductsIntoTable(getElectronics(), mainTableModel);
        }
    }


    // Method to add a selected product to the shopping cart
    private void insertIntoCart(JTable table, int selectedIndex) {
        if (selectedIndex >= 0 && selectedIndex < productsList.size()) {
            // Retrieve the product ID from the selected row
            String prodId = table.getValueAt(selectedIndex, 0).toString();


            // Find the selected product from the product list using the ID
            Product ChosenProduct = productsList.stream().filter(prod -> {
                if (prod.getProductId().equals(prodId)) {
                    return true;
                }
                return false;
            }).findFirst().get();

            int availableItems = ChosenProduct.getAvailableItems();

            // Check if there are available items for the selected product
            if (availableItems > 0) {
                ChosenProduct.setAvailableItems(availableItems - 1);


                // Find the existing product in the shopping cart by ID
                Product cartProducts = searchProductInCart(ChosenProduct.getProductId());

                // Check if the selected product is already in the cart
                if (cartProducts != null) {
                    cartProducts.setQuantityInCart(cartProducts.getQuantityInCart() + 1);
                } else {
                    // If not, add the selected product to the shopping cart
                    ChosenProduct.setQuantityInCart(1);
                    shoppingCart.addProduct(ChosenProduct);
                }

                // Update the productList to reflect changes in available items
                refreshProductList(ChosenProduct);

                // Display the updated product details
                showProductDetails(ChosenProduct);

                // Update the shopping cart table
                refreshCartTable();
            }
        }
    }

    // Display product details in the text area
    private void showProductDetails(Product product) {
        StringBuilder infoText = new StringBuilder("Selected Product - Details\n\n");

        if (product instanceof Electronics electronicsProduct) {
            insertDetailLine(infoText, "Product ID", electronicsProduct.getProductId());
            insertDetailLine(infoText, "Product Category", "Electronics");
            insertDetailLine(infoText, "Product Name", electronicsProduct.getProductName());
            insertDetailLine(infoText, "Brand", electronicsProduct.getBrand());
            insertDetailLine(infoText, "Warranty Period", electronicsProduct.getWarrantyPeriod());
            insertDetailLine(infoText, "Items Available", String.valueOf(electronicsProduct.getAvailableItems()));
        } else if (product instanceof Clothing clothingProduct) {
            insertDetailLine(infoText, "Product ID", clothingProduct.getProductId());
            insertDetailLine(infoText, "Product Category", "Clothing");
            insertDetailLine(infoText, "Product Name", clothingProduct.getProductName());
            insertDetailLine(infoText, "Size", clothingProduct.getSize());
            insertDetailLine(infoText, "Color", clothingProduct.getColor());
            insertDetailLine(infoText, "Items Available", String.valueOf(clothingProduct.getAvailableItems()));
        } else {
            infoText.append("No additional information available");
        }

        productDetailsTextArea.setText(infoText.toString());
        productDetailsTextArea.setEditable(false);
    }

    private void insertDetailLine(StringBuilder detailsText, String label, String value) {
        detailsText.append(label).append(": ").append(value).append("\n");
    }


    /**
     * Calculates the total discount amount based on the products and category quantities.
     *
     * @param products           The list of products in the shopping cart.
     * @param categoryQuantityMap A map containing the quantity of each product category.
     * @return The total discount amount.
     */
    private double calculateDiscountAmount(List<Product> products, Map<String, Integer> categoryQuantityMap) {
        double totalDiscount = 0;

        for (Product product : products) {
            // Apply 20% discount if the quantity of the product category is at least three
            double discountMultiplier = (categoryQuantityMap.getOrDefault(product.getProductId(), 0) >= 3) ? 0.2 : 0.0;
            totalDiscount += product.getPrice() * product.getQuantityInCart() * discountMultiplier;
        }

        return totalDiscount;
    }


    /**
     * Calculates the total price of the products in the shopping cart.
     *
     * @param products The list of products in the shopping cart.
     * @return The total price of the products.
     */
    private double calculateTotalPrice(List<Product> products) {
        double totalCost = 0;
        for (Product product : products) {
            totalCost += product.getPrice() * product.getQuantityInCart();
        }
        return totalCost;
    }

    private ArrayList<Product> getElectronics() {
        ArrayList<Product> electronics = new ArrayList<>();
        for (Product product : productsList) {
            if (product instanceof Electronics electronicsProduct) {
                electronics.add(electronicsProduct);
            }
        }
        return electronics;
    }


    private ArrayList<Product> getClothes() {
        ArrayList<Product> clothes = new ArrayList<>();
        for (Product product : productsList) {
            if (product instanceof Clothing clothingProduct) {
                clothes.add(clothingProduct);
            }
        }
        return clothes;
    }
    /**
     * Inserts products into the specified table model for display.
     *
     * @param products The list of products to be inserted into the table.
     * @param model    The table model to update with the product data.
     */
    private void insertProductsIntoTable(List<Product> products, DefaultTableModel model) {
        // Sort products alphabetically based on product ID
        products.sort(Comparator.comparing(Product::getProductId));
        for (Product product : products) {
            Object[] rowData = {
                    product.getProductId(),
                    product.getProductName(),
                    printCategory(product),
                    String.format("%.2f", product.getPrice()),
                    showProductInfo(product)
            };
            model.addRow(rowData);
        }
    }


    private String showProductInfo(Product product) {
        if (product instanceof Electronics electronicsProduct) {
            return electronicsProduct.getBrand() + ", " + electronicsProduct.getWarrantyPeriod();
        } else if (product instanceof Clothing clothingProduct) {
            return clothingProduct.getSize() + ", " + clothingProduct.getColor();
        } else {
            return "No additional information available";
        }
    }

    private Product searchProductInCart(String productId) {
        return shoppingCart.getCart().stream()
                .filter(product -> product.getProductId().equals(productId))
                .findFirst()
                .orElse(null);
    }


    private String printCategory(Product product) {
        if (product instanceof Electronics) {
            return "Electronics";
        } else if (product instanceof Clothing) {
            return "Clothing";
        } else {
            return "Unknown Category";
        }
    }

}
