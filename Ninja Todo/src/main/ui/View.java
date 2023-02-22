package ui;

import model.Event;
import model.EventLog;
import model.TodoList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;


public class View extends JFrame implements ActionListener {
    private final int buttonPosition = 100;
    private final int buttonWidth = 200;
    private final int buttonHeight = 20;
    private final String viewTodoAction = "VIEW_TODOLIST_ACTION";
    private final String clearTodoAction = "CLEAR_TODOLIST_ACTION";
    private final String quitAppAction = "QUIT_APP_ACTION";
    private TodoList taskList;
    private TaskView taskView;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private static final String JSON_STORE = "./data/todolist.json";


    // Main window
    public View() {
        super("Ninja Todo Application");
        taskList = new TodoList();
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
        setPreferredSize(new Dimension(400, 300));
        background();
        startLabel();
        viewButton();
        clearButton();
        quitButton();
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    // MODIFIES: this
    // EFFECTS:  set background image for main window
    private void background() {
        try {
            System.out.println("Working Directory = " + System.getProperty("user.dir"));
            BufferedImage backgroundImage = ImageIO.read(new File("./src/main/ui/background/NinjaLogo.jpg"));
            BackgroundImage backgroundImage1 = new BackgroundImage(backgroundImage);
            setContentPane(backgroundImage1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // MODIFIES: this
    // EFFECTS:  load the task list file
    private void load() {
        try {
            taskList = jsonReader.read();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // MODIFIES: this
    // EFFECTS:  add a start label to main window
    private void startLabel() {
        JLabel selectTaskLabel = new JLabel("Be a Ninja", JLabel.CENTER);
        selectTaskLabel.setBounds(buttonPosition, 30, buttonWidth, buttonHeight);
        add(selectTaskLabel);
        selectTaskLabel.setForeground(Color.black);
    }

    // MODIFIES: this
    // EFFECTS:  add a view button to main window
    private void viewButton() {
        JButton viewButton = new JButton("View my todo-list");
        viewButton.setBounds(buttonPosition, 60, buttonWidth, buttonHeight);
        add(viewButton);
        viewButton.setActionCommand(viewTodoAction);
        viewButton.addActionListener(this);
        viewButton.setForeground(Color.black);
    }

    // MODIFIES: this
    // EFFECTS:  add a clear button to main window
    private void clearButton() {
        JButton clearTodoButton = new JButton("Clear my todo-list");
        clearTodoButton.setBounds(buttonPosition, 100, buttonWidth, buttonHeight);
        add(clearTodoButton);
        clearTodoButton.setActionCommand(clearTodoAction);
        clearTodoButton.addActionListener(this);
        clearTodoButton.setForeground(Color.black);
    }

    // MODIFIES: this
    // EFFECTS:  add a quit button to main window
    private void quitButton() {
        JButton quitButton = new JButton("Save my todo-list and Quit");
        quitButton.setBounds(buttonPosition, 200, buttonWidth, buttonHeight);
        add(quitButton);
        quitButton.setActionCommand(quitAppAction);
        quitButton.addActionListener(this);
        quitButton.setForeground(Color.darkGray);
    }

    // MODIFIES: this
    // EFFECTS: clear taskList from file
    public void clearFile() {
        try {
            PrintWriter writer = new PrintWriter(JSON_STORE,"UTF-8");
            writer.print("");
            writer.close();
        } catch (IOException e) {
            System.out.println("Encountered IOException while saving todo list.");
        }
    }
    
    // MODIFIES: this
    // EFFECTS: make taskList empty
    public void emptyTodoList() {
        taskList = new TodoList();
        clearFile();
    }



    // MODIFIES: this
    // EFFECTS:  click viewButton to view task list,
    //           click clearButton to clear task list,
    //           click quitButton to save all changes and quit
    //           when the user quits your application,
    //           print to the console all the events that have been logged since the application started.
    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (action.equals(viewTodoAction)) {
            load();
            taskView = new TaskView(taskList);
        } else if (action.equals(clearTodoAction)) {
            emptyTodoList();
            taskView.dispose();
            taskView = new TaskView(taskList);
            JOptionPane.showMessageDialog(null, "Your todo-list has been cleared!");
        } else if (action.equals(quitAppAction)) {
            try {
                jsonWriter.open();
                jsonWriter.write(taskList);
                jsonWriter.close();
                log();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            if (taskView != null) {
                taskView.dispose();
            }
            dispose();
        }
    }

    // MODIFIES: this
    // EFFECTS:  print to the console all the events that have been logged since the application started.
    private void log() {
        for (Event element : EventLog.getInstance()) {
            System.out.println(element);
        }
    }
}


