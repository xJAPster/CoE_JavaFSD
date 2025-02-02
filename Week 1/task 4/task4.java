/*
 * Objective: Demonstrate the use of Java I/O streams by writing and reading a list of user data to a file.
    • Details: Create a class UserManager that allows adding users with details such as name and email. Implement methods to save these details to a file and read them back.
    • Functions to Implement:
        ◦ addUser(String name, String email): Adds a user to the system.
        ◦ saveUsersToFile(String filename): Saves all user details to a file.
        ◦ loadUsersFromFile(String filename): Loads user details from a file.
 */



import java.io.*;
import java.util.*;




class User {
    String name;
    String email;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    @Override
    public String toString() {
        return name + "," + email;
    }

    public static User fromString(String str) {
        String[] parts = str.split(",");
        return new User(parts[0], parts[1]);
    }
}

class UserManager {
    private List<User> users;

    public UserManager() {
        this.users = new ArrayList<>();
    }

    public void addUser(String name, String email) {
        users.add(new User(name, email));
    }

    public void saveUsersToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (User user : users) {
                writer.write(user.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving users to file: " + e.getMessage());
        }
    }

    public void loadUsersFromFile(String filename) {
        users.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                users.add(User.fromString(line));
            }
        } catch (IOException e) {
            System.out.println("Error loading users from file");
        }
    }

    public void displayUsers() {
        if (users.isEmpty()) {
            System.out.println("No users available");
        } else {
            System.out.println("Users in the system:");
            for (User user : users) {
                System.out.println(user.name + " | " + user.email);
            }
        }
    }
}

public class task4 {
    public static void main(String[] args) {
        UserManager userManager = new UserManager();

        userManager.addUser("Alice", "alice@example.com");
        userManager.addUser("Bob", "bob@example.com");
        userManager.addUser("Charlie", "charlie@example.com");

        userManager.saveUsersToFile("users_file_for_task_4.txt");

        userManager.loadUsersFromFile("users_file_for_task_4.txt");

        userManager.displayUsers();
    }
}
