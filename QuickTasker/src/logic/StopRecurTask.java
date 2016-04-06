package logic;
//@@author A0130949
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import model.RecurringTask;
import model.Task;

public class StopRecurTask<E> implements Command<Object>{
    private Stack<RecurringTask> undoRecurStack = new Stack<RecurringTask>();
    private Stack<Integer> undoStackInt = new Stack<Integer>();
    private Stack<RecurringTask> redoRecurStack = new Stack<RecurringTask>();
    private Stack<Integer> redoStackInt = new Stack<Integer>();
    
    @Override
    public void execute(List<Task> list, Object op) {
        int index = (int) op;
        if (list.get(index) instanceof RecurringTask) {
            RecurringTask recurringTask = (RecurringTask) list.get(index);
            undoRecurStack.push(clone(recurringTask));
            undoStackInt.push(index);
            list.set(index, recurringTask.stopRecurring());
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

    @Override
    public int findTask(Task task, ArrayList<Task> list) {
        // TODO Auto-generated method stub
        return 0;
    }

}