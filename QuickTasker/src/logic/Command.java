package logic;
//@@author A0130949Y

import model.Task;

import java.util.ArrayList;
import java.util.List;

public interface Command<E> {
    void execute(List<Task> list, E op);

    void undo(ArrayList<Task> list);

    void redo(ArrayList<Task> list);

    int findTask(String id, ArrayList<Task> list);
}
