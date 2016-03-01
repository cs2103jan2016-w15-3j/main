package logic;

import java.time.LocalDate;
import java.util.List;
import java.util.TreeMap;

import javax.activation.CommandMap;
import javax.lang.model.type.PrimitiveType;

import com.sun.javafx.collections.MappingChange.Map;

import controller.Commands;
import controller.InputInterface;
import controller.InputParser;
import model.Task;

public class ProcessInput {
    private TreeMap<Commands, Command> commandMap;

    public ProcessInput(String userInput, List<Task> tasks) {
        InputParser parser = new InputParser();
        populateCommandMap();
/*        String command = parser.outputCommand(userInput);
        String name = parser.outputTaskName(userInput);
        LocalDate endDate = parser.outputDate(userInput);*/
        Task task = new Task("name", LocalDate.now(), LocalDate.now());
        commandMap.get(Commands.CREATE_TASK).execute(tasks, task);
    }

    public void populateCommandMap() {
        commandMap = new TreeMap<Commands, Command>();
        commandMap.put(Commands.CREATE_TASK, new AddTask());
    }
}