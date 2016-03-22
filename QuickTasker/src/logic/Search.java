package logic;

import model.Task;

import java.util.ArrayList;
import java.util.List;

public class Search<E> implements Command<Object> {

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
}
