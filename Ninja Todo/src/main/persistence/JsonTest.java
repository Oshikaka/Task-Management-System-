package persistence;

import model.Task;
import model.TodoList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkTask(String tag, String dueDay, int estTime, String description, Task task) {
        assertEquals(tag, task.getTag());
        assertEquals(dueDay, task.getDueDay());
        assertEquals(estTime, task.getEstTime());
        assertEquals(description, task.getDescription());
    }
}
