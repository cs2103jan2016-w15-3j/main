package logic;

import java.util.ArrayList;
import java.util.List;

import model.Task;
import parser.UserInputParser;

public class UpdateTask<E> implements Command<Object> {

    @Override
    public void execute(List<Task> list, Object index) {
/*        try {
            UserInputParser parser = new UserInputParser();
            String updates = (String) userInput;
            Task newTask = new Task(parser.getTaskNameForUpdate(updates), parser.getStartDateForUpdate(updates),
                    parser.getEndDateForUpdate(updates));
            executeUpdate(parser.getIndexForUpdate(updates), newTask, list);
        } catch (Exception e) {
             System.out.println("Error");
        }*/
        int taskIndex = (int) index;
        executeUpdate(taskIndex, list);
    }
    
    
    public void executeUpdate(int taskIndex, List<Task> list) {
        Task newTask = list.remove(list.size() - 1);
        list.set(taskIndex, newTask);
    }


    @Override
    public void undo(ArrayList<Task> list) {
        // TODO Auto-generated method stub
        
    }

}
