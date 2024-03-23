package com.example.westminstershop;

import java.io.Serializable;

/**
 * The User class represents a user in the Westminster Shopping Center application.
 * It provides information about the user's username and password.
 */
public class User implements Serializable {

    /**
     * The username of the user.
     */
    private final String username;

    /**
     * The password of the user.
     */
    private final String password;

    /**
     * Constructs a new User with the specified username and password.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Gets the username of the user.
     *
     * @return The username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the password of the user.
     *
     * @return The password of the user.
     */
    public String getPassword() {
        return password;
    }
}
