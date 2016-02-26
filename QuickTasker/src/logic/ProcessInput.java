package logic;

import java.util.List;

import controller.Commands;
import controller.InputInterface;
import controller.InputParser;
import model.Task;

public class ProcessInput implements Command {
    public ProcessInput(String userInput, List<Task> tasks) {
        InputParser parser = new InputParser();
        Commands command = parser.outputCommand(userInput);
        String name = parser.outputTaskName(userInput);
        String endDate = parser.outputDate(userInput);
        Task task = new Task();
        task.setName(name);
        task.setDate(endDate);
        command.execute(tasks, new Task());
    }
    
    @Override
    public void execute(List<Task> list, Task task) {
        // TODO Auto-generated method stub
        
    }
}