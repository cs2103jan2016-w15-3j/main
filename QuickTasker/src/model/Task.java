package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

/**
 * @author A0121558H/A0130949
 */
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
        this.id=generateId();
    }

    /**
     * Constructor for tasks with only task name *.
     */
    public Task(String taskName) {
        this.taskName = taskName;
        setStartDateAsNow();
        this.endDate = LocalDate.MIN;
        this.id=generateId();
    }

    /**
     * Constructor for floating tasks *.
     */
    public Task(String taskName, LocalDate startDate) {
        this.taskName = taskName;
        this.endDate = LocalDate.MIN;
        this.startDate = startDate;
        this.id=generateId();

    }

    /**
     * Constructor with all fields filled *.
     */
    public Task(String taskName, LocalDate startDate, LocalDate endDate) {
        this.taskName = taskName;
        this.endDate = endDate;
        this.startDate = startDate;
        this.id=generateId();
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
        this.id=generateId();
    }

    public void setTaskType() {
        if (this.startDate == LocalDate.MAX && this.endDate == LocalDate.MAX)
            this.taskType = "floating";
        else if (this.startTime == LocalTime.MIN && this.endTime == LocalTime.MIN)
            this.taskType = "wholeDayEvent";
        else this.taskType = "task";
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

        if (id != task.id) return false;
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
        if (this.getDueDate() == LocalDate.MIN) {
            return -1;
        }
        if (this.getDueDate().getDayOfMonth() > comparedTask.getDueDate().getDayOfMonth()) {
            if (this.getDueDate().getMonthValue() >= comparedTask.getDueDate().getMonthValue()) {
                if (this.getDueDate().getYear() >= comparedTask.getDueDate().getYear()) {
                    return 1;
                }
            }
        }
        if (this.getDueDate().getMonthValue() > comparedTask.getDueDate().getMonthValue()) {
            if (this.getDueDate().getYear() > comparedTask.getDueDate().getYear()) {
                return 1;
            }
        }
        if (this.getDueDate().getYear() > comparedTask.getDueDate().getYear()) {
            return 1;
        }

        if (this.getStartDate().getDayOfMonth() > comparedTask.getStartDate().getDayOfMonth()) {
            if (this.getStartDate().getMonthValue() >= comparedTask.getStartDate()
                    .getMonthValue()) {
                if (this.getStartDate().getYear() >= comparedTask.getStartDate().getYear()) {
                    return 1;
                }
            }
        }
        if (this.getStartDate().getMonthValue() > comparedTask.getStartDate().getMonthValue()) {
            if (this.getStartDate().getYear() > comparedTask.getStartDate().getYear()) {
                return 1;
            }
        }
        if (this.getStartDate().getYear() > comparedTask.getStartDate().getYear()) {
            return 1;
        }
        return -1;
    }
}
