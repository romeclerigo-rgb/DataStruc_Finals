
import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        FileCreator fileCreator = new FileCreator();
        fileCreator.createFiles();

        LostAndFound lostAndFound = new LostAndFound();
        ActivityHistory activityHistory = new ActivityHistory();
        Validation validation = new Validation();

        boolean loop = true;

        while (loop) {
            System.out.println("\n========================================");
            System.out.println("     CAMPUS LOST AND FOUND SYSTEM");
            System.out.println("========================================");
            System.out.println("1. Report an Item");
            System.out.println("2. Display All Items");
            System.out.println("3. Search an Item");
            System.out.println("4. Update Item Information");
            System.out.println("5. Claim an Item");
            System.out.println("6. Sort Item Records");
            System.out.println("7. View Recent Activity");
            System.out.println("8. Display Unclaimed Items");
            System.out.println("9. Exit");
            System.out.println("========================================");

            int choice = validation.getMenuChoice(sc, 1, 9);

            switch (choice) {
                case 1:
                    String itemID = validation.getNonEmptyInput(
                        sc, "Enter item ID: ");

                    if (lostAndFound.idExists(itemID)) {
                        System.out.println("Item ID already exists!");
                        break;
                    }

                    String itemName = validation.getNonEmptyInput(
                        sc, "Enter item name: ");

                    String description = validation.getNonEmptyInput(
                        sc, "Enter description: ");

                    String category = validation.getNonEmptyInput(
                        sc, "Enter category: ");

                    String location = validation.getNonEmptyInput(
                        sc, "Enter location: ");

                    String status = validation.getNonEmptyInput(
                        sc, "Enter status (Lost/Found): ");

                    if (!status.equalsIgnoreCase("Lost")
                            && !status.equalsIgnoreCase("Found")) {
                        System.out.println("Invalid status!");
                        break;
                    }

                    String dateReported = LocalDate.now().toString();

                    Item item = new Item(
                        itemID, itemName, description, category,
                        dateReported, location,
                        status.substring(0, 1).toUpperCase()
                            + status.substring(1).toLowerCase()
                    );

                    lostAndFound.addItem(item);
                    activityHistory.addActivity(
                        "Reported item: " + itemName + " (" + itemID + ")"
                    );
                    break;

                case 2:
                    lostAndFound.displayAllItems();
                    break;

                case 3:
                    String keyword = validation.getNonEmptyInput(
                        sc, "Enter item ID or name: ");

                    Item foundItem = lostAndFound.searchItem(keyword);

                    if (foundItem != null) {
                        lostAndFound.displayItem(foundItem);
                    } else {
                        System.out.println("Item not found!");
                    }
                    break;

                case 4:
                    String updateID = validation.getNonEmptyInput(
                        sc, "Enter item ID to update: ");

                    Item itemToUpdate = lostAndFound.searchItem(updateID);

                    if (itemToUpdate == null) {
                        System.out.println("Item not found!");
                        break;
                    }

                    String newDescription = validation.getNonEmptyInput(
                        sc, "Enter new description: ");

                    String newCategory = validation.getNonEmptyInput(
                        sc, "Enter new category: ");

                    String newLocation = validation.getNonEmptyInput(
                        sc, "Enter new location: ");

                    if (lostAndFound.updateItem(
                            updateID, newDescription,
                            newCategory, newLocation)) {

                        activityHistory.addActivity(
                            "Updated item: " + updateID
                        );
                        System.out.println("Item updated successfully!");
                    }
                    break;

                case 5:
                    String claimID = validation.getNonEmptyInput(
                        sc, "Enter item ID to claim: ");

                    if (lostAndFound.claimItem(claimID)) {
                        activityHistory.addActivity(
                            "Claimed item: " + claimID
                        );
                        System.out.println("Item claimed successfully!");
                    } else {
                        System.out.println(
                            "Item not found or is not available to claim!"
                        );
                    }
                    break;

                case 6:
                    lostAndFound.sortItemsByName();
                    lostAndFound.displayAllItems();
                    break;

                case 7:
                    activityHistory.displayActivities();
                    break;

                case 8:
                    lostAndFound.displayUnclaimedItems();
                    break;

                case 9:
                    loop = false;
                    System.out.println("Thank you for using the system!");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }
        }

        sc.close();
    }
}
