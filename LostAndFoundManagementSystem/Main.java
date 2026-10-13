
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
            System.out.println("       CAMPUS LOST AND FOUND SYSTEM");
            System.out.println("========================================");
            System.out.println("1. Report an Item");
            System.out.println("2. Display All Items");
            System.out.println("3. Search an Item");
            System.out.println("4. Edit Item Record");
            System.out.println("5. Claim an Item");
            System.out.println("6. Sort Records by Date");
            System.out.println("7. View Status Change History");
            System.out.println("8. Display Unclaimed Items");
            System.out.println("9. Exit");
            System.out.println("========================================");

            int choice = validation.getMenuChoice(sc);

            switch (choice) {
                case 1:
                    String itemID = validation.getItemID(sc, lostAndFound);

                    String itemName = validation.getItemName(sc);

                    String description = validation.getDescription(sc);

                    String status = validation.getStatus(sc);

                    String dateReported = LocalDate.now().toString();

                    Item item = new Item(
                        itemID,
                        itemName,
                        description,
                        dateReported,
                        status
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
                        sc, "Enter item ID or keyword: "
                    );

                    Item foundItem = lostAndFound.searchItem(keyword);

                    if (foundItem != null) {
                        lostAndFound.displayItem(foundItem);
                    } else {
                        System.out.println("Item not found!");
                    }
                    break;

                case 4:
                    if (lostAndFound.isEmpty()) {
                        System.out.println("No items yet.");
                        break;
                    }

                    System.out.println("\n========================================");
                    System.out.println("             EDIT ITEM RECORD");
                    System.out.println("========================================");
                    System.out.println("1. Edit Item Information");
                    System.out.println("2. Delete Item Record");
                    System.out.println("3. Back to Main Menu");
                    System.out.println("========================================");

                    int editChoice = getEditChoice(sc);

                    switch (editChoice) {
                        case 1:
                            String updateID = validation.getNonEmptyInput(
                                sc, "Enter item ID to edit: "
                            );

                            Item itemToUpdate = lostAndFound.searchItem(
                                updateID
                            );

                            if (itemToUpdate == null
                                    || !itemToUpdate.getItemID()
                                        .equalsIgnoreCase(updateID)) {
                                System.out.println("Item ID not found!");
                                break;
                            }

                            lostAndFound.displayItem(itemToUpdate);

                            String newName = validation.getItemName(sc);
                            String newDescription =
                                validation.getDescription(sc);

                            if (lostAndFound.updateItem(
                                    updateID, newName, newDescription)) {

                                System.out.println(
                                    "Item information updated successfully!"
                                );
                            }
                            break;

                        case 2:
                            String deleteID = validation.getNonEmptyInput(
                                sc, "Enter item ID to delete: "
                            );

                            Item itemToDelete = lostAndFound.searchItem(
                                deleteID
                            );

                            if (itemToDelete == null
                                    || !itemToDelete.getItemID()
                                        .equalsIgnoreCase(deleteID)) {
                                System.out.println("Item ID not found!");
                                break;
                            }

                            lostAndFound.displayItem(itemToDelete);

                            System.out.print(
                                "Are you sure you want to delete this item? (Y/N): "
                            );

                            String confirmation = sc.nextLine();

                            if (confirmation.equalsIgnoreCase("Y")) {
                                if (lostAndFound.removeItem(deleteID)) {
                                    activityHistory.addActivity(
                                        "Deleted item: " + deleteID
                                    );

                                    System.out.println(
                                        "Item deleted successfully!"
                                    );
                                }
                            } else if (confirmation.equalsIgnoreCase("N")) {
                                System.out.println(
                                    "Deletion cancelled."
                                );
                            } else {
                                System.out.println(
                                    "Invalid confirmation. Deletion cancelled."
                                );
                            }
                            break;

                        case 3:
                            System.out.println("Returning to main menu.");
                            break;
                    }
                    break;

                case 5:
                    String claimID = validation.getNonEmptyInput(
                        sc, "Enter item ID to claim: "
                    );

                    Item itemToClaim = lostAndFound.searchItem(claimID);

                    if (itemToClaim == null
                            || !itemToClaim.getItemID()
                                .equalsIgnoreCase(claimID)) {
                        System.out.println("Item ID not found!");
                        break;
                    }

                    if (lostAndFound.claimItem(claimID)) {
                        activityHistory.addActivity(
                            "Status changed: " + claimID
                            + " (Found -> Claimed)"
                        );

                        System.out.println("Item claimed successfully!");
                    } else {
                        System.out.println(
                            "This item cannot be claimed. Only Found items can be claimed."
                        );
                    }
                    break;

                case 6:
                    lostAndFound.sortItemsByDate();
                    break;

                case 7:
                    activityHistory.displayActivities();
                    break;

                case 8:
                    lostAndFound.displayUnclaimedItems();
                    break;

                case 9:
                    loop = false;
                    System.out.println(
                        "Thank you for using the system!"
                    );
                    break;

                default:
                    System.out.println("Invalid choice!");
            }
        }

        sc.close();
    }

    private static int getEditChoice(Scanner sc) {
        boolean valid = false;
        int choice = 0;

        while (!valid) {
            System.out.print("Enter your choice: ");
            String input = sc.nextLine();

            if (input.matches("[1-3]")) {
                choice = Integer.parseInt(input);
                valid = true;
            } else {
                System.out.println(
                    "Invalid input! Please enter 1, 2, or 3."
                );
            }
        }

        return choice;
    }
}
