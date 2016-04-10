package data;
//@@author A0126077E

import model.Task;

import java.util.List;

public interface DataHandler {

    List<Task> getTasks();

    void save(Task t);

    void save(List<Task> ts);

    void setSavePath(String path);

    void reset();
}
