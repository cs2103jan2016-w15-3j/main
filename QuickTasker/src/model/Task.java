package model;

import java.time.LocalDate;

/**
 * 
 * 
 * @author A0121558H/A0130949
 *
 */
public class Task {
    private static int IdGenerator;
    private String taskName;
    private LocalDate endDate;
    private LocalDate startDate;
    private int id;

    public void setStartDateAsNow() {
        startDate = LocalDate.now();
    }

    /** Default constructor *. */
    public Task() {
        taskName = "";
        endDate = LocalDate.MIN;
        setStartDateAsNow();
        generateId();
    }

    /** Constructor for tasks with only task name *. */
    public Task(String taskName) {
        this.taskName = taskName;
        setStartDateAsNow();
        this.endDate = LocalDate.MAX;
        generateId();
    }

    /** Constructor for floating tasks *. */
    public Task(String taskName, LocalDate startDate) {
        this.taskName = taskName;
        this.endDate = LocalDate.MAX;
        this.startDate = startDate;
        generateId();
    }

    /** Constructor with all fields filled *. */
    public Task(String taskName, LocalDate startDate, LocalDate endDate) {
        this.taskName = taskName;
        this.endDate = endDate;
        this.startDate = startDate;
        generateId();
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

    public int getId() {
        return id;
    }

    private void generateId() {
        this.id = IdGenerator++;
    }

}
