package logic;
//@@author A0130949Y

import model.RecurringTask;
import model.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UpdateTask<E> implements Command<Object> {
    private static Logger loggerUpdate = Logger.getLogger("log");
    private static Stack<Task> undoStackTask = new Stack<Task>();
    private static Stack<Integer> undoStackInt = new Stack<Integer>();
    private static Stack<Task> redoStackTask = new Stack<Task>();
    private static Stack<Integer> redoStackInt = new Stack<Integer>();

    @Override
    public void execute(List<Task> list, Object index) {
        loggerUpdate.log(Level.INFO, "Start updating");
        try {
            int taskIndex = (int) index;
            assert (taskIndex >= 0);
            undoStackTask.push(list.get(taskIndex));
            executeUpdate(taskIndex, list);
            loggerUpdate.log(Level.INFO, "End");
        } catch (AssertionError e) {
            loggerUpdate.log(Level.WARNING, "Cannot update negative index");
            throw new IllegalArgumentException();
        } catch (NumberFormatException e) {
            loggerUpdate.log(Level.WARNING, "Cannot update an index thats not a number");
            throw new NumberFormatException();
        }
    }

    public void executeUpdate(int taskIndex, List<Task> list) {
        final int OFFSET = 1;
        Task newTask = list.remove(list.size() - OFFSET);
        Task updatedTask = checkAttributesForTask(newTask, taskIndex, list);
        updatedTask.setTaskType();
        list.set(taskIndex, updatedTask);
        Collections.sort(list);
        undoStackInt.push(findTask(updatedTask.getId(), (ArrayList<Task>) list));
    }

    // transfer the task attributes to recurring task
    private RecurringTask transferAttributes(Task updatedTask, int index, List<Task> list) {
        RecurringTask recurringTask = new RecurringTask(updatedTask.getName(), updatedTask.getStartDate(),
                updatedTask.getDueDate(), ((RecurringTask) list.get(index)).getRecurType(),
                updatedTask.getStartTime(), updatedTask.getEndTime(),
                ((RecurringTask) list.get(index)).getNumberToRecur());
        return recurringTask;
    }

    // checks which attributes the user wishes to update and update accordingly
    private Task checkAttributesForTask(Task updatedTask, int taskIndex, List<Task> list) {
        if (updatedTask.isStartDateEmpty() && !list.get(taskIndex).isStartDateEmpty()) {
            updatedTask.setStartDate(list.get(taskIndex).getStartDate());
        }

        if (updatedTask.isDueDateEmpty() && !list.get(taskIndex).isDueDateEmpty()) {
            updatedTask.setEndDate(list.get(taskIndex).getDueDate());
        }

        if (updatedTask.getName().isEmpty() && !list.get(taskIndex).getName().isEmpty()) {
            updatedTask.setName(list.get(taskIndex).getName());
        }

        if (updatedTask.isStartTimeEmpty() && !list.get(taskIndex).isStartTimeEmpty()) {
            updatedTask.setStartTime(list.get(taskIndex).getStartTime());
        }

        if (updatedTask.isEndTimeEmpty() && !list.get(taskIndex).isEndTimeEmpty()) {
            updatedTask.setEndTime(list.get(taskIndex).getEndTime());
        }

        if (list.get(taskIndex) instanceof RecurringTask) {
            return transferAttributes(updatedTask, taskIndex, list);
        } else {
            return updatedTask;
        }
    }

    @Override
    public void undo(ArrayList<Task> list) {
        Task undoTask = undoUpdate(list);
        Collections.sort(list);
        redoStackInt.push(findTask(undoTask.getId(), list));
    }

    private Task undoUpdate(ArrayList<Task> list) {
        int undoIndex = undoStackInt.pop();
        Task undoTask = undoStackTask.pop();
        redoStackTask.push(list.get(undoIndex));
        list.set(undoIndex, undoTask);
        return undoTask;
    }

    @Override
    public void redo(ArrayList<Task> list) {
        Task redoTask = redoUpdate(list);
        Collections.sort(list);
        undoStackInt.push(findTask(redoTask.getId(), list));
    }

    private Task redoUpdate(ArrayList<Task> list) {
        int redoIndex = redoStackInt.pop();
        Task redoTask = redoStackTask.pop();
        undoStackTask.push(list.get(redoIndex));
        list.set(redoIndex, redoTask);
        return redoTask;
    }

    @Override
    public int findTask(String id, ArrayList<Task> list) {
        final int INVALID = -1;
        int position = INVALID;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(id)) {
                position = i;
            }
        }
        return position;
    }
}
