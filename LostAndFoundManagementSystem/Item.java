
public class Item {

    private String itemID;
    private String itemName;
    private String description;
    private String category;
    private String dateReported;
    private String location;
    private String status;

    public Item(String itemID, String itemName, String description,
                String category, String dateReported,
                String location, String status) {

        this.itemID = itemID;
        this.itemName = itemName;
        this.description = description;
        this.category = category;
        this.dateReported = dateReported;
        this.location = location;
        this.status = status;
    }

    public String getItemID() {
        return itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getDateReported() {
        return dateReported;
    }

    public String getLocation() {
        return location;
    }

    public String getStatus() {
        return status;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
