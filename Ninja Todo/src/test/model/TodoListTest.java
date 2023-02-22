package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TodoListTest {
    private TodoList taskList;
    private Task task;
    private Task t1;
    private Task t2;
    private Task t3;

    @BeforeEach
    void runBefore() {
        taskList = new TodoList();
        task = new Task("Party", "2021-10-01", 300, "Homie's Birthday party");
        t1 = new Task("Math", "2021-08-08", 30, "Assignment 1");
        t2 = new Task("Work", "2021-09-12", 60, "Editing resume");
        t3 = new Task("Party", "2021-10-01", 300, "Homie's Birthday party");
    }

    @Test
    void testDefaultConstructor() {
        task = new Task("tag", "dueDay", 0, "description");

        assertEquals("tag",task.getTag());
        assertEquals("dueDay",task.getDueDay());
        assertEquals("description",task.getDescription());
        assertEquals(0, task.getEstTime());
    }

    @Test
    void testAddTasks() {
        taskList.addTask(t1);
        assertEquals(1, taskList.length());
        assertEquals(t1, taskList.getTask(0));

        taskList.addTask(t2);
        assertEquals(2, taskList.length());
        assertEquals(t2, taskList.getTask(1));

        task.setTag("English");
        task.setDueDay("2021-09-09");
        task.setEstTime(50);
        task.setDescription("Creative Writing");
        taskList.addTask(task);
        assertEquals(3, taskList.length());
        assertEquals(task, taskList.getTask(2));
    }

    @Test
     void testRemoveTasks() {
        taskList.addTask(t1);
        taskList.addTask(t2);
        taskList.addTask(t3);
        taskList.removeTask(0);

        assertEquals(2, taskList.length());
        assertFalse(taskList.review().contains(t2));
        assertEquals(t1, taskList.getTask(0));
        assertEquals(t3, taskList.getTask(1));
    }

    @Test
    void testTagFilter() {
        taskList.addTask(t1);
        taskList.addTask(t2);
        taskList.addTask(t2);
        taskList.addTask(t3);

        assertTrue(taskList.tagFilter("Party").contains(t3));
        assertEquals(1, taskList.tagFilter("Party").size());
        assertTrue(taskList.tagFilter("Party").contains(t3));
        assertEquals(2, taskList.tagFilter("Work").size());
        assertTrue(taskList.tagFilter("Work").contains(t2));
        assertFalse(taskList.tagFilter("you").contains(t1));
        assertEquals(0, taskList.tagFilter("you").size());
    }

    @Test
    void testDateFilter() {
        taskList.addTask(t1);
        taskList.addTask(t2);
        taskList.addTask(t3);

        assertTrue(taskList.dateFilter("2021-08-07", "2021-08-09").contains(t1));
        assertEquals(3,taskList.dateFilter("2021-08-07", "2021-10-02").size());
        assertTrue(taskList.dateFilter("2021-08-07", "2021-10-02").contains(t1));
        assertTrue(taskList.dateFilter("2021-08-07", "2021-10-02").contains(t2));
        assertTrue(taskList.dateFilter("2021-08-07", "2021-10-02").contains(t3));
        assertEquals(0,taskList.dateFilter("2021-08-03", "2021-08-04").size());
    }

    @Test
    void testDefaultDateFilter() {
        taskList.addTask(t1);
        assertEquals(0, taskList.dateFilter("2021-08-09", "2021-08-10").size());
        assertEquals(0, taskList.dateFilter("2021-08-02", "2021-08-04").size());
        assertEquals(0, taskList.dateFilter("2021-08-09", "2021-08-08").size());
        assertEquals(1, taskList.dateFilter("2021-08-08", "2021-08-08").size());
        assertEquals(0, taskList.dateFilter("2021-08", "2021--04").size());
    }


    @Test
    void testReview() {
        taskList.addTask(t3);
        taskList.addTask(t2);
        taskList.addTask(t1);

        assertEquals(3, taskList.review().size());
    }

    @Test
    void testToString() {
        assertTrue(task.toString().contains(
                "\t\t" + "Party"
                        + "\t\t" + "2021-10-01"
                        + "\t\t" + 300
                        + "\t\t\t\t\t" + "Homie's Birthday party"));
    }

}