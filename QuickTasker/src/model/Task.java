package model;
import java.time.LocalDate;

/**
 * 
 * @author A0121558H/A0130949
 *
 */
public class Task {
    private static String taskName;
    private static LocalDate endDate;
    private static LocalDate startDate;

    public void setStartDateAsNow() {
        startDate = LocalDate.now();
    }

    /** Default constructor **/
    public Task() {
        taskName = "";
        endDate = LocalDate.MIN;
        setStartDateAsNow();
    }
    /** Constructor for tasks with only task name **/
    public Task(String taskName) {
        Task.taskName = taskName;
        setStartDateAsNow();
        Task.endDate = LocalDate.MAX;
    }

    /** Constructor for floating tasks **/
    public Task(String taskName, LocalDate startDate) {
        Task.taskName = taskName;
        Task.endDate = LocalDate.MAX;
        Task.startDate = startDate;
    }

    /** Constructor with all fields filled **/
    public Task(String taskName, LocalDate startDate, LocalDate endDate) {
        Task.taskName = taskName;
        Task.endDate = endDate;
        Task.startDate = startDate;
    }

    public String getName() {
        return taskName;
    }

    public LocalDate getDueDate() {
        return endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setName(String newName) {
        taskName = newName;
    }

    private static void setNewEndDate(int date, int month, int year) {
        LocalDate newDate = LocalDate.of(date, month, year);
        endDate = newDate;
    }
}
