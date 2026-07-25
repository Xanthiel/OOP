package Group_Assignment;

import java.util.ArrayList;
import java.util.Scanner;

public class Admin extends User {
    private String adminLevel;

    public Admin(String username, String password, String adminLevel) {
        super(username, password, "Admin");
        this.adminLevel = adminLevel;
    }

    public String getAdminLevel() {
        return adminLevel;
    }

    public void setAdminLevel(String adminLevel) {
        this.adminLevel = adminLevel;
    }

    @Override
    public void displayDashboard(Scanner scanner, ArrayList<Event> events, ArrayList<User> users) {
        boolean sessionActive = true;
        while (sessionActive) {
            System.out.println("\n=== Admin Dashboard: " + getUsername() + " (Level: " + adminLevel + ") ===");
            System.out.println("1. Manage Members");
            System.out.println("2. Schedule Event");
            System.out.println("3. View All Reports");
            System.out.println("4. Logout");
            System.out.print("Choose admin option: ");

            int choice = Main.readValidatedInt(scanner, 1, 4);
            switch (choice) {
                case 1:
                    manageMembersMenu(scanner, users);
                    break;
                case 2:
                    String name = Main.readNonEmptyString(scanner, "Enter new event name: ");
                    String date = Main.readNonEmptyString(scanner, "Enter event date (YYYY-MM-DD): ");
                    System.out.print("Enter event capacity: ");
                    int cap = Main.readValidatedInt(scanner, 1, 10000);
                    events.add(new Event(name, date, cap));
                    System.out.println("Event successfully scheduled!");
                    break;
                case 3:
                    generateReport();
                    System.out.println("\nCurrent Scheduled Events:");
                    for (Event e : events) {
                        System.out.println(" - " + e);
                    }
                    break;
                case 4:
                    sessionActive = false;
                    System.out.println("Logging out from Admin session...");
                    break;
            }
        }
    }

    private void manageMembersMenu(Scanner scanner, ArrayList<User> users) {
        boolean managing = true;
        while (managing) {
            System.out.println("\n--- User Management ---");
            System.out.println("1. View All Registered Users");
            System.out.println("2. Add New User");
            System.out.println("3. Delete User");
            System.out.println("4. Back to Admin Main Menu");
            System.out.print("Choose option: ");

            int subChoice = Main.readValidatedInt(scanner, 1, 4);
            switch (subChoice) {
                case 1:
                    System.out.println("\nRegistered System Users:");
                    for (int i = 0; i < users.size(); i++) {
                        User u = users.get(i);
                        System.out.println((i + 1) + ". " + u.getUsername() + " | Role: " + u.getRole());
                    }
                    break;
                case 2:
                    String newUname = Main.readNonEmptyString(scanner, "Enter new username: ");
                    String newPass = Main.readNonEmptyString(scanner, "Enter password: ");
                    System.out.println("Select Role: 1. Normal Member  2. Admin");
                    int roleChoice = Main.readValidatedInt(scanner, 1, 2);
                    if (roleChoice == 1) {
                        users.add(new NormalUser(newUname, newPass, "Regular"));
                    } else {
                        users.add(new Admin(newUname, newPass, "Standard"));
                    }
                    System.out.println("User '" + newUname + "' successfully added!");
                    break;
                case 3:
                    System.out.println("\nSelect user to remove:");
                    for (int i = 0; i < users.size(); i++) {
                        System.out.println((i + 1) + ". " + users.get(i).getUsername() + " (" + users.get(i).getRole() + ")");
                    }
                    int removeIdx = Main.readValidatedInt(scanner, 1, users.size()) - 1;

                    if (users.get(removeIdx).getUsername().equals(this.getUsername())) {
                        System.out.println("Error: You cannot delete your own active account!");
                    } else {
                        String removedName = users.get(removeIdx).getUsername();
                        users.remove(removeIdx);
                        System.out.println("User '" + removedName + "' removed successfully.");
                    }
                    break;
                case 4:
                    managing = false;
                    break;
            }
        }
    }

    @Override
    public void generateReport() {
        System.out.println("Generating Comprehensive Club Admin Report...");
    }
}