package dao;

import java.util.List;

import model.Task;

public interface TaskDataAccessObject {
    /**
     * Implements the DAO Design Pattern for the Task objects.
     * http://www.tutorialspoint.com/design_pattern/data_access_object_pattern.
     * htm
     */
    List<Task> getTasks();

    void save(Task task);

    void save(List<Task> tasks);

    void reset();

    // public void delete(Task task); // remove a single task

}
