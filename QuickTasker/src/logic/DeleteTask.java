package logic;

import model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Author A0130949 Soh Yonghao
 */

public class DeleteTask<E> implements Command<Object> {
    private static Stack<Task> undoStackTask = new Stack<Task>();
    private static Stack<Integer> undoStackIndex = new Stack<Integer>();
    private static Stack<Task> redoStackTask = new Stack<Task>();
    private static Stack<Integer> redoStackIndex = new Stack<Integer>();

    @Override
    public void execute(List<Task> list, Object op) {
        undoStackTask.push(list.get((int) op));
        undoStackIndex.push((int) op); 
        executeDelete(list, (int) op);
    }

    private void executeDelete(List<Task> list, int index) {
        list.remove(index);
    }

    @Override
    public void undo(ArrayList<Task> list) {
        Task deletedTask = undoStackTask.pop();
        int indexToUndo = undoStackIndex.pop();
        redoStackTask.push(deletedTask);
        redoStackIndex.push(indexToUndo);
        list.add(indexToUndo, deletedTask);
    }

    @Override
    public void redo(ArrayList<Task> list) {
        // TODO Auto-generated method stub
        Task redoTask = redoStackTask.pop();
        int indexToRedo = redoStackIndex.pop();
        undoStackTask.push(redoTask);
        undoStackIndex.push(indexToRedo);
        //int index = findTask(redoTask.getId(), list);
        list.remove(indexToRedo);
    }

/*    private int findTask(int index, ArrayList<Task> list) {
        int position = -1;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == index) {
                position = i;
            }
        }
        return position;
    }*/
}