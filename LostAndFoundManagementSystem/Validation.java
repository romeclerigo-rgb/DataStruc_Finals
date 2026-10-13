
import java.util.Scanner;

public class Validation {

    public int getMenuChoice(Scanner sc) {
        boolean valid = false;
        int choice = 0;

        while (!valid) {
            System.out.print("Enter your choice: ");
            String input = sc.nextLine();

            if (input.matches("[1-9]")) {
                choice = Integer.parseInt(input);
                valid = true;
            } else {
                System.out.println(
                    "Invalid input! Please enter a number from 1 to 9 only."
                );
            }
        }

        return choice;
    }

    public String getItemID(Scanner sc, LostAndFound lostAndFound) {
        boolean valid = false;
        String id = "";

        while (!valid) {
            System.out.print("Enter item ID: ");
            String input = sc.nextLine();

            if (input.isEmpty()) {
                System.out.println("ID cannot be empty!");

            } else if (input.length() > 25) {
                System.out.println(
                    "Invalid ID! Maximum length is 25 characters."
                );

            } else if (!input.matches(
                    "(?=.*[A-Za-z])(?=.*\\d)[A-Za-z0-9]+(?:-[A-Za-z0-9]+)*")) {
                System.out.println(
                    "Invalid ID! ID must contain at least one letter and " +
                    "one number. Dashes are allowed only between characters."
                );

            } else if (lostAndFound.idExists(input)) {
                System.out.println(
                    "ID already exists! Please enter a different ID."
                );

            } else {
                id = input;
                valid = true;
            }
        }

        return id;
    }

    public String getItemName(Scanner sc) {
        boolean valid = false;
        String name = "";

        while (!valid) {
            System.out.print("Enter item name: ");
            String input = sc.nextLine();

            if (input.trim().isEmpty()) {
                System.out.println("Name cannot be empty!");

            } else if (input.length() > 25) {
                System.out.println(
                    "Invalid name! Maximum length is 25 characters."
                );

            } else if (input.contains("|")) {
                System.out.println(
                    "Invalid name! The character | is not allowed."
                );

            } else {
                name = input;
                valid = true;
            }
        }

        return name;
    }

    public String getDescription(Scanner sc) {
        boolean valid = false;
        String description = "";

        while (!valid) {
            System.out.print("Enter description (maximum 30 words): ");
            String input = sc.nextLine();

            if (input.trim().isEmpty()) {
                System.out.println("Description cannot be empty!");

            } else if (input.contains("|")) {
                System.out.println(
                    "Invalid description! The character | is not allowed."
                );

            } else {
                String[] words = input.trim().split("\\s+");

                if (words.length > 30) {
                    System.out.println(
                        "Invalid description! Maximum is 30 words."
                    );
                } else {
                    description = input;
                    valid = true;
                }
            }
        }

        return description;
    }

    public String getStatus(Scanner sc) {
        boolean valid = false;
        String status = "";

        while (!valid) {
            System.out.print("Enter status (Lost/Found): ");
            String input = sc.nextLine();

            if (input.equalsIgnoreCase("Lost")) {
                status = "Lost";
                valid = true;

            } else if (input.equalsIgnoreCase("Found")) {
                status = "Found";
                valid = true;

            } else {
                System.out.println(
                    "Invalid status! Please enter Lost or Found only."
                );
            }
        }

        return status;
    }

    public String getNonEmptyInput(Scanner sc, String prompt) {
        boolean valid = false;
        String input = "";

        while (!valid) {
            System.out.print(prompt);
            input = sc.nextLine();

            if (input.trim().isEmpty()) {
                System.out.println("Input cannot be empty!");

            } else if (input.contains("|")) {
                System.out.println(
                    "Invalid input! The character | is not allowed."
                );

            } else {
                valid = true;
            }
        }

        return input;
    }
}
