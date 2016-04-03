package logic;

import model.RecurringTask;
import model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
/*import java.util.logging.Level;
import java.util.logging.Logger;*/

public class SkipRecurTask<E> implements Command<Object> {
    //private static Logger loggerSkip = Logger.getLogger("log");
    private Stack<model.RecurringTask> undoRecurStack = new Stack<model.RecurringTask>();
    private Stack<Integer> undoStackInt = new Stack<Integer>();
    private Stack<model.RecurringTask> redoRecurStack = new Stack<model.RecurringTask>();
    private Stack<Integer> redoStackInt = new Stack<Integer>();

    @Override
    public void execute(List<Task> list, Object op) {
        executeSkip(list, (int) op);
    }

    private void executeSkip(List<Task> list, int index) {
        RecurringTask recurringTask = (RecurringTask) list.get(index);
        undoRecurStack.push(clone(recurringTask));
        undoStackInt.push(index);
        moveDate(recurringTask);
    }
    
    private void moveDate(RecurringTask task) {
        task.adjustDate();
        if (task.getRecurType().equals("week") || task.getRecurType().equals("weeks")) {
            task.setStartDate(task.getStartDate().plusWeeks(task.getNumberToRecur()));
            task.setEndDate(task.getDueDate().plusWeeks(task.getNumberToRecur()));
        } else {
            task.setStartDate(task.getStartDate().plusDays(task.getNumberToRecur()));
            task.setEndDate(task.getDueDate().plusDays(task.getNumberToRecur()));
        }
    }
    
    @Override
    public void undo(ArrayList<Task> list) {
        int undoIndex = undoStackInt.pop();
        RecurringTask undoRecurTask = undoRecurStack.pop();
        redoStackInt.push(undoIndex);
        redoRecurStack.push(clone((RecurringTask) list.get(undoIndex)));
        list.set(undoIndex, undoRecurTask);

    }

    @Override
    public void redo(ArrayList<Task> list) {
        int redoIndex = redoStackInt.pop();
        Task redoRecurTask = redoRecurStack.pop();
        undoStackInt.push(redoIndex);
        undoRecurStack.push(clone((RecurringTask) list.get(redoIndex)));
        list.set(redoIndex, redoRecurTask);
    }
    
    private RecurringTask clone(RecurringTask recurringTask) {
        RecurringTask clone = new RecurringTask(recurringTask.getName(), recurringTask.getStartDate(), 
                recurringTask.getDueDate(), recurringTask.getRecurType(), recurringTask.getStartTime(), 
                recurringTask.getEndTime(), recurringTask.getNumberToRecur());
        return clone;
    }

}
