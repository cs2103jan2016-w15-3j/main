package logic;

import model.Task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class UpdateTask<E> implements Command<Object> {
    private Stack<Task> undoStackTask = new Stack<Task>();
    private Stack<Integer> undoStackInt = new Stack<Integer>();
    private Stack<Task> redoStackTask = new Stack<Task>();
    private Stack<Integer> redoStackInt = new Stack<Integer>();

    @Override
    public void execute(List<Task> list, Object index) {
        int taskIndex = (int) index;
        undoStackTask.push(list.get(taskIndex));
        undoStackInt.push(taskIndex);
        executeUpdate(taskIndex, list);
    }

    public void executeUpdate(int taskIndex, List<Task> list) {
        Task newTask = list.remove(list.size() - 1);
        list.set(taskIndex, checkAttributes(newTask, taskIndex, list));
    }
    
    private Task checkAttributes(Task updatedTask, int taskIndex, List<Task> list) {
        if (updatedTask.getStartDate() == LocalDate.MIN) {
            updatedTask.setStartDate(list.get(taskIndex).getStartDate());
            updatedTask.setEndDate(list.get(taskIndex).getDueDate()); 
        } 
        return updatedTask;
    }
    
    @Override
    public void undo(ArrayList<Task> list) {
        int undoIndex = undoStackInt.pop();
        Task undoTask = undoStackTask.pop();
        redoStackInt.push(undoIndex);
        redoStackTask.push(list.get(undoIndex));
        list.set(undoIndex, undoTask);
    }

    @Override
    public void redo(ArrayList<Task> list) {
        int redoIndex = redoStackInt.pop();
        Task redoTask = redoStackTask.pop();
        undoStackInt.push(redoIndex);
        undoStackTask.push(list.get(redoIndex));
        list.set(redoIndex, redoTask);
    }

}
