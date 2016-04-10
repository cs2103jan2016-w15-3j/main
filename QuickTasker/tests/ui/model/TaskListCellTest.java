package ui.model;

import javafx.collections.ObservableList;
import model.Task;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

/*
 * @@author Lee Jia Lin <A0133333U>
 */
public class TaskListCellTest {


	// denotes the Task object
    Task t1;
    private TaskListCell taskListCell;
    private ObservableList<Task> testTasks;

    @Before public void setUp() throws Exception {

    }

    @Test public void testSetTaskName() {
        String taskName = "Buy bananas";
        Task testTask = new Task(taskName, LocalDate.now(), LocalDate.now());
        assertEquals("Buy bananas", testTask.getName());
    }

    @Test
    // method will test the name of the created task to see if they are the same as expected
    public void testTaskGetName() {
        Task t1 = new Task("buy fruits");
        assertEquals("buy fruits", t1.getName());
    }

    
    @Test 
    // method will test start date of task to see if they are the same as expected start date
    public void testSetTaskStartDate() {
        String taskName = "Buy bananas";
        Task testTask = new Task(taskName, LocalDate.now(), LocalDate.now());
        assertEquals(LocalDate.now(), testTask.getStartDate());
    }

    @Test 
    // method will test end date of task to see if they are the same as expected end date
    public void testSetTaskDueDate() {
        String taskName = "Buy bananas";
        Task testTask = new Task(taskName, LocalDate.now(), LocalDate.now());
        assertEquals(LocalDate.now(), testTask.getDueDate());
    }

}
