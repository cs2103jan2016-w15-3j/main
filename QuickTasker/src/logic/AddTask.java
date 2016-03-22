package logic;

import model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Author A0130949 Soh Yonghao
 *
 */

public class AddTask<E> implements Command<Object> {
    private static final String NOTHING_TO_ADD = "Cannot add nothing";
    private static Logger loggerAdd = Logger.getLogger("log");
    private Stack<Task> undoStack = new Stack<Task>();
    private Stack<Task> redoStack = new Stack<Task>();

    @Override
    public void execute(List<Task> list, Object task) {
        assert (list.size() >= 0);
        undoStack.push((Task) task);
        executeAdd(list, (Task) task);
    }

    private void executeAdd(List<Task> list, Task task) {
        loggerAdd.log(Level.INFO, "START");
        try {
            if (task != null) {
                int size = list.size();
                list.add(task);
                assert ((size + 1) == list.size());
            } else {
                System.out.println(NOTHING_TO_ADD);
            }
        } catch (Exception e) {
            loggerAdd.log(Level.WARNING, "ERROR", e);
        }
        loggerAdd.log(Level.INFO, "END");
    }

    @Override
    public void undo(ArrayList<Task> list) {
        Task undoTask = undoStack.pop();
        redoStack.push(undoTask);
        int index = findTask(undoTask.getId(), (ArrayList<Task>) list);
        if (index >= 0) {
            list.remove(index);
        } else {
            System.out.println("Cannot undo the add");
        }
    }

    private int findTask(int index, ArrayList<Task> list) {
        int position = -1;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == index) {
                position = i;
            }
        }
        return position;
    }

    @Override
    public void redo(ArrayList<Task> list) {
        Task redoTask = redoStack.pop();
        undoStack.push(redoTask);
        list.add(redoTask);
    }
}
