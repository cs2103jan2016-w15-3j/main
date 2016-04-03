package logic;

import model.RecurringTask;
import model.Task;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Author A0130949 Soh Yonghao
 */

public class AddTask<E> implements Command<Object> {
    private static Logger loggerAdd = Logger.getLogger("log");
    private Stack<Task> undoTaskStack = new Stack<Task>();
    private Stack<Integer> undoStackInt = new Stack<Integer>();
    private Stack<Task> redoTaskStack = new Stack<Task>();
    private Stack<Integer> redoStackInt = new Stack<Integer>();

    @Override
    public void execute(List<Task> list, Object task) {
        assert (list.size() >= 0);
        executeAddTask(list, (Task) task);
    }

    private void executeAddTask(List<Task> list, Task task) {
        loggerAdd.log(Level.INFO, "START ADDING TASK");
        try {
            assert task != null;
            int size = list.size();
            list.add(task);
            if (task instanceof RecurringTask) {
                undoTaskStack.push(cloneRecurringTask((RecurringTask) task));
            } else {
                undoTaskStack.push(cloneTask(task));           
            }
            Collections.sort(list);
            int index = findTask(task, (ArrayList<Task>) list);
            undoStackInt.push(index);
            assert ((size + 1) == list.size());
        } catch (Exception e) {
            loggerAdd.log(Level.WARNING, "ERROR AT ADDING TASK", e);
        }
        loggerAdd.log(Level.INFO, "END");
    }

    @Override
    public void undo(ArrayList<Task> list) {
        loggerAdd.log(Level.INFO, "START UNDO PROCESS");
        try {
            Task undoTask = undoTaskStack.pop();
            int index = undoStackInt.pop();
            addRedoStack(undoTask, index);
            list.remove(index);
            loggerAdd.log(Level.INFO, "UNDO COMPELETED");
        } catch (Exception e) {
            loggerAdd.log(Level.WARNING, "ERROR, THERES NOTHING IN THE UNDOSTACK", e);
        }
        loggerAdd.log(Level.INFO, "END");
    }

    private void addRedoStack(Task undoTask, int index) {
        if (undoTask instanceof RecurringTask) {
            redoTaskStack.push(cloneRecurringTask((RecurringTask) undoTask));
            redoStackInt.push(index);
        } else {
            redoTaskStack.push(cloneTask(undoTask));
            redoStackInt.push(index);                
        }
    }

    private int findTask(Task task, ArrayList<Task> list) {
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
        } else {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).equals(task)) {
                    position = i;
                    break;
                }
            }
        }
        return position;
    }

    @Override
    public void redo(ArrayList<Task> list) {
        loggerAdd.log(Level.INFO, "START REDO PROCESS");
        try {
            Task redoTask = redoTaskStack.pop();
            int index = redoStackInt.pop();
            addUndoStack(redoTask, index);
            list.add(index, redoTask);
        } catch (Exception e) {
            loggerAdd.log(Level.WARNING, "ERROR, THERES NOTHING IN THE REDOSTACK", e);
        }
        loggerAdd.log(Level.INFO, "FINISH REDO PROCESS");
    }

    private void addUndoStack(Task redoTask, int index) {
        if (redoTask instanceof RecurringTask) {
            undoTaskStack.push(cloneRecurringTask((RecurringTask) redoTask));
            undoStackInt.push(index);
        } else {
            undoTaskStack.push(cloneTask(redoTask));
            undoStackInt.push(index);                
        }
    }
    
    private RecurringTask cloneRecurringTask(RecurringTask recurringTask) {
        RecurringTask clone = new RecurringTask(recurringTask.getName(), recurringTask.getStartDate(), 
                recurringTask.getDueDate(), recurringTask.getRecurType(), recurringTask.getStartTime(), 
                recurringTask.getEndTime(), recurringTask.getNumberToRecur());
        return clone;
    }
    
    private Task cloneTask(Task task) {
        Task clone = new Task(task.getName(), task.getStartDate(), 
                task.getDueDate(), task.getStartTime(), task.getEndTime());
        return clone;
    }
}
