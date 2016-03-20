package logic;

import java.util.ArrayList;
import java.util.List;

import model.Task;
import parser.UserInputParser;

public class UpdateTask<E> implements Command<Object> {

    @Override
    public void execute(List<Task> list, Object userInput) {
        try {
            UserInputParser parser = new UserInputParser();
            String updates = (String) userInput;
            Task newTask = new Task(parser.getTaskNameForUpdate(updates), parser.getStartDateForUpdate(updates),
                    parser.getEndDateForUpdate(updates));
            executeUpdate(parser.getIndexForUpdate(updates), newTask, list);
        } catch (Exception e) {
             System.out.println("Error");
        }
    }
    
    
    public void executeUpdate(int taskIndex, Task newTask, List<Task> list) {
        System.out.println(" aaaa   "  + newTask.getName());
        list.set(taskIndex, newTask);
    }


    @Override
    public void undo(ArrayList<Task> list) {
        // TODO Auto-generated method stub
        
    }

}
