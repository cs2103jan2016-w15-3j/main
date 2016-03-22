package logic;

import model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Author A0130949 Soh Yonghao
 * <p>
 * .
 */

public class DeleteTask<E> implements Command<Object> {
    private Stack<Task> undoStack = new Stack<Task>();

    @Override
    public void execute(List<Task> list, Object task) {
        undoStack.push(list.get((int) task));
        executeDelete(list, (int) task);

    }

    private void executeDelete(List<Task> list, int index) {
        list.remove(index);
    }

    @Override
    public void undo(ArrayList<Task> list) {
        list.add(undoStack.pop());

    }
}