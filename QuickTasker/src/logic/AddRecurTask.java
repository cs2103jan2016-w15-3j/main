package logic;

import model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddRecurTask<RecurringTask> implements Command {
    private static final String NOTHING_TO_ADD = "Cannot add nothing";
    private static Logger loggerAdd = Logger.getLogger("log");
    private Stack<Task> undoStack = new Stack<Task>();
    private Stack<Task> redoStack = new Stack<Task>();

    @Override
    public void execute(List list, Object op) {
        assert (list.size() >= 0);
        undoStack.push((model.RecurringTask) op);
        executeAdd(list, (model.RecurringTask) op);
    }

    private void executeAdd(List<Task> list, model.RecurringTask op) {
        loggerAdd.log(Level.INFO, "ADD RECUR TASK");
        try {
            if (op != null) {
                list.add(op);
            } else {
                System.out.println(NOTHING_TO_ADD);
            }
        } catch (Exception e) {
            loggerAdd.log(Level.WARNING, "ERROR", e);
        }
        loggerAdd.log(Level.INFO, "END");
    }

    @Override
    public void undo(ArrayList list) {
        // TODO Auto-generated method stub

    }

    @Override
    public void redo(ArrayList list) {
        // TODO Auto-generated method stub

    }

}
