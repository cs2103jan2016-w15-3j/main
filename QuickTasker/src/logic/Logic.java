package logic;

import data.JsonTaskDataAccess;
import data.SettingManager;
import data.TaskDataAccessObject;
import model.RecurringTask;
import model.Task;
import parser.Commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.TreeMap;

/**
 * Author A0130949 Soh Yonghao
 * .
 */

public class Logic {
    protected List<Task> list;
    protected List<Task> archivedList;
    protected TreeMap<Commands, Command> commandMap;
    private TaskDataAccessObject storage;
    protected Stack<Commands> undoStack;
    protected Stack<Commands> redoStack;
    private SettingManager settings;

    public Logic() {
        init();
    }

    private void init() {
        initializeVariables();
        loadSavedTask();
    }

    private void initializeVariables() {
        populateCommandMap();
        list = new ArrayList<Task>();
        assert (list != null);
        settings = new SettingManager();
        archivedList = new ArrayList<Task>();
        storage = new JsonTaskDataAccess();
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

    public ArrayList<Task> getArchivedTasks() {
        return (ArrayList<Task>) archivedList;
    }

    public void populateCommandMap() {
        commandMap = new TreeMap<Commands, Command>();
        commandMap.put(Commands.CREATE_TASK, new AddTask());
        commandMap.put(Commands.DELETE_TASK, new DeleteTask());
        commandMap.put(Commands.UPDATE_TASK, new UpdateTask());
        commandMap.put(Commands.SEARCH_TASK, new Search());
        commandMap.put(Commands.SORT_TASK, new Sort());
        commandMap.put(Commands.SKIP, new SkipRecurTask());
        commandMap.put(Commands.STOP, new StopRecurTask());
        commandMap.put(Commands.MARK, new MarkTask());
    }

    public ArrayList<Task> clear() {
        list.clear();
        storage.reset();
        return (ArrayList<Task>) list;
    }

    public void adjustmentForRecurringTasks() {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) instanceof RecurringTask) {
                ((RecurringTask) list.get(i)).adjustDate();
            }
        }
    }

    public void loadSavedTask() {
        list = storage.getTasks();
        adjustmentForRecurringTasks();
        saveList();
    }

    public void exit() {
        saveList();
        System.exit(0);
    }

    public ArrayList<Task> addTask(Task task) {
        commandMap.get(Commands.CREATE_TASK).execute(list, task);
        undoStack.push(Commands.CREATE_TASK);
        saveList();
        return (ArrayList<Task>) list;
    }

    public ArrayList<Task> deleteTask(int index) {
        commandMap.get(Commands.DELETE_TASK).execute(list, index);
        undoStack.push(Commands.DELETE_TASK);
        saveList();
        return (ArrayList<Task>) list;
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
        sort();
        return (ArrayList<Task>) list;
    }

    public ArrayList<Task> redo() {
        Commands command = redoStack.pop();
        undoStack.push(command);
        commandMap.get(command).redo((ArrayList<Task>) list);
        sort();
        return (ArrayList<Task>) list;
    }

    public ArrayList<Task> sort() {
        commandMap.get(Commands.SORT_TASK).execute(list, "");
        saveList();
        return (ArrayList<Task>) list;
    }

    public ArrayList<Task> skip(int index) {
        if (list.get(index) instanceof RecurringTask) {
            commandMap.get(Commands.SKIP).execute(list, index);
            undoStack.push(Commands.SKIP);
            saveList();
        }
        return (ArrayList<Task>) list;
    }

    public void skipForMark(int index) {
        if (list.get(index) instanceof RecurringTask) {
            commandMap.get(Commands.SKIP).execute(list, index);
            saveList();
        }
    }

    public void stopRecurring(int index) {
        if (list.get(index) instanceof RecurringTask) {
            commandMap.get(Commands.STOP).execute(list, index);
            undoStack.push(Commands.STOP);
            saveList();
        }
        saveList();
    }

    public void markAsDone(String taskId) {
        try {
            int index = findTask(taskId, list);
            Task completedTask = list.get(index);
            completedTask.setDone(true);
            if (list.get(index) instanceof RecurringTask) {
                archivedList.add(clone(completedTask));
                skipForMark(index);
            } else {
                archivedList.add(list.get(index));
                list.remove(index);
            }
            undoStack.push(Commands.MARK);
            commandMap.get(Commands.MARK).execute(archivedList, completedTask.getId());
            saveList();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException();
        } catch (NumberFormatException e) {
            throw new NumberFormatException();
        }
    }

    // requires cloning to prevent the reucrring task from changing values
    private RecurringTask clone(Task completedTask) {
        return new RecurringTask(completedTask.getName(),
                completedTask.getStartDate(), completedTask.getDueDate(),
                ((RecurringTask) completedTask).getRecurType(), completedTask.getStartTime(),
                completedTask.getEndTime(), ((RecurringTask) completedTask).getNumberToRecur());
    }

    public void changeDir(String path) {
        settings.setPathOfSaveFile(path);
        saveList();
    }

    public int findTask(String id, List<Task> list) {
        int position = -1;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(id)) {
                position = i;
            }
        }
        return position;
    }

    private void saveList() {
        storage.save(list);
    }
}
