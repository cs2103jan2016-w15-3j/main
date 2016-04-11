package logic;
//@@author A0130949Y

import model.RecurringTask;
import model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MarkTask<E> extends SkipRecurTask<E> implements Command<Object> {
    private static Logger loggerMark = Logger.getLogger("log");
    ArrayList<Task> archivedList;
    private Stack<Task> undoStack = new Stack<Task>();
    private Stack<Task> redoStack = new Stack<Task>();
    private Stack<String> undoStackId = new Stack<String>();
    private Stack<String> redoStackId = new Stack<String>();
    private static final int OFFSET = 1;

    @Override
    public void execute(List list, Object op) {
        loggerMark.log(Level.INFO, "Init variables for mark");
        archivedList = (ArrayList<Task>) list;
        String taskId = (String) op;
        Task archivedTask = archivedList.get(archivedList.size() - OFFSET);
        assert (archivedTask != null);
        loggerMark.log(Level.INFO, "Finish init of variables, Start the stack manipulation");
        executeArchive(taskId, archivedTask);
        loggerMark.log(Level.INFO, "End");
    }

    private void executeArchive(String taskId, Task archivedTask) {
        if (archivedTask instanceof RecurringTask) {
            undoStackId.push(taskId);
        }
        undoStack.push(archivedTask);
    }

    @Override
    // puts task back to list
    public void undo(ArrayList<Task> list) {
        assert (archivedList.size() > 0);
        loggerMark.log(Level.INFO, "Start undo process for mark");
        try {
            archivedList.remove(archivedList.size() - OFFSET);
            Task undoTask = undoStack.pop();
            if (undoTask instanceof RecurringTask) {
                undoForRecurringTask(list);
            } else {
                list.add(undoTask);
            }
            redoStack.push(undoTask);
        } catch (Exception e) {
            loggerMark.log(Level.WARNING, "Error in undo-ing for mark");
        }
        loggerMark.log(Level.INFO, "End");
    }

    // return the recurring task from archived as well as to shift the date back
    private void undoForRecurringTask(ArrayList<Task> list) {
        String taskId = undoStackId.pop();
        redoStackId.push(taskId);
        int positionOfRecurringTask = findTask(taskId, list);
        moveDateBackward((RecurringTask) list.get(positionOfRecurringTask));
    }

    @Override
    // puts task back to ARCHIVED list
    public void redo(ArrayList list) {
        loggerMark.log(Level.INFO, "Start redo process for mark");
        try {
            Task redoTask = redoStack.pop();
            undoStack.push(redoTask);
            archivedList.add(redoTask);
            if (redoTask instanceof RecurringTask) {
                redoForRecurringTask(list);
            } else {
                list.remove(findTask(redoTask.getId(), list));
            }
        } catch (Exception e) {
            loggerMark.log(Level.WARNING, "Error in redo-ing for mark");
        }
        loggerMark.log(Level.INFO, "End");
    }

    // backs the recurring task back into archived list and shift the date forward
    private void redoForRecurringTask(ArrayList list) {
        String taskId = redoStackId.pop();
        undoStackId.push(taskId);
        int positionOfRecurringTask = findTask(taskId, list);
        undoStack.push((RecurringTask) list.get(positionOfRecurringTask));
        moveDateForward((RecurringTask) list.get(positionOfRecurringTask));
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
