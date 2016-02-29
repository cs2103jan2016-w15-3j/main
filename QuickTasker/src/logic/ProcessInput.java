package logic;

import java.util.TreeMap;

import controller.Commands;

public class ProcessInput {
    private TreeMap<Commands, Command> commandMap;
    /*
    public ProcessInput(String userInput, List<Task> tasks) {
        InputParser parser = new InputParser();
        String name = parser.outputTaskName(userInput);
        LocalDate endDate = parser.outputDate(userInput);
        Task task = new Task(name, LocalDate.now(), endDate);
        commandMap.get(command).execute(tasks, task);
    }
    }*/

    public void populateCommandMap() {
        commandMap = new TreeMap<Commands, Command>();
        commandMap.put(Commands.CREATE_TASK, new AddTask());
    }
}