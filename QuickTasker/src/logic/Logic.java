package logic;

import data.JsonTaskDataAccess;
import data.SettingManager;
import data.TaskDataAccessObject;
import model.RecurringTask;
import model.Task;
import parser.Commands;

import java.util.*;

//@@author A0130949

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

    private void populateCommandMap() {
        commandMap = new TreeMap<Commands, Command>();
        commandMap.put(Commands.CREATE_TASK, new AddTask());
        commandMap.put(Commands.DELETE_TASK, new DeleteTask());
        commandMap.put(Commands.UPDATE_TASK, new UpdateTask());
        commandMap.put(Commands.SKIP_TASK, new SkipRecurTask());
        commandMap.put(Commands.STOP_TASK, new StopRecurTask());
        commandMap.put(Commands.MARK_TASK, new MarkTask());
       // commandMap.put((Commands.CLEAR_TASK),new ClearTasks());
    }

    public ArrayList<Task> clear() {
        //list.clear();
        //storage.reset();
        commandMap.get(Commands.CLEAR_TASK).execute(list, "");
        manageStacks(Commands.CLEAR_TASK);
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
        manageStacks(Commands.CREATE_TASK);
        saveList();
        return (ArrayList<Task>) list;
    }

    public void manageStacks(Commands command) {
        undoStack.push(command);
        redoStack.clear();
    }

    public ArrayList<Task> deleteTask(int index) {
        commandMap.get(Commands.DELETE_TASK).execute(list, index);
        manageStacks(Commands.DELETE_TASK);
        saveList();
        return (ArrayList<Task>) list;
    }

    public ArrayList<Task> updateTask(Task task, int index) {
        list.add(task);
        commandMap.get(Commands.UPDATE_TASK).execute(list, index);
        manageStacks(Commands.UPDATE_TASK);
        saveList();
        return (ArrayList<Task>) list;
    }

    public ArrayList<Task> undo() {
        Commands command = undoStack.pop();
        redoStack.push(command);
        commandMap.get(command).undo((ArrayList<Task>) list);
        System.out.println("undo " + command);
        Collections.sort(list);
        return (ArrayList<Task>) list;
    }

    public ArrayList<Task> redo() {
        Commands command = redoStack.pop();
        undoStack.push(command);
        commandMap.get(command).redo((ArrayList<Task>) list);
        System.out.println("redo " + command);
        Collections.sort(list);
        return (ArrayList<Task>) list;
    }

    public ArrayList<Task> sort() {
        commandMap.get(Commands.SORT_TASK).execute(list, "");
        saveList();
        return (ArrayList<Task>) list;
    }

    public ArrayList<Task> skip(int index) {
        if (list.get(index) instanceof RecurringTask) {
            commandMap.get(Commands.SKIP_TASK).execute(list, index);
            manageStacks(Commands.SKIP_TASK);
            saveList();
        }
        return (ArrayList<Task>) list;
    }

    // mark uses this version of skip so that the undoStack does not take in skip command
    public void skipForMark(int index) {
        if (list.get(index) instanceof RecurringTask) {
            commandMap.get(Commands.SKIP_TASK).execute(list, index);
            saveList();
        }
    }

    public void stopRecurring(int index) {
        if (list.get(index) instanceof RecurringTask) {
            commandMap.get(Commands.STOP_TASK).execute(list, index);
            manageStacks(Commands.STOP_TASK);
            saveList();
        }
        saveList();
    }

    public void markAsDone(String taskId) {
        try {
            shiftCompletedTaskToArchivedList(taskId);
            saveList();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException();
        } catch (NumberFormatException e) {
            throw new NumberFormatException();
        }
    }

    // recurring task requires cloning as the task remains in the list
    private void shiftCompletedTaskToArchivedList(String taskId) {
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
        commandMap.get(Commands.MARK_TASK).execute(archivedList, completedTask.getId());
        manageStacks(Commands.MARK_TASK);
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