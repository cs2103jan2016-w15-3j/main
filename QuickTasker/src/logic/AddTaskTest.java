package logic;

import model.Task;
import org.junit.Before;
import org.junit.Test;
import parser.Commands;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Author A0130949 Soh Yonghao
 * <p>
 * .
 */

public class AddTaskTest {
    Logic logic;

    @Before public void setUp() throws Exception {
        logic = new Logic();
    }

    @Test public void testSizeOfListAfterAdd() throws Exception {
        logic.addTask(new Task());
        logic.addTask(new Task());
        assertEquals(logic.getSize(), 2);
        logic.commandMap.get(Commands.CREATE_TASK).undo((ArrayList<Task>) logic.list);
        assertEquals(logic.getSize(), 1);
    }

}