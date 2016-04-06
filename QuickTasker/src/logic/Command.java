package logic;

import model.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Author A0130949 Soh Yonghao
 * <p>
 * .
 */

public interface Command<E> {
    void execute(List<Task> list, E op);

    void undo(ArrayList<Task> list);

    void redo(ArrayList<Task> list);

    int findTask(String id, ArrayList<Task> list);
}
