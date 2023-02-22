package ui;

import model.Task;
import model.TodoList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.ArrayList;
import java.util.List;

// Ninja Task list application
public class NinjaApp {
    boolean keepGoing = true;
    private static final String JSON_STORE = "./data/todolist.json";
    private TodoList taskList = new TodoList();
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the Ninja application
    public NinjaApp() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runNinja();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runNinja() {
        init();
        displayMenu();
        Scanner scanner = new Scanner(System.in);

        while (keepGoing) {
            int command = scanner.nextInt();
            switch (command) {
                case 1: doAddTask();
                    break;
                case 2: doRemoveTask();
                    break;
                case 3: select();
                    break;
                case 4: askToSave();
                    break;
                case 5: saveTaskList();
                    displayMenu();
                    break;
                case 6: loadTaskList();
                    break;
                default: System.out.println("Please enter a number from [1-6]: ");
            }
        }
        System.out.println("Bye~ Have a nice day!");
    }



    // MODIFIES: this
    // EFFECTS: initializes tasks
    private void init() {
        taskList.addTask(new Task("Math", "2021-08-08", 30,"Assignment 1"));
        taskList.addTask(new Task("Work", "2021-09-12", 60, "Editing resume"));
        taskList.addTask(new Task("Party", "2021-10-01", 300,"Homie's Birthday party"));
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("-------------- Ninja Todo | Main menu --------------");
        System.out.println("1. Add Tasks");
        System.out.println("2. Remove Tasks");
        System.out.println("3. Search Tasks");
        System.out.println("4. Quit");
        System.out.println("5. Save todo list to file");
        System.out.println("6. load todo list from file");
        System.out.println("Select a number from [1-6]");
    }


    // MODIFIES: this
    // EFFECTS: add tasks to task list
    private void doAddTask() {
        System.out.println("Ninja >> Add Tasks");
        Scanner inScanner = new Scanner(System.in);
        Task tasks = new Task("tag", "dueDay", 0, "description");
        System.out.println("Please enter tag:");
        tasks.setTag(inScanner.nextLine());
        System.out.println("Please enter due date (yyyy-MM-dd):");
        tasks.setDueDay(inScanner.nextLine());
        System.out.println("Please enter estimate completion time (in minutes):");
        tasks.setEstTime(inScanner.nextInt());
        inScanner.nextLine();
        System.out.println("Please enter description:");
        tasks.setDescription(inScanner.nextLine());

        taskList.addTask(tasks);
        System.out.println("Task was added successfully!");
        displayMenu();
    }


    // MODIFIES: this
    // EFFECTS: remove a certain task from task list
    private void doRemoveTask() {
        System.out.println("Ninja >> Remove Tasks");
        Scanner inScanner = new Scanner(System.in);
        int num = inScanner.nextInt();
        taskList.removeTask(num);
        System.out.println("Task was removed successfully!");
        displayMenu();
    }


    // MODIFIES: this
    // EFFECTS: ways to search tasks
    private void select() {
        System.out.println("Ninja >> Search Tasks");
        System.out.println("1. Review all tasks");
        System.out.println("2. Search by tag");
        System.out.println("3. Search by a due date period");
        System.out.println("Select a number from [1-3]");

        Scanner scanner = new Scanner(System.in);
        int option = scanner.nextInt();
        switch (option) {
            case 1:
                doReview();
                break;
            case 2:
                doSelectByTag();
                break;
            case 3:
                doSelectByDue();
                break;
            default: System.out.println("Please enter a number from [1-3]: ");
        }
        displayMenu();
    }


    // EFFECTS: get all tasks in tasks list
    private void doReview() {
        List<Task> tasks = taskList.review();
        print(tasks);
    }

    // MODIFIES: this
    // EFFECTS: search all tasks in tasks list by tag that users input
    private void doSelectByTag() {
        System.out.println("Ninja >> Search Tasks >> Search by tag");
        System.out.println("Please enter the tag that you want to search: ");
        Scanner scanner = new Scanner(System.in);
        String tag = scanner.next();

        List<Task> tasksWTag = taskList.tagFilter(tag);
        print(tasksWTag);
    }

    // MODIFIES: this
    // EFFECTS: search all tasks in tasks list by due date period that users input
    private void doSelectByDue() {
        System.out.println("Ninja >> Search Tasks >> Search by due date");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the start date: (yyyy-MM-dd)");
        String start = scanner.next();
        System.out.println("Please enter the end date: (yyyy-MM-dd)");
        String end = scanner.next();

        List<Task> tasksInDate = taskList.dateFilter(start, end);
        print(tasksInDate);
    }


    // EFFECTS: print all tasks in tasks list
    public void print(List<Task> tasks) {
        System.out.println("Ninja >> Search Tasks >> Review all tasks");
        System.out.println("Num\t\tTag\t\t\tDue date\t\tNinja Minutes\t\tDescription");

        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            System.out.println(i + 1 + task.toString());
        }
    }

    // MODIFIES: this
    // EFFECTS: ask whether the user want to save tasks to task list or not,
    //          if the user want, then save. If not, then quit.
    private void askToSave() {
        System.out.println("Ninja >> Quit");
        System.out.println("Do you want to save tasks on your todo list?");
        System.out.println("Click 1 to save");
        System.out.println("Click 0 to quit");

        Scanner scanner = new Scanner(System.in);
        int option = scanner.nextInt();
        switch (option) {
            case 1:
                saveTaskList();
                System.out.println("Saved!");
                keepGoing = false;
                break;
            case 0:
                keepGoing = false;
                break;
            default: System.out.println("Please enter a number from [1 or 0]: ");
        }
    }

    // EFFECTS: saves the taskList to file
    private void saveTaskList() {
        try {
            jsonWriter.open();
            jsonWriter.write(taskList);
            jsonWriter.close();
            System.out.println("Saved " + taskList.review() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads taskList from file
    private void loadTaskList() {
        try {
            taskList = jsonReader.read();
            System.out.println("Loaded " + taskList.review() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
        displayMenu();
    }

}
