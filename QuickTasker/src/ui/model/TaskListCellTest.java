package ui.model;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.List;

import javax.swing.ListCellRenderer;

import org.junit.Before;
import org.junit.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Task;

// @author: A0133333U

public class TaskListCellTest {
    
//ObservableList<Task> guiList; 

    Task  t1;
    private TaskListCell taskListCell;
    private ObservableList<Task> testTasks;
   
    
    @Before 
  public void setUp() throws Exception {
        
        //ObservableList<Task> guiList = FXCollections.observableArrayList();
        //taskListCell = new TaskListCell(testTasks);
        //ObservableList<Task> guiList = null;
        //taskListCell = new TaskListCell(guiList);
    }
    


    @Test
    public void testSetTaskId() {
        
        String taskName = "Buy bananas";
        Task testTask = new Task(taskName, LocalDate.now(), LocalDate.now());
        assertEquals(5, testTask.getId());

    }
   
   @Test
    public void testSetTaskName() {
        String taskName = "Buy bananas";
        Task testTask = new Task(taskName, LocalDate.now(), LocalDate.now());
        
        assertEquals("Buy bananas",testTask.getName());
    }
    
    @Test
    // to fill in comments 
    public void testTaskGetName() {
        Task t1 = new Task ("buy fruits");
        assertEquals("buy fruits", t1.getName());
    }

    @Test
    public void testSetTaskStartDate() {
        String taskName = "Buy bananas";
        Task testTask = new Task(taskName, LocalDate.now(), LocalDate.now());
        assertEquals(LocalDate.now(), testTask.getStartDate());
    }

    @Test
    public void testSetTaskDueDate() { 
        String taskName = "Buy bananas";
        Task testTask = new Task(taskName, LocalDate.now(), LocalDate.now());
        assertEquals(LocalDate.now(), testTask.getDueDate());
    }

}
