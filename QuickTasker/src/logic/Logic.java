package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import model.Task;
import parser.Commands;

/**
 * 
 * Author A0130949 Soh Yonghao
 * 
 * 
 * 
 * .
 */

public class Logic {
    protected static List<Task> list;
    private TreeMap<Commands, Command> commandMap;

    public Logic() {
        populateCommandMap();
        list = new ArrayList<Task>();
    }

    public int getSize() {
        return list.size();
    }

    public void populateCommandMap() {
        commandMap = new TreeMap<Commands, Command>();
        commandMap.put(Commands.CREATE_TASK, new AddTask());
        commandMap.put(Commands.DELETE_TASK, new DeleteTask());
        commandMap.put(Commands.DISPLAY_TASK, new DisplayTask());
    }
    
    public void exit(){
        System.exit(0);
    }

    public void addTask(Task task) {
        commandMap.get(Commands.CREATE_TASK).execute(list, task);
    }

    public void deleteTask(int index) {
        commandMap.get(Commands.DELETE_TASK).execute(list, index);
    }

    public void displayTask() {
        commandMap.get(Commands.DISPLAY_TASK).execute(list, null);
    }
}
