package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import model.Task;

/**
 * 
 * Author A0130949 Soh Yonghao
 * 
 * .
 */

public class DeleteTask<E> implements Command<Object> {
    private Stack<Task> undoStack = new Stack<Task>();
    
    @Override
    public void execute(List<Task> list, Object task) {
        undoStack.push((Task) list.get((int) task));
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