package logic;
//@@author A0130949

import model.RecurringTask;
import model.Task;
import model.RecurringTask;
import java.time.LocalDate;
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
        Task newTask = list.remove(list.size() - 1);
        Task updatedTask = checkAttributes(newTask, taskIndex, list);
        list.set(taskIndex, updatedTask);
        Collections.sort(list);
        undoStackInt.push(findTask(updatedTask.getId(), (ArrayList<Task>) list));
    }

    private Task checkAttributes(Task newTask, int taskIndex, List<Task> list) {
        if (list.get(taskIndex) instanceof RecurringTask) {
            return checkAttributesForRecurringTask(newTask, taskIndex, list);
        } else {
            return checkAttributesForTask(newTask, taskIndex, list);
        }
    }

    private RecurringTask checkAttributesForRecurringTask(Task updatedTask, int taskIndex, List<Task> list) {
        if (updatedTask.getStartDate().equals(LocalDate.MAX)
                && !(list.get(taskIndex).getStartDate().equals(LocalDate.MAX))) {
            updatedTask.setStartDate(list.get(taskIndex).getStartDate());
        }

        if (updatedTask.getDueDate().equals(LocalDate.MAX)
                && !(list.get(taskIndex).getDueDate().equals(LocalDate.MAX))) {
            updatedTask.setEndDate(list.get(taskIndex).getDueDate());
        }

        if (updatedTask.getName().isEmpty() && !list.get(taskIndex).getName().isEmpty()) {
            updatedTask.setName(list.get(taskIndex).getName());
        }

        if (updatedTask.getStartTime() == null && list.get(taskIndex).getStartTime() != null) {
            updatedTask.setStartTime(list.get(taskIndex).getStartTime());
        }

        if (updatedTask.getEndTime() == null && list.get(taskIndex).getEndTime() != null) {
            updatedTask.setEndTime(list.get(taskIndex).getEndTime());
        }

        return transferAttributes(updatedTask, taskIndex, list);
    }

    private RecurringTask transferAttributes(Task updatedTask, int index, List<Task> list) {
        RecurringTask recurringTask = new RecurringTask(updatedTask.getName(), updatedTask.getStartDate(),
                updatedTask.getDueDate(), ((RecurringTask) list.get(index)).getRecurType(), updatedTask.getStartTime(),
                updatedTask.getEndTime(), ((RecurringTask) list.get(index)).getNumberToRecur());
        return recurringTask;
    }

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

        if (updatedTask.getStartTime() == null && list.get(taskIndex).getStartTime() != null) {
            updatedTask.setStartTime(list.get(taskIndex).getStartTime());
        }

        if (updatedTask.getEndTime() == null && list.get(taskIndex).getEndTime() != null) {
            updatedTask.setEndTime(list.get(taskIndex).getEndTime());
        }

        return updatedTask;
    }

    @Override
    public void undo(ArrayList<Task> list) {
        int undoIndex = undoStackInt.pop();
        Task undoTask = undoStackTask.pop();
        redoStackTask.push(list.get(undoIndex));
        list.set(undoIndex, undoTask);
        Collections.sort(list);
        redoStackInt.push(findTask(undoTask.getId(), list));
    }

    @Override
    public void redo(ArrayList<Task> list) {
        int redoIndex = redoStackInt.pop();
        Task redoTask = redoStackTask.pop();
        undoStackTask.push(list.get(redoIndex));
        list.set(redoIndex, redoTask);
        Collections.sort(list);
        undoStackInt.push(findTask(redoTask.getId(), list));
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
