package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.TreeMap;

import dao.JsonTaskDataAccess;
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
    protected TreeMap<Commands, Command> commandMap;
    private JsonTaskDataAccess storage;
    protected Stack<Commands> stack;

    public Logic() {
        initialize();
    }

    private void initialize() {
        populateCommandMap();
        list = new ArrayList<Task>();
        assert(list != null);
        storage = new JsonTaskDataAccess();
        stack = new Stack<Commands>();
    }

    public int getSize() {
        assert(list.size() >= 0);
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
    
    public void exit(){
        System.exit(0);
    }

    public ArrayList<Task> addTask(Task task) {
        commandMap.get(Commands.CREATE_TASK).execute(list, task);
        stack.push(Commands.CREATE_TASK);
        storage.save(list);
        return (ArrayList<Task>) list;
    }

    public ArrayList<Task> deleteTask(int index) {
        commandMap.get(Commands.DELETE_TASK).execute(list, index);
        stack.push(Commands.DELETE_TASK);
        storage.save(list);
        return (ArrayList<Task>) list;
    }

    public void displayTask() {
        commandMap.get(Commands.DISPLAY_TASK).execute(list, null);
    }
    
    public ArrayList<Task> updateTask(Task task, int index) {
        list.add(task);
        commandMap.get(Commands.UPDATE_TASK).execute(list, index);
        stack.push(Commands.UPDATE_TASK);
        storage.save(list);
        return (ArrayList<Task>) list;
    }
    
    public ArrayList<Task> undo() {
        commandMap.get(stack.pop()).undo((ArrayList<Task>)list);
        return (ArrayList<Task>) list; 
    }
}
