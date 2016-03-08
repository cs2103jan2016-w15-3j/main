package logic;

import java.util.ArrayList;
import java.util.List;

import model.Task;
/**
 * 
 * Author A0130949
 * Soh Yonghao
 * 
 *. */

public class DisplayTask<E> implements Command<E> {

    @Override
    public void execute(List list, Object task) {
        executeDisplay(list);

    }

    public ArrayList<Task> executeDisplay(List<Task> list) {
        return (ArrayList<Task>) list;
    }

}