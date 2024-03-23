package com.example.westminstershop;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * The Login class represents the GUI for user login in the Westminster Shopping application.
 */
public class Login {
    private final JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private final ArrayList<User> userList;
    private final WestminsterShoppingManager shoppingManager;

    /**
     * Constructs a Login object.
     *
     * @param shoppingManager The WestminsterShoppingManager instance.
     */
    public Login(WestminsterShoppingManager shoppingManager) {
        this.shoppingManager = shoppingManager;
        userList = loadUsers();

        frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        frame.setContentPane(contentPane);

        // Create and configure components on the panel
        createComponents(contentPane);

        frame.setSize(350, 180);

        // Set frame not resizable
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * The entry point for the Login class.
     */
    public void start() {
    }

    /**
     * Creates and configures components on the login panel.
     *
     * @param contentPane The JPanel to which components are added.
     */
    private void createComponents(JPanel contentPane) {
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        usernameField = new JTextField(10);
        passwordField = new JPasswordField(10);
        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(e -> authenticateUser());

        JPanel loginPanel = new JPanel(new GridLayout(3, 2, 10, 5));
        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(new JLabel()); // Empty label for layout
        loginPanel.add(loginButton);

        contentPane.add(loginPanel, BorderLayout.CENTER);
    }

    /**
     * Loads user information from the user details file.
     *
     * @return The list of User objects.
     */
    private ArrayList<User> loadUsers() {
        return (ArrayList<User>) UserManager.loadUsersFile();
    }

    /**
     * Validates user login credentials.
     *
     * @param enteredUsername The username entered by the user.
     * @param enteredPassword The password entered by the user.
     * @return True if the credentials are valid; otherwise, false.
     */
    private boolean validateLogin(String enteredUsername, String enteredPassword) {
        for (User user : userList) {
            // Convert both the input username and stored username to lowercase for case-insensitive comparison
            String usernameLowercase = user.getUsername().toLowerCase();
            String enteredUsernameLowerCase = enteredUsername.toLowerCase();

            if (usernameLowercase.equals(enteredUsernameLowerCase) && user.getPassword().equals(enteredPassword)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Authenticates the user and opens the main shopping application on successful login.
     */
    private void authenticateUser() {
        String enteredUsername = usernameField.getText();
        char[] enteredPasswordChars = passwordField.getPassword();
        String enteredPassword = new String(enteredPasswordChars);

        if (validateLogin(enteredUsername, enteredPassword)) {
            JOptionPane.showMessageDialog(frame, "Login successful. Welcome!");

            ShoppingApplication app = new ShoppingApplication();
            ShoppingApplication.setProductList(new ArrayList<>(shoppingManager.getArrayList()));
            SwingUtilities.invokeLater(app::start);

            frame.dispose();
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid credentials. Please check your username and password.", "Authentication Error", JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
        }
    }
}
