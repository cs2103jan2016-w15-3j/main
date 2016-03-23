package logic;

import model.Task;
import org.junit.Before;
import org.junit.Test;
import parser.Commands;

import java.time.LocalDate;
import java.util.ArrayList;

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
        logic.addTask(new Task());
        logic.addTask(new Task());
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
        assertEquals(logic.getList().get(2).getName(), "name");
    }
    
    @Test 
    // A user could have added task without start date
    public void testNameAfterAddingWithoutStartDate() throws Exception {
        logic.addTask(new Task("name", null, LocalDate.now()));
        assertEquals(logic.getList().get(2).getName(), "name");
    }
    
    @Test 
    // A user could have added task without due date
    public void testNameAfterAddingWithoutDueDate() throws Exception {
        logic.addTask(new Task("name", LocalDate.now(), null));
        assertEquals(logic.getList().get(2).getName(), "name");
    }
    
    @Test
    // A user could have added task without dates
    public void testNameAfterAddingWithoutDates() throws Exception {
        logic.addTask(new Task("name", null, null));
        assertEquals(logic.getList().get(2).getName(), "name");
    }
    
    @Test 
    // A user could have undo add
    public void testUndoAfterAdding() throws Exception {
        logic.commandMap.get(Commands.CREATE_TASK).undo((ArrayList<Task>) Logic.list);
        assertEquals(logic.getSize(), 1);
    }
}