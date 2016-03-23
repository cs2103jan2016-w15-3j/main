package logic;

import model.Task;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class UpdateTaskTest {
    Logic logic;

    @Before public void setUp() throws Exception {
        init();
    }

    private void init() {
        logic = new Logic();
        logic.addTask(new Task());
        logic.addTask(new Task());
    }

    @Test
    // This is the regular update
    public void testUpdatingWithName() throws Exception {
        logic.updateTask(new Task("name", LocalDate.now(), LocalDate.now()), 0);
        assertEquals("name", logic.getList().get(0).getName());
    }

    @Test
    // A user could have updated a longer name
    public void testUpdatingWithLongerName() throws Exception {
        logic.updateTask(new Task("name longer by alot", LocalDate.now(), LocalDate.now()), 1);
        assertEquals("name longer by alot", logic.getList().get(1).getName());
    }
    
    @Test
    // A user could have changed the task to floating 
    public void testUpdatingWithFloating() throws Exception {
        logic.updateTask(new Task("name longer by alot", null, null), 1);
        assertEquals("name longer by alot", logic.getList().get(1).getName());
    }
    
    @Test
    // A user could have changed name without start date
    public void testUpdatingWithoutStartDate() throws Exception {
        logic.updateTask(new Task("name longer by alot", null, LocalDate.now()), 1);
        assertEquals("name longer by alot", logic.getList().get(1).getName());
    }
    
    @Test
    // A user could have changed name without due date
    public void testUpdatingWithoutDueDate() throws Exception {
        logic.updateTask(new Task("name longer by alot", LocalDate.now(), null), 1);
        assertEquals("name longer by alot", logic.getList().get(1).getName());
    }
}
