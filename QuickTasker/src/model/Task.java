package model;

import java.time.LocalDate;

/**
 * .
 *
 * @author A0121558H/A0130949
 */
public class Task implements Comparable {
    private static int IdGenerator;
    private String taskName;
    private LocalDate endDate;
    private LocalDate startDate;
    private boolean isDone = false;
    private int id;

    public void setStartDateAsNow() {
        startDate = LocalDate.now();
    }

    /**
     * Default constructor *.
     */
    public Task() {
        this.taskName = "";
        this.endDate = LocalDate.MIN;
        this.setStartDateAsNow();
        generateId();
    }

    /**
     * Constructor for tasks with only task name *.
     */
    public Task(String taskName) {
        this.taskName = taskName;
        setStartDateAsNow();
        this.endDate = LocalDate.MIN;
        generateId();
    }

    /**
     * Constructor for floating tasks *.
     */
    public Task(String taskName, LocalDate startDate) {
        this.taskName = taskName;
        this.endDate = LocalDate.MIN;
        this.startDate = startDate;
        generateId();
    }

    /**
     * Constructor with all fields filled *.
     */
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

    public boolean isDone() {
        return isDone;
    }

    public void setStartDate(LocalDate date) {
        this.startDate = date;
    }

    public void setEndDate(LocalDate date) {
        this.endDate = date;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    private void generateId() {
        this.id = ++IdGenerator;
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
        result = 31 * result + id;
        return result;
    }

    @Override
    public int compareTo(Object task) {
        Task comparedTask = (Task) task;

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
