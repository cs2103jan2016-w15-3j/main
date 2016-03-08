package logic;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import model.Task;

/**
 * 
 * Author A0130949 Soh Yonghao
 * 
 * .
 */

public class DisplayTaskTest {
    private ArrayList<Task> test;
    private ArrayList<Task> tasks;

    @Before
    public void setUp() throws Exception {
        test = new ArrayList<Task>();
        for (int i = 0; i < 10; i++) {
            Task task = new Task();
            test.add(task);
        }
    }

    @Test
    public void test() {
        DisplayTask<Task> dTask = new DisplayTask<Task>();
        assertEquals(dTask.executeDisplay(test), test);
    }

}
