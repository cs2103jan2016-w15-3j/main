package logic;

import java.util.List;

import model.Task;
import parser.UserInputParser;
/*
public class UpdateTask<E> implements Command {

    @Override
    public void execute(List list, Object userInput) {
        UserInputParser parser = new UserInputParser();
        String updates = (String) userInput;
        Task newTask = new Task(parser.getTaskName(updates), parser.getStartDate(updates), parser.getEndDate(updates));
        executeUpdate(parser.getIndexForUpdate(taskIndex), newTask, list);
    }
    
    
    public void executeUpdate(int taskIndex, Task newTask, List<Task> list) {
        list.set(taskIndex, newTask);
    }

}
*/