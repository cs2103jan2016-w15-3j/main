package logic;

import model.RecurringTask;
import model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MarkTask<E> extends SkipRecurTask<E> implements Command<Object> {
    ArrayList<Task> archivedList;
    private Stack<Task> undoStack = new Stack<Task>();
    private Stack<Task> redoStack = new Stack<Task>();
    private Stack<String> undoStackId = new Stack<String>();
    private Stack<String> redoStackId = new Stack<String>();

    @Override
    public void execute(List list, Object op) {
        archivedList = (ArrayList<Task>) list;
        String taskId = (String) op;
        Task archivedTask = archivedList.get(archivedList.size() - 1);
        if (archivedTask instanceof RecurringTask) {
            undoStackId.push(taskId);
        }
        undoStack.push(archivedTask);
    }

    @Override
    public void undo(ArrayList<Task> list) {
        archivedList.remove(archivedList.size() - 1);
        Task undoTask = undoStack.pop();
        if (undoTask instanceof RecurringTask) {
            String taskId = undoStackId.pop();
            redoStackId.push(taskId);
            redoStack.push((undoTask));
            int positionOfRecurringTask = findTask(taskId, list);
            moveDateBackward((RecurringTask) list.get(positionOfRecurringTask));
        } else {
            redoStack.push(undoTask);
            list.add(undoTask);
        }


    }

    @Override
    public void redo(ArrayList list) {
        Task redoTask = redoStack.pop();
        undoStack.push(redoTask);
        archivedList.add(redoTask);
        if (redoTask instanceof RecurringTask) {
            String taskId = redoStackId.pop();
            undoStackId.push(taskId);
            int positionOfRecurringTask = findTask(taskId, list);
            undoStack.push((RecurringTask) list.get(positionOfRecurringTask));
            moveDateForward((RecurringTask) list.get(positionOfRecurringTask));
        } else {
            list.remove(findTask(redoTask.getId(), list));
        }
    }

    /*    @Override
        public int findingTask(Task task, ArrayList list) {
            int position = -1;
            if (task instanceof RecurringTask) {
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i) instanceof RecurringTask) {
                        if ((list.get(i)).equals(task)) {
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
        }*/
    @Override
    public int findTask(String id, ArrayList<Task> list) {
        int position = -1;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(id)) {
                position = i;
            }
        }
        return position;
    }
}
