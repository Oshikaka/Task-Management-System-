package model;


import org.json.JSONObject;
import persistence.Writable;

// Represents an incident having a case number, description and open/closed status
public class Task implements Writable {
    private String tag;              // Tag of a task
    private String dueDay;           // Due date of a task
    private int estTime;             // Estimated completion time in minutes of a task
    private String description;      // Description of a task


    // EFFECTS: incident has given case number and description, and is not closed
    public Task(String tag, String dueDay, int estTime, String description) {
        this.tag = tag;
        this.dueDay = dueDay;
        this.estTime = estTime;
        this.description = description;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDueDay() {
        return this.dueDay;
    }

    public void setDueDay(String dueDay) {
        this.dueDay = dueDay;
    }

    public int getEstTime() {
        return this.estTime;
    }

    public void setEstTime(int estTime) {
        this.estTime = estTime;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // EFFECTS: A method to get the task data as formatted string to display in multiple lines
    public String toString() {
        return ("\t\t" + tag
                + "\t\t" + dueDay
                + "\t\t" + estTime
                + "\t\t\t\t\t" + description);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("tag", tag);
        json.put("dueDay", dueDay);
        json.put("estTime", estTime);
        json.put("description", description);
        return json;
    }
}
