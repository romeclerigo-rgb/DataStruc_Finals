
import java.util.Scanner;

public class Validation {

    public String getNonEmptyInput(Scanner sc, String prompt) {
        String input;
        boolean valid = false;

        do {
            System.out.print(prompt);
            input = sc.nextLine();

            if (!input.isEmpty()) {
                valid = true;
            } else {
                System.out.println("Input cannot be empty!");
            }

        } while (!valid);

        return input;
    }

    public int getMenuChoice(Scanner sc, int min, int max) {
        int choice = 0;
        boolean valid = false;

        do {
            System.out.print("Enter your choice: ");
            String input = sc.nextLine();

            try {
                choice = Integer.parseInt(input);

                if (choice >= min && choice <= max) {
                    valid = true;
                } else {
                    System.out.println("Invalid choice!");
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            }

        } while (!valid);

        return choice;
    }
}
