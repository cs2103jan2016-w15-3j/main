package logic;

import java.util.ArrayList;
import java.util.List;

import controller.InputInterface;
import model.Task;

public interface Command {
    void execute(List<Task> list, Task task);
}

