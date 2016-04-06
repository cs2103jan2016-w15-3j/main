package logic;
//@@author A0130949
import model.Task;

import java.util.ArrayList;
import java.util.List;

public class Search<E> implements Command<Object> {
    // For dear Yong Hao
    // http://www.tutorialspoint.com/design_pattern/filter_pattern.htm

    @Override
    public void execute(List<Task> list, Object task) {

    }

    @Override
    public void undo(ArrayList<Task> list) {
        // TODO Auto-generated method stub

    }

    @Override
    public void redo(ArrayList<Task> list) {
        // TODO Auto-generated method stub

    }

    @Override
    public int findTask(Task task, ArrayList<Task> list) {
        // TODO Auto-generated method stub
        return 0;
    }
}
