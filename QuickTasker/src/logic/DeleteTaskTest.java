package logic;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Task;
/*
 * 
 * author A0130949
 * Soh Yonghao
 * 
 * */

public class DeleteTaskTest {
    Logic logic;

    @Before
    public void setUp() throws Exception {
        logic = new Logic();
        for (int i = 0; i < 10; i++) {
            Task task = new Task(i);
            logic.list.add(task);
        }
    }

    @Test
    public void test() throws Exception {
        setUp();
        assertEquals(logic.getSize(), 10);
        logic.deleteTask(0);
        assertEquals(logic.getSize(), 9);
        logic.deleteTask(8);
        assertEquals(logic.getSize(), 8);

    }
}
