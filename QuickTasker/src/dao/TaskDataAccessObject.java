package dao;

import java.util.List;

import model.Task;

public interface TaskDataAccessObject {
    /**
     * Implements the DAO Design Pattern for the Task objects.
     * http://www.tutorialspoint.com/design_pattern/data_access_object_pattern.htm
     */
    public List<Task> getTasks();

    public void save(Task task);

    public void save(List<Task> tasks);

    public void reset();

    // public void delete(Task task); // remove a single task

}
