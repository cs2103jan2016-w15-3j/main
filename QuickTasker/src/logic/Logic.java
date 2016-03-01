package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import controller.Commands;
import model.Task;

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
    }
    
    public void addTask(Task task) {
        commandMap.get(Commands.CREATE_TASK).execute(list, task);
    }
    
    public void deleteTask(int index) {
        commandMap.get(Commands.DELETE_TASK).execute(list, index);
    }
}