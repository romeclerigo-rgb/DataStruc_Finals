
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.Random;

public class LostAndFound {

    private ArrayList<Item> items = new ArrayList<>();

    public LostAndFound() {
        loadItems();
    }

    // Load item records from the file
    private void loadItems() {
        items.clear();

        try (BufferedReader reader = new BufferedReader(
                new FileReader("ITEMS.txt"))) {

            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\|", -1);

                if (data.length == 5) {
                    Item item = new Item(
                        data[0], data[1], data[2],
                        data[3], data[4]
                    );

                    items.add(item);
                }
            }

        } catch (IOException e) {
            System.out.println("Error loading item records!");
        }
    }

    // Save item records to the file
    private void saveItems() {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter("ITEMS.txt"))) {

            for (Item item : items) {
                writer.write(
                    item.getItemID() + "|" +
                    item.getItemName() + "|" +
                    item.getDescription() + "|" +
                    item.getDateReported() + "|" +
                    item.getStatus()
                );

                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error saving item records!");
        }
    }

    // Check whether there are no item records
    public boolean isEmpty() {
        return items.isEmpty();
    }

    // Check whether an ID already exists
    public boolean idExists(String itemID) {
        for (Item item : items) {
            if (item.getItemID().equalsIgnoreCase(itemID)) {
                return true;
            }
        }

        return false;
    }

    // Add a new item
    public void addItem(Item item) {
        if (idExists(item.getItemID())) {
            System.out.println("Item ID already exists!");
            return;
        }

        items.add(item);
        saveItems();

        System.out.println("Item added successfully!");
    }

    // Linear Search: find by exact ID or keyword
    public Item searchItem(String keyword) {
        for (Item item : items) {
            if (item.getItemID().equalsIgnoreCase(keyword)
                    || item.getItemName().toLowerCase()
                        .contains(keyword.toLowerCase())
                    || item.getDescription().toLowerCase()
                        .contains(keyword.toLowerCase())) {

                return item;
            }
        }

        return null;
    }

    // Display all records in a table
    public void displayAllItems() {
        if (items.isEmpty()) {
            System.out.println("No items yet.");
            return;
        }

        printTableHeader();

        for (Item item : items) {
            printTableRow(item);
        }

        printTableBorder();
    }

    // Display the table header
    private void printTableHeader() {
        printTableBorder();

        System.out.printf(
            "| %-12s | %-20s | %-35s | %-12s | %-10s |%n",
            "Item ID", "Item Name", "Description",
            "Date Reported", "Status"
        );

        printTableBorder();
    }

    // Display one item as a table row
    private void printTableRow(Item item) {
        System.out.printf(
            "| %-12s | %-20s | %-35s | %-12s | %-10s |%n",
            formatCell(item.getItemID(), 12),
            formatCell(item.getItemName(), 20),
            formatCell(item.getDescription(), 35),
            formatCell(item.getDateReported(), 12),
            formatCell(item.getStatus(), 10)
        );
    }

    // Display the table border
    private void printTableBorder() {
        System.out.println(
            "+--------------+----------------------+"
            + "-------------------------------------+"
            + "--------------+------------+"
        );
    }

    // Keep table columns aligned when a value is too long
    private String formatCell(String value, int maxLength) {
        if (value.length() > maxLength) {
            return value.substring(0, maxLength - 3) + "...";
        }

        return value;
    }

    // Display one item's complete details
    public void displayItem(Item item) {
        if (item == null) {
            System.out.println("Item not found!");
            return;
        }

        System.out.println("\n========================================");
        System.out.println("             ITEM DETAILS");
        System.out.println("========================================");
        System.out.println("Item ID:       " + item.getItemID());
        System.out.println("Item Name:     " + item.getItemName());
        System.out.println("Description:   " + item.getDescription());
        System.out.println("Date Reported: " + item.getDateReported());
        System.out.println("Status:        " + item.getStatus());
        System.out.println("========================================");
    }

    // Edit an item's name and description
    public boolean updateItem(String itemID, String itemName,
                              String description) {
        Item item = findByID(itemID);

        if (item == null) {
            return false;
        }

        item.setItemName(itemName);
        item.setDescription(description);

        saveItems();
        return true;
    }

    // Delete an item record
    public boolean removeItem(String itemID) {
        Item item = findByID(itemID);

        if (item == null) {
            return false;
        }

        items.remove(item);
        saveItems();

        return true;
    }

    // Find a record using its exact ID
    private Item findByID(String itemID) {
        for (Item item : items) {
            if (item.getItemID().equalsIgnoreCase(itemID)) {
                return item;
            }
        }

        return null;
    }

    // Mark a found item as claimed
    public boolean claimItem(String itemID) {
        Item item = findByID(itemID);

        if (item == null) {
            return false;
        }

        if (!item.getStatus().equalsIgnoreCase("Found")) {
            return false;
        }

        item.setStatus("Claimed");
        saveItems();

        return true;
    }

    // Insertion Sort: oldest report date to newest
    // Dates must use the yyyy-MM-dd format
    public void sortItemsByDate() {
        if (items.isEmpty()) {
            System.out.println("No items to sort.");
            return;
        }

        for (int i = 1; i < items.size(); i++) {
            Item current = items.get(i);
            int j = i - 1;

            while (j >= 0 && items.get(j).getDateReported()
                    .compareTo(current.getDateReported()) > 0) {

                items.set(j + 1, items.get(j));
                j--;
            }

            items.set(j + 1, current);
        }

        saveItems();

        System.out.println("Items sorted by date successfully!");
        displayAllItems();
    }

    // Display all items that have been found but not claimed
    public void displayUnclaimedItems() {
        boolean found = false;

        for (Item item : items) {
            if (item.getStatus().equalsIgnoreCase("Found")) {
                if (!found) {
                    printTableHeader();
                }

                printTableRow(item);
                found = true;
            }
        }

        if (found) {
            printTableBorder();
        } else {
            System.out.println("No unclaimed items yet.");
        }
    }

    // Generate a unique ID using the current year and four random digits
    public String generateID() {
        Random random = new Random();
        String itemID;
        boolean exists = true;

        while (exists) {
            int number = random.nextInt(10000);

            itemID = LocalDate.now().getYear()
                    + String.format("%04d", number);

            exists = idExists(itemID);

            if (!exists) {
                return itemID;
            }
        }

        return "";
    }
}
