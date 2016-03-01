package logic;

import java.util.List;

import model.Task;
/*
 * 
 * author A0130949
 * Soh Yonghao
 * 
 * */

public class AddTask<E> implements Command {
    private static final String NOTHING_TO_ADD = "Cannot add nothing";

    @Override
    public void execute(List list, Object task) {
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
