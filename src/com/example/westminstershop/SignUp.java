package com.example.westminstershop;

import javax.swing.*;
import java.util.ArrayList;

/**
 * The SignUp class represents the GUI for user sign-up in the Westminster Shopping application.
 */
public class SignUp {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private final WestminsterShoppingManager shoppingManager;

    /**
     * Constructs a SignUp object.
     *
     * @param shoppingManager The WestminsterShoppingManager instance.
     */
    public SignUp(WestminsterShoppingManager shoppingManager) {
        this.shoppingManager = shoppingManager;

        // Create and configure the sign-up frame
        JFrame frame = new JFrame("Sign Up");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel);

        // Set up the components on the panel
        placeComponents(panel);

        frame.setSize(350, 200);

        // Set frame not resizable
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * The entry point for the SignUp class.
     */
    public static void start() {
    }

    /**
     * Configures the layout of components on the panel.
     *
     * @param panel The JPanel to which components are added.
     */
    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(40, 25, 100, 20);
        panel.add(userLabel);

        usernameField = new JTextField(20);
        usernameField.setBounds(130, 25, 170, 25);
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(40, 60, 100, 20);
        panel.add(passwordLabel);

        passwordField = new JPasswordField(20);
        passwordField.setBounds(130, 60, 170, 25);
        panel.add(passwordField);

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(40, 100, 120, 25);
        panel.add(signUpButton);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(180, 100, 120, 25);
        panel.add(loginButton);

        signUpButton.addActionListener(e -> userSignUp());

        loginButton.addActionListener(e -> {
            SwingUtilities.getWindowAncestor(usernameField).dispose();
            openLogin();
        });
    }

    /**
     * Handles the sign-up process by collecting user input and saving the user information.
     */
    private void userSignUp() {
        String username = usernameField.getText();
        char[] passwordChars = passwordField.getPassword();
        String password = new String(passwordChars);

        // Check if the user already exists
        if (alreadySignedUp(username)) {
            JOptionPane.showMessageDialog(null, "Sorry, this username is already in use. Please pick another username.");
            return;  // Don't proceed with signing up if the user already exists
        }

        User user = new User(username, password);

        saveUserInfo(user);

        JOptionPane.showMessageDialog(null, "Congratulations! You've successfully signed up.");

        // Close the sign-up frame
        SwingUtilities.getWindowAncestor(usernameField);
    }

    /**
     * Checks if the entered username already exists in the list of users.
     *
     * @param enteredUsername The username entered by the user.
     * @return True if the username already exists; otherwise, false.
     */
    private boolean alreadySignedUp(String enteredUsername) {
        ArrayList<User> existingUsersList = (ArrayList<User>) UserManager.loadUsersFile();

        // Convert the entered username to lowercase for case-insensitive comparison
        String usernameLowercase = enteredUsername.toLowerCase();

        // Check if the username already exists in the loaded users list
        for (User user : existingUsersList) {
            if (user.getUsername().toLowerCase().equals(usernameLowercase)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Saves the user information to the user details file.
     *
     * @param user The User object containing the user information.
     */
    private void saveUserInfo(User user) {
        UserManager.saveUsersFile(user);
    }

    /**
     * Opens the login window.
     */
    private void openLogin() {
        Login loginApp = new Login(shoppingManager);
        SwingUtilities.invokeLater(loginApp::start);
    }
}
