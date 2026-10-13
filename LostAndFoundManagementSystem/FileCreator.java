
import java.io.File;
import java.io.IOException;

public class FileCreator {

    public void createFiles() {
        File itemsFile = new File("ITEMS.txt");
        File activityFile = new File("ACTIVITY.txt");

        try {
            if (itemsFile.createNewFile()) {
                System.out.println("ITEMS.txt created successfully!");
            }

            if (activityFile.createNewFile()) {
                System.out.println("ACTIVITY.txt created successfully!");
            }

        } catch (IOException e) {
            System.out.println("An error occurred while creating files.");
        }
    }
}
