package logic;

import model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Author A0130949 Soh Yonghao
 */

public class DeleteTask<E> implements Command<Object> {
    private Stack<Task> undoStack = new Stack<Task>();
    private Stack<Task> redoStack = new Stack<Task>();

    @Override public void execute(List<Task> list, Object task) {
        undoStack.push(list.get((int) task));
        executeDelete(list, (int) task);
    }

    private void executeDelete(List<Task> list, int index) {
        list.remove(index);
    }

    @Override public void undo(ArrayList<Task> list) {
        Task deletedTask = undoStack.pop();
        redoStack.push(deletedTask);
        list.add(deletedTask.getId() - 1, deletedTask);
    }

    @Override public void redo(ArrayList<Task> list) {
        // TODO Auto-generated method stub
        Task redoTask = redoStack.pop();
        undoStack.push(redoTask);
        int index = findTask(redoTask.getId(), list);
        list.remove(index);
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
}