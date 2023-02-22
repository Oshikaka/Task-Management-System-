package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

// represent functions can do to todolist
public class TodoList implements Writable {
    private ArrayList<Task> taskList;

    public TodoList() {
        taskList = new ArrayList<>();
    }


    // MODIFIES: this
    // EFFECTS: add tasks to task list
    public void addTask(Task task) {
        this.taskList.add(task);
        EventLog.getInstance().logEvent(new Event("Task was added to todo list."));
    }

    // MODIFIES: this
    // EFFECTS: remove tasks to task list
    public void removeTask(int n) {
        this.taskList.remove(n + 1);
        EventLog.getInstance().logEvent(new Event("Task was removed from todo list."));
    }

    // MODIFIES: this
    // EFFECTS: search all tasks in tasks list by tag
    public List<Task> tagFilter(String tag) {
        return taskList.stream().filter(tasks -> tasks.getTag().equals(tag)).collect(Collectors.toList());
    }

    // MODIFIES: this
    // EFFECTS: search all tasks in tasks list by due date period
    public List<Task> dateFilter(String dueDateStart, String dueDateEnd) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return taskList.stream().filter(task -> {
            String date = task.getDueDay();
            try {
                Date startDate = format.parse(dueDateStart);
                Date endDate = format.parse(dueDateEnd);
                Date timeDate = format.parse(date);
                if ((timeDate.before(endDate) && timeDate.after(startDate))
                        || (timeDate.equals(endDate) && timeDate.equals(startDate))) {
                    return true;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return false;
        }).collect(Collectors.toList());
    }

    public List<Task> review() {
        return taskList;
    }

    // EFFECTS: returns number of task currently in task list
    public int length() {
        return taskList.size(); // stub
    }

    // EFFECTS: returns task in task list
    public Task getTask(int n) {
        return taskList.get(n); // stub
    }

//    // EFFECTS: returns task list
//    public List<Task> getTaskList() {
//        return taskList;
//    }
    // EFFECTS: returns task list
    public ArrayList<Task> getTasks() {
        return taskList;
    }



    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("taskList", taskListToJson());
        return json;
    }

    // EFFECTS: returns things in this task list as a JSON array
    private JSONArray taskListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Task t : taskList) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }

}
