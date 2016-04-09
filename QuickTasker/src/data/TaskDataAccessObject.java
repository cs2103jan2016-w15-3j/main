package data;
//@@author A0126077E

import model.Task;

import java.util.List;

public interface TaskDataAccessObject {

    List<Task> getTasks();

    void save(Task t);

    void save(List<Task> ts);

    void reset();
}
