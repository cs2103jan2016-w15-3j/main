package model;
//@@author A0121558H

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class Task implements Comparable {
    private String taskName;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean isDone = false;
    private String id;
    private String taskType;

    public void setStartDateAsNow() {
        startDate = LocalDate.now();
    }

    public Task() {
        this.taskName = "";
        this.endDate = LocalDate.MIN;
        this.setStartDateAsNow();
        this.id = generateId();
        setTaskType();
    }

    /**
     * Constructor for tasks with only task name *.
     */
    public Task(String taskName) {
        this.taskName = taskName;
        setStartDateAsNow();
        this.endDate = LocalDate.MIN;
        this.id = generateId();
    }

    /**
     * Constructor for floating tasks *.
     */
    public Task(String taskName, LocalDate startDate) {
        this.taskName = taskName;
        this.endDate = LocalDate.MIN;
        this.startDate = startDate;
        this.id = generateId();

    }

    /**
     * Constructor with all fields filled *.
     */
    public Task(String taskName, LocalDate startDate, LocalDate endDate) {
        this.taskName = taskName;
        this.endDate = endDate;
        this.startDate = startDate;
        this.id = generateId();
    }

    // @author: A0133333U
    // this is the only constructor being used, with the rest bypassed
    // There need to be a way to determine the correct task type and call the respective constructor
    public Task(String taskName, LocalDate startDate, LocalDate endDate, LocalTime startTime,
            LocalTime endTime) {
        this.taskName = taskName;
        this.endDate = endDate;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endTime = endTime;
        setTaskType();
        this.id = generateId();
    }

    public void setTaskType() {
        if (bothDateAndTimeAreDefaultValue()) this.taskType = "floating";
        else if (onlyTimeAreDefaultValue()) this.taskType = "wholeDayEvent";
        else this.taskType = "task";
    }

    private boolean onlyTimeAreDefaultValue() {
        return (startTime == null || this.startTime == LocalTime.MIN) && (this.endTime == null
                || this.endTime == LocalTime.MIN);
    }

    //
    private boolean bothDateAndTimeAreDefaultValue() {
        return (startDate == null || this.startDate.equals(LocalDate.MAX)) && (endDate == null || this.endDate
                .equals(LocalDate.MAX)) && (startTime == null || this.startTime.equals(LocalTime.MAX)) && (
                endDate == null || this.endDate.equals(LocalDate.MAX));
    }

    public String getTaskType() {
        return this.taskType;
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

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setName(String newName) {
        taskName = newName;
    }

    public String getId() {
        return id;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setStartDate(LocalDate date) {
        this.startDate = date;
    }

    public void setEndDate(LocalDate date) {
        this.endDate = date;
    }

    public void setStartTime(LocalTime time) {
        this.startTime = time;
    }

    public void setEndTime(LocalTime time) {
        this.endTime = time;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    private String generateId() {
        return UUID.randomUUID().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;

        Task task = (Task) o;

        if (!id.equals(task.id)) return false;
        if (!taskName.equals(task.taskName)) return false;
        if (endDate != null ? !endDate.equals(task.endDate) : task.endDate != null) return false;
        return startDate != null ? startDate.equals(task.startDate) : task.startDate == null;

    }

    @Override
    public int hashCode() {
        int result = taskName.hashCode();
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + id.hashCode();
        return result;
    }

    @Override
    public int compareTo(Object task) {
        Task comparedTask = (Task) task;
        int result = compareDueDate(this, comparedTask);

        if (result == 0) {
            result = compareStartDate(this, comparedTask);
        }

        if (result == 0) {
            result = this.getName().compareTo(comparedTask.getName());
        }

        return result;
    }

    private int compareDueDate(Task task, Task comparedTask) {
        if (task.getDueDate() == null && comparedTask.getDueDate() == null) {
            return 0;
        } else if (task.getDueDate() != null && comparedTask.getDueDate() == null) {
            return 1;
        } else if (task.getDueDate() == null && comparedTask.getDueDate() != null) {
            return -1;
        } else {
            return comparedTask.getDueDate().compareTo(this.getDueDate());
        }
    }

    private int compareStartDate(Task task, Task comparedTask) {
        if (task.getStartDate() == null && comparedTask.getStartDate() == null) {
            return 0;
        } else if (task.getStartDate() != null && comparedTask.getStartDate() == null) {
            return 1;
        } else if (task.getStartDate() == null && comparedTask.getStartDate() != null) {
            return -1;
        } else {
            return comparedTask.getStartDate().compareTo(this.getStartDate());
        }
    }
}
