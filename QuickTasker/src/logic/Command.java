package logic;

import model.Task;

import java.util.ArrayList;
import java.util.List;

//@@author A0130949

public interface Command<E> {
    void execute(List<Task> list, E op);

    void undo(ArrayList<Task> list);

    void redo(ArrayList<Task> list);
    
    int findTask(Task task, ArrayList<Task> list);
}
