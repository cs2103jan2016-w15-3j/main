package model;

import java.time.LocalDate;

/**
 * .
 *
 * @author A0121558H/A0130949
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

    /**
     * Default constructor *.
     */
    public Task() {
        taskName = "";
        endDate = LocalDate.MIN;
        setStartDateAsNow();
        generateId();
    }

    /**
     * Constructor for tasks with only task name *.
     */
    public Task(String taskName) {
        this.taskName = taskName;
        setStartDateAsNow();
        this.endDate = LocalDate.MAX;
        generateId();
    }

    /**
     * Constructor for floating tasks *.
     */
    public Task(String taskName, LocalDate startDate) {
        this.taskName = taskName;
        this.endDate = LocalDate.MAX;
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

    private void generateId() {
        this.id = IdGenerator++;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
        result = prime * result + id;
        result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
        result = prime * result + ((taskName == null) ? 0 : taskName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Task other = (Task) obj;
        if (endDate == null) {
            if (other.endDate != null) return false;
        } else if (!endDate.equals(other.endDate)) return false;
        if (id != other.id) return false;
        if (startDate == null) {
            if (other.startDate != null) return false;
        } else if (!startDate.equals(other.startDate)) return false;
        if (taskName == null) {
            if (other.taskName != null) return false;
        } else if (!taskName.equals(other.taskName)) return false;
        return true;
    }

}
