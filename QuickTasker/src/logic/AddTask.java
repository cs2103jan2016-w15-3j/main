package logic;

import model.Task;

import java.util.List;

/**
 * Author A0130949 Soh Yonghao
 * <p>
 * .
 */

public class AddTask<E> implements Command {
    private static final String NOTHING_TO_ADD = "Cannot add nothing";

    @Override public void execute(List list, Object task) {
        executeAdd(list, (Task) task);
    }

    private void executeAdd(List<Task> list, Task task) {
        if (task != null) {
            list.add(task);
        } else {
            System.out.println(NOTHING_TO_ADD);
        }
    }
}
