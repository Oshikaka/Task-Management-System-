package persistence;

import model.Task;
import model.TodoList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            TodoList tl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyTodoList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyTodoList.json");
        try {
            TodoList tl = reader.read();
            assertEquals(0, tl.length());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralTodoList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralTodoList.json");
        try {
            TodoList tl = reader.read();
            assertEquals(2, tl.length());
            List<Task> tasks = tl.getTasks();
            checkTask("Party",
                    "2021-10-01",
                    300,
                    "Homie's Birthday party",
                    tasks.get(0));
            checkTask("Math",
                    "2021-08-08",
                    30,
                    "Assignment 1",
                    tasks.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
