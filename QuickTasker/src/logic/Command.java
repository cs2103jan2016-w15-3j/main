package logic;

import java.util.List;

import model.Task;

public interface Command {
    void execute(List<Task> list, Task task);
}
