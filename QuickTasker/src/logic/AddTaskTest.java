package logic;

import model.Task;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Author A0130949 Soh Yonghao
 * <p>
 * .
 */

public class AddTaskTest {
    Logic logic;

    @Before
    public void setUp() throws Exception {
        logic = new Logic();
    }

    @Test
    public void test() throws Exception {
        setUp();
        logic.addTask(new Task());
        assertEquals(logic.getSize(), 1);
        logic.addTask(new Task());
        assertEquals(logic.getSize(), 2);

    }

}