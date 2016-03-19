package logic;

import dao.JsonTaskDataAccess;
import model.Task;
import parser.Commands;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Author A0130949 Soh Yonghao
 * <p>
 * <p>
 * <p>
 * .
 */

public class Logic {
    protected static List<Task> list;
    private TreeMap<Commands, Command> commandMap;
    private JsonTaskDataAccess storage;

    public Logic() {
        populateCommandMap();
        list = new ArrayList<Task>();
        storage = new JsonTaskDataAccess();
    }

    public int getSize() {
        return list.size();
    }

    public ArrayList<Task> getList() {
        return (ArrayList<Task>) list;
    }

    public void populateCommandMap() {
        commandMap = new TreeMap<Commands, Command>();
        commandMap.put(Commands.CREATE_TASK, new AddTask());
        commandMap.put(Commands.DELETE_TASK, new DeleteTask());
        commandMap.put(Commands.DISPLAY_TASK, new DisplayTask());
        commandMap.put(Commands.UPDATE_TASK, new UpdateTask());
    }

    public void exit() {
        System.exit(0);
    }

    public void addTask(Task task) {
        commandMap.get(Commands.CREATE_TASK).execute(list, task);
        storage.save(list);
    }

    public void deleteTask(int index) {
        commandMap.get(Commands.DELETE_TASK).execute(list, index);
        storage.save(list);
    }

    public void displayTask() {
        commandMap.get(Commands.DISPLAY_TASK).execute(list, null);
    }

    public void updateTask(String input) {
        commandMap.get(Commands.UPDATE_TASK).execute(list, input);
        storage.save(list);
    }
}
