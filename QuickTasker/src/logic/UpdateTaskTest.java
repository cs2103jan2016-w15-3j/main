package logic;

import model.Task;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class UpdateTaskTest {
    Logic logic;

    @Before
    public void setUp() throws Exception {
        init();
    }

    private void init() {
        logic = new Logic();
        logic.addTask(new Task());
        logic.addTask(new Task());
    }

    @Test
    public void testUpdatingWithName() throws Exception {
        logic.updateTask(new Task("name", LocalDate.now(), LocalDate.now()), 0);
        assertEquals("name", logic.getList().get(0).getName());
    }

    @Test
    public void testUpdatingWithLongerName() throws Exception {
        logic.updateTask(new Task("name longer by alot", LocalDate.now(), LocalDate.now()), 1);
        assertEquals("name longer by alot", logic.getList().get(1).getName());
    }
}
