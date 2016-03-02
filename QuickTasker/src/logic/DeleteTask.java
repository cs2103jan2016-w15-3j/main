package logic;

import java.util.List;

import model.Task;
/**
 * 
 * Author A0130949
 * Soh Yonghao
 * 
 *. */

public class DeleteTask<E> implements Command {

    @Override
    public void execute(List list, Object task) {
        executeDelete(list, (int) task);

    }

    private void executeDelete(List<Task> list, int index) {
        list.remove(index);
    }
}