package logic;
//@@author A0130949Y

import model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeleteTask<E> implements Command<Object> {
    private static Logger loggerDelete = Logger.getLogger("log");
    private static Stack<Task> undoStackTask = new Stack<Task>();
    private static Stack<Task> redoTaskStack = new Stack<Task>();

    @Override
    // deletes a task from that index. exceptions are thrown for Junit tests.
    public void execute(List<Task> list, Object index) {
        loggerDelete.log(Level.INFO, "Start logging for delete");
        try {
            int taskIndex = (int) index;
            assert (taskIndex >= 0);
            executeDelete(list, taskIndex);
        } catch (AssertionError e) {
            loggerDelete.log(Level.WARNING, "Index is negative");
            throw new IllegalArgumentException();
        } catch (NumberFormatException e) {
            loggerDelete.log(Level.WARNING, "Index is not a number");
            throw new NumberFormatException();
        } catch (ArrayIndexOutOfBoundsException e) {
            loggerDelete.log(Level.WARNING, "Index is out of range");
            throw new ArrayIndexOutOfBoundsException();
        }
        loggerDelete.log(Level.INFO, "End");
    }

    private void executeDelete(List<Task> list, int index) {
        undoStackTask.push(list.get(index));
        list.remove(index);
    }

    @Override
    // takes out the deleted task and add back in
    public void undo(ArrayList<Task> list) {
        loggerDelete.log(Level.INFO, "Start undo process for deleting");
        try {
            Task deletedTask = undoStackTask.pop();
            redoTaskStack.push(deletedTask);
            list.add(deletedTask);
        } catch (Exception e) {
            loggerDelete.log(Level.WARNING, "Error in undo-ing the delete", e);
        }
        loggerDelete.log(Level.INFO, "End");
    }

    @Override
    // takes out the previous-deleted-but-added-back-in task from the redoTaskStack and remove it from the list
    public void redo(ArrayList<Task> list) {
        loggerDelete.log(Level.INFO, "Start redo process for deleting");
        try {
            Task redoTask = redoTaskStack.pop();
            int indexToRedo = findTask(redoTask.getId(), list);
            undoStackTask.push(redoTask);
            list.remove(indexToRedo);
        } catch (Exception e) {
            loggerDelete.log(Level.WARNING, "Error in redo-ing the delete", e);
        }
        loggerDelete.log(Level.INFO, "End");
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