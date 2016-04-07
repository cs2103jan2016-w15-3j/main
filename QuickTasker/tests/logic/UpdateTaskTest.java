package logic;

import data.SettingManager;
import model.Task;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class UpdateTaskTest {
    Logic logic;
    SettingManager settings;

    @Before
    public void setUp() throws Exception {
        init();
    }

    private void init() {
        logic = new Logic();
        logic.clear();
        logic.addTask(new Task("hello", LocalDate.now(), LocalDate.now()));
        logic.addTask(new Task());
    }

    @Test
    // This is the regular update
    public void testUpdatingWithName() throws Exception {
        logic.updateTask(new Task("name", LocalDate.now(), LocalDate.now()), 0);
        assertEquals("name", logic.getTasks().get(0).getName());
    }

    @Test
    // A user could have updated a longer name
    public void testUpdatingWithLongerName() throws Exception {
        logic.updateTask(new Task("name longer by alot", LocalDate.now(), LocalDate.now()), 1);
        assertEquals("name longer by alot", logic.getTasks().get(1).getName());
    }

    @Test
    // A user could have changed the task to floating 
    public void testUpdatingWithFloating() throws Exception {
        logic.updateTask(new Task("name longer by alot", null, null), 1);
        assertEquals("name longer by alot", logic.getTasks().get(0).getName());
    }

    @Test
    public void testUpdatingOnlyWithName() throws Exception {
        logic.updateTask(new Task("name longer by alot", LocalDate.MIN, LocalDate.MIN), 0);
        assertEquals("name longer by alot", logic.getTasks().get(0).getName());
        assertEquals(LocalDate.now(), logic.getTasks().get(0).getStartDate());
        assertEquals(LocalDate.now(), logic.getTasks().get(0).getDueDate());
    }

    @Test
    public void testUpdatingOnlyWithDates() throws Exception {
        logic.updateTask(new Task("", LocalDate.of(2016, 4, 1), LocalDate.of(2016, 5, 1)), 0);
        assertEquals("hello", logic.getTasks().get(0).getName());
        assertEquals(LocalDate.of(2016, 4, 1), logic.getTasks().get(0).getStartDate());
        assertEquals(LocalDate.of(2016, 5, 1), logic.getTasks().get(0).getDueDate());
    }

    @Test
    // A user could have changed name without start date
    public void testUpdatingWithoutStartDate() throws Exception {
        logic.updateTask(new Task("name longer by alot", null, LocalDate.now()), 1);
        assertEquals("name longer by alot", logic.getTasks().get(0).getName());
    }

    @Test
    // A user could have changed name without due date
    public void testUpdatingWithoutDueDate() throws Exception {
        logic.updateTask(new Task("name longer by alot", LocalDate.now(), null), 1);
        assertEquals("name longer by alot", logic.getTasks().get(0).getName());
    }

    @Test
    public void testUpdatingNegativeIndex() throws Exception {
        boolean testNegative = false;
        try {
            logic.updateTask(new Task("name longer by alot", LocalDate.now(), null), -1);
        } catch (IllegalArgumentException e) {
            testNegative = true;
        }
        assert (testNegative);
    }

    @Test
    public void testUpdatingWithString() throws Exception {
        boolean testString = false;
        try {
            logic.updateTask(new Task("name longer by alot", LocalDate.now(), null), Integer.parseInt("hi"));
        } catch (NumberFormatException e) {
            testString = true;
        }
        assert (testString);
    }
}
