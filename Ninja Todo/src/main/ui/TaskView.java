package ui;

import model.Task;
import model.TodoList;
import persistence.JsonWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TaskView extends JFrame implements ActionListener {
    private DefaultTableModel tableModel;
    private JTable table;
    private TodoList taskList;
    private final String addTaskAction = "ADD_TASK_ACTION";
    private final String removeTaskAction = "REMOVE_TASK_ACTION";
    //private final String returnAction = "RETURN_ACTION";
    private final int buttonWidth = 350;
    private final int buttonHeight = 25;

    // Task window
    public TaskView(TodoList taskList) {
        super("My todo-list");
        this.taskList = taskList;
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setPreferredSize(new Dimension(600, 500));
        setLayout(null);
        this.setLocationRelativeTo(null);
        background();
        final String[] columnLabels = new String[]{
                "Index", "Tag", "Due date", "Estimated completion time", "Description"};

        table(taskList, columnLabels);
        buttons();
        setTitle("My Todo-list");
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        pack();
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

    private void buttons() {
        addTaskButton();
        removeTaskButton();
    }

    // MODIFIES: this
    // EFFECTS:  create an add task button and add to JPanel
    private void addTaskButton() {
        JButton addTaskButton = new JButton(("Add a new task"));
        addTaskButton.setBounds(500, 90, buttonWidth, buttonHeight);
        add(addTaskButton);
        addTaskButton.setActionCommand(addTaskAction);
        addTaskButton.addActionListener(this);
        addTaskButton.setForeground(Color.darkGray);
    }

    // MODIFIES: this
    // EFFECTS:  create a remove task button and add to JPanel
    private void removeTaskButton() {
        JButton removeTheSelectedTaskButton = new JButton("Remove the selected task");
        removeTheSelectedTaskButton.setBounds(14, 30, buttonWidth, buttonHeight);
        add(removeTheSelectedTaskButton);
        removeTheSelectedTaskButton.setActionCommand(removeTaskAction);
        removeTheSelectedTaskButton.addActionListener(this);
        removeTheSelectedTaskButton.setForeground(Color.darkGray);
    }



    // MODIFIES: this
    // EFFECTS:  create a task list table and add to JPanel
    private void table(TodoList taskList, String[] columnLabels) {
        tableModel = new DefaultTableModel(null, columnLabels) {
        };
        table = new JTable(tableModel);
        for (int i = 0; i < taskList.getTasks().size(); i++) {
            Task task = taskList.getTasks().get(i);
            Object[] tableRow = new Object[]{
                    i + 1,                  // index column
                    task.getTag(),          // tag column
                    task.getDueDay(),       // Due date column
                    task.getEstTime(),      // Estimated completion time column
                    task.getDescription()   // Description column
            };
            tableModel.addRow(tableRow);
        }

        JScrollPane scrollPane = new JScrollPane(table, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane);
    }



    // MODIFIES: this
    // EFFECTS:  click addTaskButton then do add task action,
    //           click removeTaskButton then do remove the selected task action,
    //           save all changes
    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (action.equals(addTaskAction)) {
            try {
                new AddTaskView(this, taskList);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        } else if (action.equals(removeTaskAction)) {
            int selectedRowIndex = table.getSelectedRow();
            if (selectedRowIndex == -1) {
                JOptionPane.showMessageDialog(null, "Please select a task to remove.");
                return;
            }
            taskList.removeTask(selectedRowIndex - 1);
            JOptionPane.showMessageDialog(null, "This task has been removed.");
            new TaskView(taskList);
            dispose();
        }
    }

}
