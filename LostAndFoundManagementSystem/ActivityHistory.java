
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

public class ActivityHistory {

    private Stack<String> activities = new Stack<>();

    public void addActivity(String activity) {
        activities.push(activity);
        saveActivity(activity);
    }

    public String viewLatestActivity() {
        if (activities.isEmpty()) {
            return "No activities yet.";
        }

        return activities.peek();
    }

    public String removeLatestActivity() {
        if (activities.isEmpty()) {
            return "No activities to remove.";
        }

        return activities.pop();
    }

    public void displayActivities() {
        if (activities.isEmpty()) {
            System.out.println("No activities yet.");
            return;
        }

        System.out.println("\n===== RECENT ACTIVITIES =====");

        for (int i = activities.size() - 1; i >= 0; i--) {
            System.out.println(activities.get(i));
        }
    }

    private void saveActivity(String activity) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter("ACTIVITY.txt", true))) {

            writer.write(activity);
            writer.newLine();

        } catch (IOException e) {
            System.out.println("Error saving activity!");
        }
    }
}
