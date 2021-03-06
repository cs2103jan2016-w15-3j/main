package model;
//@@author A0121558H

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class Task implements Comparable {
    private String taskName;
    private LocalDate startDate;
    private LocalDate endDate;
    // @@author A0133333U
    private LocalTime startTime;
    private LocalTime endTime;
    //@@author A0121558H
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

    // @@author A0133333U
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
        this.taskType = bothDateAndTimeAreDefaultValue() ?
                "floating" :
                !onlyTimeAreDefaultValue() ? "task" : "wholeDayEvent";
    }

    private boolean onlyTimeAreDefaultValue() {
        return (startTime == null || this.startTime == LocalTime.MIN) && (this.endTime == null
                || this.endTime == LocalTime.MAX);
    }

    //
    private boolean bothDateAndTimeAreDefaultValue() {
        return (startDate == null || this.startDate.equals(LocalDate.MIN)) && (endDate == null || this.endDate
                .equals(LocalDate.MAX)) && (startTime == null || this.startTime.equals(LocalTime.MIN)) && (
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

    // @@author A0133333U
    public LocalTime getStartTime() {
        return startTime;
    }

    // @@author A0133333U
    public LocalTime getEndTime() {
        return endTime;
    }

    // @@author 
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

    // @@author A0133333U
    public void setStartTime(LocalTime time) {
        this.startTime = time;
    }

    // @@author A0133333U
    public void setEndTime(LocalTime time) {
        this.endTime = time;
    }

    // @@author
    public void setDone(boolean done) {
        isDone = done;
    }

    private String generateId() {
        return UUID.randomUUID().toString();
    }

    //@@author A0126077E
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return id.equals(task.id) && taskName.equals(task.taskName) && !(endDate == null ?
                task.endDate != null :
                !endDate.equals(task.endDate)) && (startDate == null ?
                task.startDate == null :
                startDate.equals(task.startDate));
    }

    @Override
    public int hashCode() {
        return 31 * ((startDate == null ? 0 : startDate.hashCode()) + 31 * (31 * taskName.hashCode() + (
                endDate == null ?
                        0 :
                        endDate.hashCode()))) + id.hashCode();
    }

    //@@author A0130949
    @Override
    public int compareTo(Object task) {
        Task comparedTask = (Task) task;
        int result = compareDueDate(comparedTask, this);

        if (result == 0) {
            result = compareStartDate(comparedTask, this);
        }

        if (result == 0) {
            result = comparedTask.getName().compareTo(this.getName());
        }
        return result;
    }

    private int compareDueDate(Task task, Task comparedTask) {
        if (task.isDueDateEmpty() && comparedTask.isDueDateEmpty()) {
            System.out.println(task.getName() + " due is empty");
            return 0;
        } else if (!task.isDueDateEmpty() && comparedTask.isDueDateEmpty()) {
            return 1;
        } else if (task.isDueDateEmpty() && !comparedTask.isDueDateEmpty()) {
            return -1;
        } else {
            return comparedTask.getDueDate().compareTo(task.getDueDate());
        }
    }

    private int compareStartDate(Task task, Task comparedTask) {
        if (task.isStartDateEmpty() && comparedTask.isStartDateEmpty()) {
            System.out.println(task.getName() + " add Start is empty");
            return 0;
        } else if (!task.isStartDateEmpty() && comparedTask.isStartDateEmpty()) {
            return 1;
        } else if (task.isStartDateEmpty() && !comparedTask.isStartDateEmpty()) {
            return -1;
        } else {
            return task.getStartDate().compareTo(comparedTask.getStartDate());
        }
    }

    public boolean isDueDateEmpty() {
        return this.getDueDate() == null || this.getDueDate().equals(LocalDate.MIN) || this.getDueDate()
                .equals(LocalDate.MAX);
    }

    public boolean isStartDateEmpty() {
        return this.getStartDate() == null || this.getStartDate().equals(LocalDate.MIN) || this.getStartDate()
                .equals(LocalDate.MAX);
    }

    public boolean isStartTimeEmpty() {
        return this.getStartTime() == null || this.getStartTime().equals(LocalTime.MAX) || this.getStartTime()
                .equals(LocalTime.MIN);
    }

    public boolean isEndTimeEmpty() {
        return this.getEndTime() == null || this.getEndTime().equals(LocalTime.MAX) || this.getEndTime()
                .equals(LocalTime.MIN);
    }

    public boolean isDatesInvalid() {
        return this.getStartDate().isAfter(this.getDueDate());
    }
}
