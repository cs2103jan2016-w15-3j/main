package logic;
//@@author A0130949
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.Task;

public class Sort<E> implements Command {

    @Override
    public void execute(List list, Object op) {
        System.out.println("really sorting now");
        Collections.sort(list);
        System.out.println("finish sorting");
    }

    @Override
    public void undo(ArrayList list) {
        // TODO Auto-generated method stub

    }

    @Override
    public void redo(ArrayList list) {
        // TODO Auto-generated method stub

    }

    @Override
    public int findTask(Task task, ArrayList list) {
        // TODO Auto-generated method stub
        return 0;
    }

}
