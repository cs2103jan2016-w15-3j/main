package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import model.Task;
import parser.UserInputParser;

public class UpdateTask<E> implements Command<Object> {
    private Stack<Task> undoStackTask = new Stack<Task>(); 
    private Stack<Integer> undoStackInt = new Stack<Integer>();
    
    @Override
    public void execute(List<Task> list, Object index) {
        int taskIndex = (int) index;
        undoStackTask.push(list.get(taskIndex));
        undoStackInt.push(taskIndex);
        executeUpdate(taskIndex, list);
    }
    
    
    public void executeUpdate(int taskIndex, List<Task> list) {
        Task newTask = list.remove(list.size() - 1);
        list.set(taskIndex, newTask);
    }


    @Override
    public void undo(ArrayList<Task> list) {
        // TODO Auto-generated method stub
        list.set(undoStackInt.pop(), undoStackTask.pop());
    }

}
