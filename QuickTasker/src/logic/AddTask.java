package logic;
//@@author A0130949
import model.Task;
import org.ocpsoft.prettytime.shade.org.apache.commons.lang.NullArgumentException;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;



public class AddTask<E> implements Command<Object> {
    private static Logger loggerAdd = Logger.getLogger("log");
    private Stack<Task> undoTaskStack = new Stack<Task>();
    private Stack<Integer> undoStackInt = new Stack<Integer>();
    private Stack<Task> redoTaskStack = new Stack<Task>();
    private Stack<Integer> redoStackInt = new Stack<Integer>();

    @Override
    /*  adds the task into the list. task cannot be null. throwing NullArgumentException is for Junit test
        uncomment the throwing of exception when running tests */
    public void execute(List<Task> list,
            Object task) {
        loggerAdd.log(Level.INFO, "Start adding task");
        try {
            assert (task != null);
            executeAddTask(list, (Task) task);
        } catch (AssertionError e) {
            loggerAdd.log(Level.WARNING, "Task is null");
            throw new NullArgumentException("For add test");
        }
        loggerAdd.log(Level.INFO, "End");
    }

    private void executeAddTask(List<Task> list, Task task) {
        list.add(task);
        undoTaskStack.push(task);
        Collections.sort(list);
        int index = findTask(task.getId(), (ArrayList<Task>) list);
        undoStackInt.push(index);
    }

    @Override
    public void undo(ArrayList<Task> list) {
        loggerAdd.log(Level.INFO, "START ADD UNDO PROCESS");
        try {
            undoAdding(list);
            loggerAdd.log(Level.INFO, "UNDO COMPELETED");
        } catch (EmptyStackException e) {
            loggerAdd.log(Level.WARNING, "ERROR, ADD UNDO HAS AN ERROR");
        } catch (Exception e) {
            System.out.println(e);
        }
        loggerAdd.log(Level.INFO, "END");
    }

    // basically removing the added task
    private void undoAdding(ArrayList<Task> list) {
        Task undoTask = undoTaskStack.pop();
        int index = undoStackInt.pop();
        addRedoStack(undoTask, index);
        list.remove(index);
    }

    private void addRedoStack(Task undoTask, int index) {
        redoTaskStack.push(undoTask);
        redoStackInt.push(index);
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

    @Override
    public void redo(ArrayList<Task> list) {
        loggerAdd.log(Level.INFO, "START REDO PROCESS");
        try {
            redoAdding(list);
        } catch (EmptyStackException e) {
            loggerAdd.log(Level.WARNING, "ERROR, ADD REDO HAS AN ERROR");
        } catch (Exception e) {
            System.out.println(e);
        }
        loggerAdd.log(Level.INFO, "FINISH REDO PROCESS");
    }

    // basically add the task again
    private void redoAdding(ArrayList<Task> list) {
        Task redoTask = redoTaskStack.pop();
        int index = redoStackInt.pop();
        addUndoStack(redoTask, index);
        list.add(index, redoTask);
    }

    private void addUndoStack(Task redoTask, int index) {
        undoTaskStack.push(redoTask);
        undoStackInt.push(index);
    }
}
