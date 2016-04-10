package logic;
//@@author A0130949Y
import model.Task;

import java.util.ArrayList;
import java.util.List;

public class ClearTasks<E> implements Command<Object> {
    private ArrayList<Task> tasks = new ArrayList<Task>();

    @Override
    public void execute(List<Task> list, Object op) {
        executeClear(list);
    }

    // transfer the current list to a new task for undo proposes
    private void executeClear(List<Task> list) {
        tasks.clear();
        for (int i = 0; i < list.size(); i++) {
            tasks.add(list.get(i));
        }
        list.clear();
    }

    @Override
    public void undo(ArrayList<Task> list) {
        list.clear();
        for (int i = 0; i < tasks.size(); i++) {
            list.add(tasks.get(i));
        }
    }

    @Override
    public void redo(ArrayList<Task> list) {
        list.clear();
    }

    @Override
    public int findTask(String id, ArrayList<Task> list) {
        return 0;
    }
}
