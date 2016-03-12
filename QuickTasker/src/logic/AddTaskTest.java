package logic;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import model.Task;

/**
 * 
 * Author A0130949 Soh Yonghao
 * 
 * .
 */

public class AddTaskTest {

    private Logic logic;

    @Before
    public void setUp() throws Exception {
        logic = new Logic();
    }

    @Test
    public void test() {

        logic.addTask(new Task());
        assertEquals(logic.getSize(), 1);
        logic.addTask(new Task());
        assertEquals(logic.getSize(), 2);

    }

}