package Group_Assignment;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ArrayList<User> users = new ArrayList<>();
    private static ArrayList<Event> events = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static int readValidatedInt(Scanner sc, int min, int max) {
        while (true) {
            try {
                String input = sc.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.print("Input cannot be empty. Please enter a number: ");
                    continue;
                }
                int val = Integer.parseInt(input);
                if (val < min || val > max) {
                    System.out.print("Out of range. Please enter a number between " + min + " and " + max + ": ");
                    continue;
                }
                return val;
            } catch (NumberFormatException e) {
                System.out.print("Invalid numeric input. Please enter a valid integer: ");
            } catch (Exception e) {
                System.out.print("Error reading input. Try again: ");
            }
        }
    }

    public static String readNonEmptyString(Scanner sc, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = sc.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println("Error: Field cannot be left empty. Try again.");
                    continue;
                }
                return input;
            } catch (Exception e) {
                System.out.println("Error reading string input: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        users.add(new Admin("admin", "admin", "Super"));
        users.add(new NormalUser("student", "student", "Regular"));

        events.add(new Event("SDG Alignment Briefing (Climate Action)", "2026-07-30", 100));

        boolean running = true;
        while (running) {
            System.out.println("\n--- Student Club Membership & Event Scheduler ---");
            System.out.println("1. Login");
            System.out.println("2. Register New Member");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");

            int choice = readValidatedInt(scanner, 1, 3);
            switch (choice) {
                case 1:
                    handleLogin();
                    break;
                case 2:
                    handleRegistration();
                    break;
                case 3:
                    running = false;
                    System.out.println("Exiting application. Goodbye!");
                    break;
            }
        }
    }

    private static void handleLogin() {
        String uname = readNonEmptyString(scanner, "Enter username: ");
        String pass = readNonEmptyString(scanner, "Enter password: ");

        if (uname.equalsIgnoreCase("sudo")) {
            System.out.println("[sudo] password for " + uname + ": ");
            System.out.println(uname + " is not in the sudoers file. This incident will be reported.");
            return;
        }

        User loggedInUser = null;
        for (User u : users) {
            if (u.getUsername().equals(uname) && u.getPassword().equals(pass)) {
                loggedInUser = u;
                break;
            }
        }

        if (loggedInUser != null) {
            System.out.println("Login successful as " + loggedInUser.getRole() + "!");
            // Passes both events and users to support management features
            loggedInUser.displayDashboard(scanner, events, users);
        } else {
            System.out.println("Authentication failed. Invalid username or password.");
        }
    }

    private static void handleRegistration() {
        String uname = readNonEmptyString(scanner, "Enter new username: ");
        String pass = readNonEmptyString(scanner, "Enter password: ");

        users.add(new NormalUser(uname, pass, "Regular"));
        System.out.println("Registration successful! You can now log in.");
    }
}