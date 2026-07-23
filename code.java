import java.util.ArrayList;
import java.util.InputMismatchException;
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

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public abstract void showDashboardMenu(Scanner sc, RecyclingManager mgr);

    @Override
    public void generateReport() {
        System.out.println("Activity Report for: " + username);
    }
}

class Resident extends User {
    private double totalRecycledKg;

    public Resident(String username, String password) {
        super(username, password, "RESIDENT");
        this.totalRecycledKg = 0.0;
    }

    public double WasteRecycled() { return totalRecycledKg; }
    public void setTotalRecycledKg(double totalRecycledKg) { this.totalRecycledKg = totalRecycledKg; }

    @Override
    public void showDashboardMenu(Scanner sc, RecyclingManager mgr) {
        int choice = 0;
        do {
            System.out.println("\n--- Resident Menu (" + getUsername() + ") ---");
            System.out.println("1. Add Recycling Log");
            System.out.println("2. View My Logs");
            System.out.println("3. Logout");
            System.out.print("Choice: ");

            try {
                choice = sc.nextInt();
                sc.nextLine();

                if (choice == 1) {
                    mgr.logRecordPrompt(sc, getUsername());
                } else if (choice == 2) {
                    mgr.viewUserRecords(getUsername());
                } else if (choice == 3) {
                    System.out.println("Logging out...");
                } else {
                    System.out.println("Wrong choice, pick 1-3.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: enter a number.");
                sc.nextLine();
                choice = 0;
            }
        } while (choice != 3);
    }

    @Override
    public void generateReport() {
        System.out.println("--- Impact Report: " + getUsername() + " ---");
        System.out.println("Total Recycled: " + totalRecycledKg + " kg");
        System.out.println("CO2 Saved: " + (totalRecycledKg * 1.5) + " kg");
    }
}

class AdminUser extends User {
    public AdminUser(String username, String password) {
        super(username, password, "ADMIN");
    }

    @Override
    public void showDashboardMenu(Scanner sc, RecyclingManager mgr) {
        int opt = 0;
        do {
            System.out.println("\n--- Admin Panel ---");
            System.out.println("1. View All Logs");
            System.out.println("2. Delete Record");
            System.out.println("3. Community Summary");
            System.out.println("4. Logout");
            System.out.print("Select: ");

            try {
                opt = sc.nextInt();
                sc.nextLine();

                switch (opt) {
                    case 1: mgr.viewAllRecords(); break;
                    case 2: mgr.deleteRecordPrompt(sc); break;
                    case 3: mgr.generateReport(); break;
                    case 4: System.out.println("Bye admin."); break;
                    default: System.out.println("Invalid choice.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Numbers only please.");
                sc.nextLine();
                opt = 0;
            }
        } while (opt != 4);
    }

    @Override
    public void generateReport() {
        System.out.println("\n=== COMMUNITY RECYCLING SUMMARY ===");
    }
}

class RecyclingRecord {
    private int recordId;
    private String username;
    private String materialType;
    private double weightKg;
    private static int idCounter = 101;

    public RecyclingRecord(String username, String materialType, double weightKg) {
        this.recordId = idCounter++;
        this.username = username;
        this.materialType = materialType;
        this.weightKg = weightKg;
    }

    public int getRecordId() { return recordId; }
    public String getUsername() { return username; }
    public String getMaterialType() { return materialType; }
    public double getWeightKg() { return weightKg; }
}

class RecyclingManager implements Reportable {
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<RecyclingRecord> records = new ArrayList<>();

    public RecyclingManager() {
        users.add(new AdminUser("admin", "admin123"));
        users.add(new Resident("john", "password"));
    }

    public void registerUser(Scanner sc) {
        System.out.print("Username: ");
        String uname = sc.nextLine().trim();

        for(User u : users) {
            if(u.getUsername().equalsIgnoreCase(uname)) {
                System.out.println("Username already taken.");
                return;
            }
        }

        System.out.print("Password: ");
        String pwd = sc.nextLine().trim();

        users.add(new Resident(uname, pwd));
        System.out.println("Registered successfully!");
    }

    public User authenticate(Scanner sc) {
        System.out.print("Username: ");
        String uname = sc.nextLine().trim();
        System.out.print("Password: ");
        String pwd = sc.nextLine().trim();

        for (User u : users) {
            if (u.getUsername().equals(uname) && u.getPassword().equals(pwd)) {
                return u;
            }
        }
        System.out.println("Login failed.");
        return null;
    }

    public void logRecordPrompt(Scanner sc, String uname) {
        System.out.println("1. Plastic\n2. Paper\n3. Glass\n4. Metal");
        System.out.print("Material choice: ");
        int choice = sc.nextInt();
        sc.nextLine();

        String mat = "";
        if (choice == 1) mat = "Plastic";
        else if (choice == 2) mat = "Paper";
        else if (choice == 3) mat = "Glass";
        else if (choice == 4) mat = "Metal";
        else {
            System.out.println("Bad choice.");
            return;
        }

        System.out.print("Weight (kg): ");
        double wt = sc.nextDouble();
        sc.nextLine();

        if (wt <= 0) {
            System.out.println("Weight must be positive.");
            return;
        }

        records.add(new RecyclingRecord(uname, mat, wt));

        for (User u : users) {
            if (u.getUsername().equals(uname) && u instanceof Resident) {
                Resident r = (Resident) u;
                r.setTotalRecycledKg(r.WasteRecycled() + wt);
            }
        }
        System.out.println("Logged successfully!");
    }

    public void viewUserRecords(String uname) {
        double sum = 0;
        for (RecyclingRecord r : records) {
            if (r.getUsername().equals(uname)) {
                System.out.println("[" + r.getRecordId() + "] " + r.getMaterialType() + " - " + r.getWeightKg() + "kg");
                sum += r.getWeightKg();
            }
        }
        System.out.println("Total: " + sum + " kg");
    }

    public void viewAllRecords() {
        for (RecyclingRecord r : records) {
            System.out.println("ID:" + r.getRecordId() + " User:" + r.getUsername() + " Mat:" + r.getMaterialType() + " Wt:" + r.getWeightKg() + "kg");
        }
    }

    public void deleteRecordPrompt(Scanner sc) {
        System.out.print("Enter ID to delete: ");
        int id = sc.nextInt();
        sc.nextLine();

        boolean removed = records.removeIf(r -> r.getRecordId() == id);
        if (removed) System.out.println("Deleted.");
        else System.out.println("ID not found.");
    }

    @Override
    public void generateReport() {
        double total = 0;
        for (RecyclingRecord r : records) total += r.getWeightKg();
        System.out.println("Total Waste: " + total + " kg");
        System.out.println("Total Users: " + users.size());
    }
}

class MainApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        RecyclingManager mgr = new RecyclingManager();
        int opt = 0;

        do {
            System.out.println("\n1. Login\n2. Register\n3. Exit");
            System.out.print("Select: ");

            try {
                opt = sc.nextInt();
                sc.nextLine();

                if (opt == 1) {
                    User u = mgr.authenticate(sc);
                    if (u != null) {
                        u.generateReport();
                        u.showDashboardMenu(sc, mgr);
                    }
                } else if (opt == 2) {
                    mgr.registerUser(sc);
                } else if (opt == 3) {
                    System.out.println("Exiting...");
                }
            } catch (Exception e) {
                System.out.println("Invalid input.");
                sc.nextLine();
            }
        } while (opt != 3);

        sc.close();
    }
}
