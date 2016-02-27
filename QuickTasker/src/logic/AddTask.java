package logic;

import java.util.List;

import model.Task;

public class AddTask implements Command {

    @Override
    public void execute(List<Task> list, Task task) {
        executeAdd(list, task);
    }

    private void executeAdd(List<Task> list, Task task) {
        if (task != null) {
            list.add(task);

        }
    }
}
