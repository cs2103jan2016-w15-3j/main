package logic;

import model.RecurringTask;
import model.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
/*import java.util.logging.Level;
import java.util.logging.Logger;*/

public class SkipRecurTask<E> implements Command<Object> {
    //private static Logger loggerSkip = Logger.getLogger("log");
    private Stack<Integer> undoStackInt = new Stack<Integer>();
    private Stack<Integer> redoStackInt = new Stack<Integer>();

    @Override
    public void execute(List<Task> list, Object op) {
        executeSkip(list, (int) op);
    }

    private void executeSkip(List<Task> list, int index) {
        RecurringTask recurringTask = (RecurringTask) list.get(index);
        moveDateForward(recurringTask);
        Collections.sort(list);
        undoStackInt.push(findTask(recurringTask.getId(), (ArrayList<Task>) list));
    }

    protected void moveDateForward(RecurringTask task) {
        task.adjustDate();
        if (task.getRecurType().equals("week") || task.getRecurType().equals("weeks")) {
            task.setStartDate(task.getStartDate().plusWeeks(task.getNumberToRecur()));
            task.setEndDate(task.getDueDate().plusWeeks(task.getNumberToRecur()));
        } else {
            task.setStartDate(task.getStartDate().plusDays(task.getNumberToRecur()));
            task.setEndDate(task.getDueDate().plusDays(task.getNumberToRecur()));
        }
    }

    protected void moveDateBackward(RecurringTask task) {
        task.adjustDate();
        if (task.getRecurType().equals("week") || task.getRecurType().equals("weeks")) {
            task.setStartDate(task.getStartDate().minusWeeks(task.getNumberToRecur()));
            task.setEndDate(task.getDueDate().minusWeeks(task.getNumberToRecur()));
        } else {
            task.setStartDate(task.getStartDate().minusDays(task.getNumberToRecur()));
            task.setEndDate(task.getDueDate().minusDays(task.getNumberToRecur()));
        }
    }

    @Override
    public void undo(ArrayList<Task> list) {
        int undoIndex = undoStackInt.pop();
        RecurringTask recurringTask = (RecurringTask) list.get(undoIndex);
        moveDateBackward((RecurringTask) list.get(undoIndex));
        Collections.sort(list);
        redoStackInt.push(findTask(recurringTask.getId(), list));

    }

    @Override
    public void redo(ArrayList<Task> list) {
        int redoIndex = redoStackInt.pop();
        RecurringTask recurringTask = (RecurringTask) list.get(redoIndex);
        moveDateForward(recurringTask);
        Collections.sort(list);
        undoStackInt.push(findTask(recurringTask.getId(), list));
    }
    
/*    @Override
    public int findingTask(Task task, ArrayList<Task> list) {
        int position = -1;
        if (task instanceof RecurringTask) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) instanceof RecurringTask) {
                    if (((RecurringTask)list.get(i)).equals(task)) {
                        position = i;
                        break;
                    }
                }
            }
        } 
        return position;
    }*/

    @Override
    public int findTask(String id, ArrayList<Task> list) {
        int position = -1;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(id)) {
                position = i;
            }
        }
        return position;
    }
}
