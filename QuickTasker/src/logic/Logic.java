package logic;

import data.JsonTaskDataAccess;
import data.TaskDataAccessObject;
import model.RecurringTask;
import model.Task;
import parser.Commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.TreeMap;

import org.hamcrest.core.IsInstanceOf;

/**
 * Author A0130949 Soh Yonghao
 * .
 */

public class Logic {
    protected List<Task> list;
    protected TreeMap<Commands, Command> commandMap;
    private TaskDataAccessObject storage;
    protected Stack<Commands> undoStack;
    protected Stack<Commands> redoStack;

    public Logic() {
        initialize();
    }

    private void initialize() {
        populateCommandMap();
        list = new ArrayList<Task>();
        assert (list != null);
        storage = new JsonTaskDataAccess();
        loadSavedTask();
        undoStack = new Stack<Commands>();
        redoStack = new Stack<Commands>();
    }

    public int getSize() {
        assert (list.size() >= 0);
        return list.size();
    }

    public ArrayList<Task> getTasks() {
        return (ArrayList<Task>) list;
    }

    public void populateCommandMap() {
        commandMap = new TreeMap<Commands, Command>();
        commandMap.put(Commands.CREATE_TASK, new AddTask());
        commandMap.put(Commands.DELETE_TASK, new DeleteTask());
        commandMap.put(Commands.DISPLAY_TASK, new DisplayTask());
        commandMap.put(Commands.UPDATE_TASK, new UpdateTask());
        commandMap.put(Commands.SEARCH_TASK, new Search());
        commandMap.put(Commands.SORT_TASK, new Sort());
        //commandMap.put(Commands.RECUR_TASK, new AddRecurTask());
    }

    public List<Task> clear() {
        list = new ArrayList<>();
        storage.reset();
        return list;
    }

    public void adjustmentForRecurringTasks() {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) instanceof RecurringTask) {
                System.out.println(list.get(i).getName() + " " + list.get(i).getStartDate());
                ((RecurringTask) list.get(i)).adjustDate();
                ((RecurringTask) list.get(i)).setRecurType();
                System.out.println(list.get(i).getName() + " " + list.get(i).getStartDate());
            }
        }
    }

    public void loadSavedTask() {
        list = storage.getTasks();
        adjustmentForRecurringTasks();
        saveList();
    }

    public void exit() {
        System.exit(0);
    }

    public ArrayList<Task> addTask(Task task) {
        commandMap.get(Commands.CREATE_TASK).execute(list, task);
        undoStack.push(Commands.CREATE_TASK);
        saveList();
        return (ArrayList<Task>) list;
    }
    
/*    public ArrayList<Task> addRecurTask(RecurringTask task) {
        commandMap.get(Commands.CREATE_TASK).execute(list, task);
        undoStack.push(Commands.CREATE_TASK);
        storage.save(list);
        return (ArrayList<Task>) list;
    }*/

    public ArrayList<Task> deleteTask(int index) {
        System.out.println("deleting " + list.size());
        commandMap.get(Commands.DELETE_TASK).execute(list, index);
        undoStack.push(Commands.DELETE_TASK);
        saveList();
        return (ArrayList<Task>) list;
    }

    public void displayTask() {
        commandMap.get(Commands.DISPLAY_TASK).execute(list, null);
    }

    public ArrayList<Task> updateTask(Task task, int index) {
        list.add(task);
        commandMap.get(Commands.UPDATE_TASK).execute(list, index);
        undoStack.push(Commands.UPDATE_TASK);
        saveList();
        return (ArrayList<Task>) list;
    }

    public ArrayList<Task> undo() {
        Commands command = undoStack.pop();
        redoStack.push(command);
        commandMap.get(command).undo((ArrayList<Task>) list);
        saveList();
        return (ArrayList<Task>) list;
    }

    public ArrayList<Task> redo() {
        Commands command = redoStack.pop();
        undoStack.push(command);
        commandMap.get(command).redo((ArrayList<Task>) list);
        saveList();
        return (ArrayList<Task>) list;
    }

    public ArrayList<Task> sort() {
        commandMap.get(Commands.SORT_TASK).execute(list, "");
        saveList();
        return (ArrayList<Task>) list;
    }
    
    private void saveList() {
        storage.save(list);
    }
}
