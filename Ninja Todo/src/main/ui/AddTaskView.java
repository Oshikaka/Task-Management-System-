package ui;

import model.Event;
import model.EventLog;
import model.Task;
import model.TodoList;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;


public class AddTaskView extends JFrame implements ActionListener {
    JTextField itemTagField;
    JTextField itemDueDayField;
    JTextField itemEstTimeField;
    JTextField itemDescriptionField;
    JsonWriter jsonWriter;
    TaskView taskView;
    TodoList taskList;
    private final String finishAction = "FINISH_ACTION";
    private static final String JSON_STORE = "./data/todolist.json";

    //Add task window
    public AddTaskView(TaskView taskView, TodoList taskList) throws FileNotFoundException {
        super("Add a task");
        this.taskView = taskView;
        this.taskList = taskList;
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonWriter.open();
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setPreferredSize(new Dimension(500, 400));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(null);

        enterTag();
        enterDueDay();
        enterEstTime();
        enterDescription();
        finishButton();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    // MODIFIES: this
    // EFFECTS: constructs a Tag label and a tag JTextFiled object
    private void enterTag() {
        JLabel itemNameLabel = new JLabel("Please enter task's tag: ");
        itemNameLabel.setBounds(54, 40, 400, 20);
        add(itemNameLabel);
        itemNameLabel.setForeground(Color.darkGray);

        itemTagField = new JTextField(30);
        itemTagField.setBounds(50, 60, 400, 20);
        add(itemTagField);
    }

    // MODIFIES: this
    // EFFECTS: constructs a DueDay label and a DueDay JTextFiled object
    private void enterDueDay() {
        JLabel itemAgeLabel = new JLabel("Please enter task's due date (YYYY-MM-DD): ");
        itemAgeLabel.setBounds(54, 90, 400, 20);
        add(itemAgeLabel);
        itemAgeLabel.setForeground(Color.darkGray);

        itemDueDayField = new JTextField(30);
        itemDueDayField.setBounds(50, 110,400,20);
        add(itemDueDayField);
    }

    // MODIFIES: this
    // EFFECTS: constructs a EstTime label and a EstTime JTextFiled object
    private void enterEstTime() {
        JLabel itemHeightLabel = new JLabel("Please enter task's estimated completion time (in minutes): ");
        itemHeightLabel.setBounds(54, 140, 400, 20);
        add(itemHeightLabel);
        itemHeightLabel.setForeground(Color.darkGray);

        itemEstTimeField = new JTextField(30);
        itemEstTimeField.setBounds(50, 160,400,20);
        add(itemEstTimeField);
    }

    // MODIFIES: this
    // EFFECTS: constructs a Description label and a Description JTextFiled object
    private void enterDescription() {
        JLabel itemHeightLabel = new JLabel("Please enter task's Description: ");
        itemHeightLabel.setBounds(54, 190, 400, 20);
        add(itemHeightLabel);
        itemHeightLabel.setForeground(Color.darkGray);

        itemDescriptionField = new JTextField(30);
        itemDescriptionField.setBounds(50, 210,400,50);
        add(itemDescriptionField);
    }

    // MODIFIES: this
    // EFFECTS:  creates a new "Finish" button
    private void finishButton() {
        JButton finishButton = new JButton("Finish");
        finishButton.setBounds(54,290,100,20);
        add(finishButton);
        finishButton.setActionCommand(finishAction);
        finishButton.addActionListener(this);
        finishButton.setForeground(Color.darkGray);
    }

    // MODIFIES: this
    // EFFECTS:  after click finish button, add the new task that the user just input, save to the file
    @Override
    public void actionPerformed(ActionEvent ae) {
        String action = ae.getActionCommand();
        if (action.equals(finishAction)) {
            String tag = itemTagField.getText();
            String dueDay = itemDueDayField.getText();
            String estTimeString = itemEstTimeField.getText();
            String description = itemDescriptionField.getText();
            int estTime = Integer.parseInt(estTimeString);
            try {
                this.taskList.addTask(new Task(tag, dueDay, estTime, description));
                JOptionPane.showMessageDialog(null, "The task has been successfully added.");
            } catch (Exception e) {
                System.out.println("Wrong form of estimated completion time!");
                JOptionPane.showMessageDialog(null,
                        "Adding task fails, please enter an appropriate integer for estimated completion time.");
            }
            taskView.dispose();
            new TaskView(taskList);
            dispose();
        }
    }

}
