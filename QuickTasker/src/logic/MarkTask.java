package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import model.RecurringTask;
import model.Task;

public class MarkTask<E> implements Command {
    ArrayList<Task> archivedList;
    private Stack<Task> undoStack = new Stack<Task>();
    private Stack<Task> redoStack = new Stack<Task>();
    private Stack<Integer> undoStackInt = new Stack<Integer>();
    private Stack<Integer> redoStackInt = new Stack<Integer>();
    
    @Override
    public void execute(List list, Object op) {
        archivedList = (ArrayList<Task>) list;
        int index = (int) op;
        if (archivedList.get(archivedList.size() - 1) instanceof RecurringTask) {
            undoStackInt.push(index);
        }
        undoStack.push(archivedList.get(archivedList.size() - 1));
    }

    @Override
    public void undo(ArrayList list) {
        archivedList.remove(archivedList.size() - 1);
        Task undoTask = undoStack.pop();
        if (undoTask instanceof RecurringTask) {
            int index = undoStackInt.pop();
            redoStackInt.push(index);
            redoStack.push((RecurringTask) list.get(index));
            list.set(index, undoTask);
        } else {
            redoStack.push(undoTask);
            list.add(undoTask);
        }
    }

    @Override
    public void redo(ArrayList list) {
        Task redoTask = redoStack.pop();
        undoStack.push(redoTask);
        if (redoTask instanceof RecurringTask) {
            int index = redoStackInt.pop();
            undoStackInt.push(index);
            undoStack.push((RecurringTask) list.get(index));
            list.set(index, redoTask);
        } else {
            list.remove(findTask(redoTask, list));
            archivedList.add(redoTask);
        }
    }
    
    private int findTask(Task task, ArrayList<Task> list) {
        int position = -1;
        if (task instanceof RecurringTask) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) instanceof RecurringTask) {
                    if (((RecurringTask)list.get(i)).equals(task)) {
                        position = i;
                        break;
                    }
                }
            }
        } else {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).equals(task)) {
                    position = i;
                    break;
                }
            }
        }
        return position;
    }

}
