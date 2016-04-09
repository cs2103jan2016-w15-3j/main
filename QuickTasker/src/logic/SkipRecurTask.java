package logic;

import model.RecurringTask;
import model.Task;

import java.time.LocalDate;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SkipRecurTask<E> implements Command<Object> {
    private static Logger loggerSkip = Logger.getLogger("log");
    private Stack<Integer> undoStackInt = new Stack<Integer>();
    private Stack<Integer> redoStackInt = new Stack<Integer>();

    @Override
    public void execute(List<Task> list, Object op) {
        executeSkip(list, (int) op);
    }

    private void executeSkip(List<Task> list, int index) {
        loggerSkip.log(Level.INFO, "Start skipping");
        try {
            RecurringTask recurringTask = (RecurringTask) list.get(index);
            moveDateForward(recurringTask);
            Collections.sort(list);
            undoStackInt.push(findTask(recurringTask.getId(), (ArrayList<Task>) list));
        } catch (IndexOutOfBoundsException e) {
            loggerSkip.log(Level.WARNING, "Out of bound index");
            throw new IndexOutOfBoundsException();
        } catch (NumberFormatException e) {
            loggerSkip.log(Level.WARNING, "Invalid index");
            throw new NumberFormatException();
        }
        loggerSkip.log(Level.INFO, "End");
    }

    protected void moveDateBackward(RecurringTask task) {
        assert (task != null);
        task.adjustDate();
        if (task.getRecurType().equals("week") || task.getRecurType().equals("weeks")) {
            adjustDatesBackwardForWeeks(task);
        } else if (task.getRecurType().equals("day") || task.getRecurType().equals("days")){
            adjustDatesBackwardForDays(task);
        } else if (task.getRecurType().equals("month") || task.getRecurType().equals("months")) {
            adjustDatesBackwardForMonths(task);
        } else {
            adjustDatesBackwardForYears(task);
        }
    }

    protected void moveDateForward(RecurringTask task) {
        assert (task != null);
        task.adjustDate();
        if (task.getRecurType().equals("week") || task.getRecurType().equals("weeks")) {
            adjustDatesForwardForWeeks(task);
        } else if (task.getRecurType().equals("day") || task.getRecurType().equals("days")){
            adjustDatesForwardForDays(task);
        } else if (task.getRecurType().equals("month") || task.getRecurType().equals("months")) {
            adjustDatesForwardForMonths(task);
        } else {
            adjustDatesForwardForYears(task);
        }
    }

    private void adjustDatesForwardForWeeks(RecurringTask task) {
        if (!task.isDueDateEmpty()) {
            task.setEndDate(task.getDueDate().plusWeeks(task.getNumberToRecur()));
        }

        if (!task.isStartDateEmpty()) {
            task.setStartDate(task.getStartDate().plusWeeks(task.getNumberToRecur()));
        }
    }

    private void adjustDatesForwardForDays(RecurringTask task) {
        if (!task.isDueDateEmpty()) {
            task.setEndDate(task.getDueDate().plusDays(task.getNumberToRecur()));
        }

        if (!task.isStartDateEmpty()) {
            task.setStartDate(task.getStartDate().plusDays(task.getNumberToRecur()));
        }
    }

    private void adjustDatesForwardForMonths(RecurringTask task) {
        if (!task.isDueDateEmpty()) {
            task.setEndDate(task.getDueDate().plusMonths(task.getNumberToRecur()));
        }

        if (!task.isStartDateEmpty()) {
            task.setStartDate(task.getStartDate().plusMonths(task.getNumberToRecur()));
        }
    }

    private void adjustDatesForwardForYears(RecurringTask task) {
        if (!task.isDueDateEmpty()) {
            task.setEndDate(task.getDueDate().plusYears(task.getNumberToRecur()));
        }

        if (!task.isStartDateEmpty()) {
            task.setStartDate(task.getStartDate().plusYears(task.getNumberToRecur()));
        }
    }

    private void adjustDatesBackwardForWeeks(RecurringTask task) {
        if (!task.isDueDateEmpty()) {
            task.setEndDate(task.getDueDate().minusWeeks(task.getNumberToRecur()));
        }

        if (!task.isStartDateEmpty()) {
            task.setStartDate(task.getStartDate().minusWeeks(task.getNumberToRecur()));
        }
    }

    private void adjustDatesBackwardForDays(RecurringTask task) {
        if (!task.isDueDateEmpty()) {
            task.setEndDate(task.getDueDate().minusDays(task.getNumberToRecur()));
        }

        if (!task.isStartDateEmpty()) {
            task.setStartDate(task.getStartDate().minusDays(task.getNumberToRecur()));
        }
    }

    private void adjustDatesBackwardForMonths(RecurringTask task) {
        if (!task.isDueDateEmpty()) {
            task.setEndDate(task.getDueDate().minusMonths(task.getNumberToRecur()));
        }

        if (!task.isStartDateEmpty()) {
            task.setStartDate(task.getStartDate().minusMonths(task.getNumberToRecur()));
        }
    }

    private void adjustDatesBackwardForYears(RecurringTask task) {
        if (!task.isDueDateEmpty()) {
            task.setEndDate(task.getDueDate().minusYears(task.getNumberToRecur()));
        }

        if (!task.isStartDateEmpty()) {
            task.setStartDate(task.getStartDate().minusYears(task.getNumberToRecur()));
        }
    }

    @Override
    public void undo(ArrayList<Task> list) {
        loggerSkip.log(Level.INFO, "Start undo process for skip");
        try {
            undoSkip(list);
        } catch (EmptyStackException e) {
            loggerSkip.log(Level.WARNING, "Stack is empty");
        } catch (IndexOutOfBoundsException e) {
            loggerSkip.log(Level.WARNING, "Index is out of range");
        }
        loggerSkip.log(Level.INFO, "End");
    }

    // shifts back the date for recurring task at undo index
    private void undoSkip(ArrayList<Task> list) {
        int undoIndex = undoStackInt.pop();
        RecurringTask recurringTask = (RecurringTask) list.get(undoIndex);
        moveDateBackward((RecurringTask) list.get(undoIndex));
        Collections.sort(list);
        redoStackInt.push(findTask(recurringTask.getId(), list));
    }

    @Override
    public void redo(ArrayList<Task> list) {
        loggerSkip.log(Level.INFO, "Start redo process for skip");
        try {
            redoOperation(list);
        } catch (EmptyStackException e) {
            loggerSkip.log(Level.WARNING, "Redo stack is empty");
        } catch (IndexOutOfBoundsException e) {
            loggerSkip.log(Level.WARNING, "Index is out of range");
        }
        loggerSkip.log(Level.INFO, "End");
    }

    // shifts forward the date for the recurring task at redoIndex
    private void redoOperation(ArrayList<Task> list) {
        int redoIndex = redoStackInt.pop();
        RecurringTask recurringTask = (RecurringTask) list.get(redoIndex);
        moveDateForward(recurringTask);
        Collections.sort(list);
        undoStackInt.push(findTask(recurringTask.getId(), list));
    }

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
