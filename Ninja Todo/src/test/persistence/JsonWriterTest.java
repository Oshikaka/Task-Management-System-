package persistence;

import model.Task;
import model.TodoList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            TodoList tl = new TodoList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyTodoList() {
        try {
            TodoList tl = new TodoList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyTodoList.json");
            writer.open();
            writer.write(tl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyTodoList.json");
            tl = reader.read();
            assertEquals(0, tl.length());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralTodoList() {
        try {
            TodoList tl = new TodoList();
            tl.addTask(new Task("English", "2022-09-09", 50, "Essay final version"));
            tl.addTask(new Task("CPSC210", "2021-11-09", 80, "Lab 10"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralTodoList.json");
            writer.open();
            writer.write(tl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralTodoList.json");
            tl = reader.read();
            assertEquals(2, tl.length());
            List<Task> tasks = tl.getTasks();
            checkTask("English", "2022-09-09", 50, "Essay final version", tasks.get(0));
            checkTask("CPSC210", "2021-11-09", 80, "Lab 10", tasks.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
