package logic;

import data.SettingManager;
import model.Task;
import org.junit.Before;
import org.junit.Test;
import parser.Commands;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

//@@author A0130949

public class DeleteTaskTest {
    Logic logic;
    SettingManager settings;

    @Before public void setUp() throws Exception {
        init();
    }

    private void init() {
        logic = new Logic();
        logic.clear();
        for (int i = 0; i < 10; i++) {
            Task task = new Task();
            logic.list.add(task);
        }
    }

    @Test public void test() throws Exception {
        assertEquals(10, logic.getSize());
        logic.deleteTask(0);
        assertEquals(9, logic.getSize());
        logic.deleteTask(8);
        assertEquals(8, logic.getSize());
        logic.commandMap.get(Commands.DELETE_TASK).undo((ArrayList<Task>) logic.list);
        assertEquals(9, logic.getSize());
    }

    @Test public void testNegative() throws Exception {
        boolean testNegative = false;
        try {
            logic.deleteTask(-1);
        } catch (IllegalArgumentException e) {
            testNegative = true;
        }
        assert (testNegative);
    }

    @Test public void testString() throws Exception {
        boolean testString = false;
        try {
            logic.deleteTask(Integer.parseInt("hi"));
        } catch (NumberFormatException e) {
            testString = true;
        }
        assert (testString);
    }
}
