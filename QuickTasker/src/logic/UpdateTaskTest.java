package logic;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Task;

public class UpdateTaskTest {
    Logic logic;
    
    @Before
    public void setUp() throws Exception {
        init();
    }

    private void init() {
        logic = new Logic();
        logic.addTask(new Task());
        logic.addTask(new Task());
    }

    @Test
    public void testUpdatingWithName() throws Exception {
        logic.updateTask("update 1 name 11-11-2016 11-12-2017");
        assertEquals("name", logic.getList().get(0).getName());
    }
    
    @Test
    public void testUpdatingWithLongerName() throws Exception {
        logic.updateTask("update 2 name longer by alot 11-11-2016 11-12-2017");
        assertEquals("name longer by alot", logic.getList().get(1).getName());
    }
}
