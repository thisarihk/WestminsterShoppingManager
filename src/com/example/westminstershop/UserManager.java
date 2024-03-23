package com.example.westminstershop;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The UserManager class manages user-related operations, such as saving and loading user details.
 */
public class UserManager {

    /**
     * The file path for storing user details.
     */
    private static final String userDetailsFile = "Users.txt";

    /**
     * The list of users loaded from the file.
     */
    static final List<User> usersList;

    static {
        usersList = loadUsersFile();
    }

    /**
     * Saves user details to the file.
     *
     * @param user The user to be saved.
     */
    public static void saveUsersFile(User user) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(userDetailsFile, true))) {
            writer.println(user.getUsername() + "," + user.getPassword());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads user details from the file.
     *
     * @return The list of users loaded from the file.
     */
    static List<User> loadUsersFile() {
        List<User> users = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(userDetailsFile))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String username = parts[0];
                    String password = parts[1];
                    users.add(new User(username, password));
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found");
        }
        return users;
    }
}
