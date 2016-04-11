package logic;
//@@author A0130949Y

import model.RecurringTask;
import model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class StopRecurTask<E> implements Command<Object> {
    private Stack<Task> undoRecurStack = new Stack<Task>();
    private Stack<String> undoStackId = new Stack<String>();
    private Stack<Task> redoRecurStack = new Stack<Task>();
    private Stack<String> redoStackId = new Stack<String>();

    @Override
    public void execute(List<Task> list, Object index) {
        int taskIndex = (int) index;
        if (list.get(taskIndex) instanceof RecurringTask) {
            executeStopRecurring(list, taskIndex);
        }
    }

    private void executeStopRecurring(List<Task> list, int index) {
        RecurringTask recurringTask = (RecurringTask) list.get(index);
        undoRecurStack.push(recurringTask);
        Task task = recurringTask.stopRecurring();
        undoStackId.push(task.getId());
        list.set(index, task);
    }

    @Override
    public void undo(ArrayList<Task> list) {
        Task previousTask = undoStopRecurTask(list);
        list.add(previousTask);
    }

    private Task undoStopRecurTask(ArrayList<Task> list) {
        String undoTaskId = undoStackId.pop();
        Task previousTask = undoRecurStack.pop();
        Task taskToBeReomved = list.remove(findTask(undoTaskId, list));
        redoRecurStack.push(taskToBeReomved);
        redoStackId.push(undoTaskId);
        return previousTask;
    }

    @Override
    public void redo(ArrayList<Task> list) {
        Task previousTask = redoStopRecurTask(list);
        list.add(previousTask);
    }

    private Task redoStopRecurTask(ArrayList<Task> list) {
        String redoTaskId = redoStackId.pop();
        Task previousTask = redoRecurStack.pop();
        undoStackId.push(redoTaskId);
        undoRecurStack.push(list.remove(findTask(redoTaskId, list)));
        return previousTask;
    }

    private RecurringTask clone(RecurringTask recurringTask) {
        RecurringTask clone = new RecurringTask(recurringTask.getName(), recurringTask.getStartDate(),
                recurringTask.getDueDate(), recurringTask.getRecurType(), recurringTask.getStartTime(),
                recurringTask.getEndTime(), recurringTask.getNumberToRecur());
        return clone;
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
