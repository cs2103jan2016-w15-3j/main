package logic;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import model.Task;

public class AddTaskTest {
    Logic logic;
    @Before
    public void setUp() throws Exception {
        logic = new Logic();
    }

    @Test
    public void test() throws Exception {
        setUp();
        logic.addTask(new Task(1));
        assertEquals(logic.getSize(), 1);
        logic.addTask(new Task("name", LocalDate.now(), LocalDate.now(),2));
        assertEquals(logic.getSize(), 2);
        
    }

}