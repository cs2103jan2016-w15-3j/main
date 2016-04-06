package logic;

import data.SettingManager;
import model.Task;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import parser.Commands;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

//@@author A0130949

public class AddTaskTest {
    Logic logic;
    private SettingManager settings;

    @Before
    public void setUp() throws Exception {
        settings = new SettingManager();
        settings.setPathOfSaveFile("test.json");
        logic = new Logic();
        createEmptyTasks(2);
    }

    private void createEmptyTasks(int n) {
        for (int i = 0; i < n; i++) {
            logic.addTask(new Task());
        }
    }

    @After
    public void tearDown() {
        settings.resetDefaultSettings();
        logic.clear();
    }

    @Test
    public void testSizeOfListAfterAdd() throws Exception {
        assertEquals(logic.getSize(), 2);
    }

    @Test
    public void testSizeOfListAfterAddingNull() throws Exception {
        logic.addTask(new Task(null, null, null));
        assertEquals(logic.getSize(), 3);
    }

    @Test
    // A user could have added normally
    public void testNameAfterAdding() throws Exception {
        logic.addTask(new Task("name", LocalDate.now(), LocalDate.now()));
        assertEquals(logic.getTasks().get(2).getName(), "name");
    }

    @Test
    // A user could have added task without start date
    public void testNameAfterAddingWithoutStartDate() throws Exception {
        logic.addTask(new Task("name", null, LocalDate.now()));
        assertEquals(logic.getTasks().get(2).getName(), "name");
    }

    @Test
    // A user could have added task without due date
    public void testNameAfterAddingWithoutDueDate() throws Exception {
        logic.addTask(new Task("name", LocalDate.now(), null));
        assertEquals(logic.getTasks().get(2).getName(), "name");
    }

    @Test
    // A user could have added task without dates
    public void testNameAfterAddingWithoutDates() throws Exception {
        logic.addTask(new Task("name", null, null));
        assertEquals(logic.getTasks().get(2).getName(), "name");
    }

    @Test
    // A user could have undo add
    public void testUndoAfterAdding() throws Exception {
        logic.commandMap.get(Commands.CREATE_TASK).undo((ArrayList<Task>) logic.list);
        assertEquals(logic.getSize(), 1);
    }
}