
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class LostAndFound {

    private ArrayList<Item> items = new ArrayList<>();

    public LostAndFound() {
        loadItems();
    }

    private void loadItems() {
        items.clear();

        try (BufferedReader reader = new BufferedReader(
                new FileReader("ITEMS.txt"))) {

            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\|", -1);

                if (data.length == 7) {
                    Item item = new Item(
                        data[0], data[1], data[2], data[3],
                        data[4], data[5], data[6]
                    );

                    items.add(item);
                }
            }

        } catch (IOException e) {
            System.out.println("Error loading item records!");
        }
    }

    private void saveItems() {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter("ITEMS.txt"))) {

            for (Item item : items) {
                writer.write(
                    item.getItemID() + "|" +
                    item.getItemName() + "|" +
                    item.getDescription() + "|" +
                    item.getCategory() + "|" +
                    item.getDateReported() + "|" +
                    item.getLocation() + "|" +
                    item.getStatus()
                );

                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error saving item records!");
        }
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public boolean idExists(String itemID) {
        for (Item item : items) {
            if (item.getItemID().equalsIgnoreCase(itemID)) {
                return true;
            }
        }

        return false;
    }

    public void addItem(Item item) {
        if (idExists(item.getItemID())) {
            System.out.println("Item ID already exists!");
            return;
        }

        items.add(item);
        saveItems();

        System.out.println("Item added successfully!");
    }

    public Item searchItem(String keyword) {
        for (Item item : items) {
            if (item.getItemID().equalsIgnoreCase(keyword)
                    || item.getItemName().equalsIgnoreCase(keyword)) {
                return item;
            }
        }

        return null;
    }

    public void displayAllItems() {
        if (items.isEmpty()) {
            System.out.println("No items yet.");
            return;
        }

        for (Item item : items) {
            displayItem(item);
        }
    }

    public void displayItem(Item item) {
        System.out.println("\nItem ID: " + item.getItemID());
        System.out.println("Item Name: " + item.getItemName());
        System.out.println("Description: " + item.getDescription());
        System.out.println("Category: " + item.getCategory());
        System.out.println("Date Reported: " + item.getDateReported());
        System.out.println("Location: " + item.getLocation());
        System.out.println("Status: " + item.getStatus());
    }

    public boolean updateItem(String itemID, String description,
                              String category, String location) {

        Item item = searchItem(itemID);

        if (item == null) {
            return false;
        }

        item.setDescription(description);
        item.setCategory(category);
        item.setLocation(location);

        saveItems();
        return true;
    }

    public boolean claimItem(String itemID) {
        Item item = searchItem(itemID);

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

    public void sortItemsByName() {
        for (int i = 1; i < items.size(); i++) {
            Item current = items.get(i);
            int j = i - 1;

            while (j >= 0 && items.get(j).getItemName()
                    .compareToIgnoreCase(current.getItemName()) > 0) {

                items.set(j + 1, items.get(j));
                j--;
            }

            items.set(j + 1, current);
        }

        System.out.println("Items sorted by name.");
    }

    public void displayUnclaimedItems() {
        boolean found = false;

        for (Item item : items) {
            if (item.getStatus().equalsIgnoreCase("Found")) {
                displayItem(item);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No unclaimed items yet.");
        }
    }
}
