package Group_Assignment;

import java.util.ArrayList;
import java.util.Scanner;

public class NormalUser extends User {
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
    public void displayDashboard(Scanner scanner, ArrayList<Event> events, ArrayList<User> users) {
        boolean sessionActive = true;
        while (sessionActive) {
            System.out.println("\n=== Member Dashboard: " + getUsername() + " (" + memberType + " Member) ===");
            System.out.println("1. View Events");
            System.out.println("2. Register for Event");
            System.out.println("3. View My Profile");
            System.out.println("4. Logout");
            System.out.print("Choose member option: ");

            int choice = Main.readValidatedInt(scanner, 1, 4);
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