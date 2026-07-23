import java.util.ArrayList;
import java.util.Scanner;

interface Reportable {
    void generateReport();
}

abstract class User implements Reportable {
    private String username;
    private String password;
    private String role;

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public abstract void displayDashboard(Scanner scanner, ArrayList<Event> events);
}

class Admin extends User {
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
    public void displayDashboard(Scanner scanner, ArrayList<Event> events) {
        boolean sessionActive = true;
        while (sessionActive) {
            System.out.println("\n=== Admin Dashboard: " + getUsername() + " (Level: " + adminLevel + ") ===");
            System.out.println("1. Manage Members");
            System.out.println("2. Schedule Event");
            System.out.println("3. View All Upcoming Events");
            System.out.println("4. Logout");

            System.out.print("Choose admin option: ");

            int choice = ClubManagementApp.readValidatedInt(scanner, 1, 4);
            switch (choice) {
                case 1:
                    System.out.println("Managing members: Total registered users in system.");
                    break;
                case 2:
                    String name = ClubManagementApp.readNonEmptyString(scanner, "Enter new event name: ");
                    String date = ClubManagementApp.readNonEmptyString(scanner, "Enter event date (YYYY-MM-DD): ");
                    System.out.print("Enter event capacity: ");
                    int cap = ClubManagementApp.readValidatedInt(scanner, 1, 10000);
                    events.add(new Event(name, date, cap));
                    System.out.println("Event successfully scheduled!");
                    break;
                case 3:
                    generateReport();
                    System.out.println("Current Scheduled Events:");
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

    @Override
    public void generateReport() {
        System.out.println("Generating Comprehensive Club Admin Report...");
    }
}

class NormalUser extends User {
    private String memberType;

    public NormalUser(String username, String password, String memberType) {
        super(username, password, "Member");
        this.memberType = memberType;
    }

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    @Override
    public void displayDashboard(Scanner scanner, ArrayList<Event> events) {
        boolean sessionActive = true;
        while (sessionActive) {
            System.out.println("\n=== Member Dashboard: " + getUsername() + " (" + memberType + " Member) ===");
            System.out.println("1. View Events");
            System.out.println("2. Register for Event");
            System.out.println("3. View My Profile");
            System.out.println("4. Logout");
            System.out.print("Choose member option: ");

            int choice = ClubManagementApp.readValidatedInt(scanner, 1, 4);
            switch (choice) {
                case 1:
                    System.out.println("Available Club Events:");
                    for (int i = 0; i < events.size(); i++) {
                        System.out.println((i + 1) + ". " + events.get(i));
                    }
                    break;
                case 2:
                    System.out.println("Event registration feature accessed. You are signed up for upcoming sessions.");
                    break;
                case 3:
                    generateReport();
                    break;
                case 4:
                    sessionActive = false;
                    System.out.println("Logging out from Member session...");
                    break;
            }
        }
    }

    @Override
    public void generateReport() {
        System.out.println("Generating Member Activity Report for user: " + getUsername());
    }
}

class Event {
    private String eventName;
    private String eventDate;
    private int capacity;

    public Event(String eventName, String eventDate, int capacity) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.capacity = capacity;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "Event: " + eventName + " | Date: " + eventDate + " | Capacity: " + capacity;
    }
}

 class ClubManagementApp {
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

    public static double readValidatedDouble(Scanner sc, double min, double max) {
        while (true) {
            try {
                String input = sc.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.print("Input cannot be empty. Please enter a number: ");
                    continue;
                }
                double val = Double.parseDouble(input);
                if (val < min || val > max) {
                    System.out.print("Out of range. Please enter a value between " + min + " and " + max + ": ");
                    continue;
                }
                return val;
            } catch (NumberFormatException e) {
                System.out.print("Invalid numeric input. Please enter a valid decimal number: ");
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
        users.add(new NormalUser("student1", "1234", "Regular"));

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

        User loggedInUser = null;
        for (User u : users) {
            if (u.getUsername().equals(uname) && u.getPassword().equals(pass)) {
                loggedInUser = u;
                break;
            }
        }

        if (loggedInUser != null) {
            System.out.println("Login successful as " + loggedInUser.getRole() + "!");
            loggedInUser.displayDashboard(scanner, events);
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
