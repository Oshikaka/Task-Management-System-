package persistence;

import model.Event;
import model.EventLog;
import model.Task;
import model.TodoList;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

/*
*    Title: JsonSerializationDemo
*    Author: Paul C.
*    Date: 2021
*    Code version: 20210307
*    Availability: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
*/

// Represents a reader that reads taskList from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads taskList from file and returns it;
    // throws IOException if an error occurs reading data from file
    public TodoList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseTodoList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses taskList from JSON object and returns it
    private TodoList parseTodoList(JSONObject jsonObject) {
        JSONArray taskList = jsonObject.getJSONArray("taskList");
        TodoList tl = new TodoList();
        addTasks(tl, jsonObject);
        return tl;
    }

    // MODIFIES: tl
    // EFFECTS: parses tasks from JSON object and adds them to taskList
    private void addTasks(TodoList tl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("taskList");
        for (Object json : jsonArray) {
            JSONObject nextTask = (JSONObject) json;
            addTask(tl, nextTask);
        }
    }

    // MODIFIES: tl
    // EFFECTS: parses elements of task from JSON object and add them to task
    private void addTask(TodoList tl, JSONObject jsonObject) {
        String tag = jsonObject.getString("tag");
        String dueDay = jsonObject.getString("dueDay");
        int estTime = jsonObject.getInt("estTime");
        String description = jsonObject.getString("description");
        Task task = new Task(tag, dueDay, estTime, description);
        tl.addTask(task);
    }
}
