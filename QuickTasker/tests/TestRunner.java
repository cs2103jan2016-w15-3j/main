import data.JsonTaskDataAccessTest;
import data.SettingManagerTest;
import logic.*;
import model.RecurringTaskTest;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import parser.DateTimeParserTest;
import parser.UserInputParserTest;
import ui.controller.TaskDoneEventTest;
import ui.model.TaskListCellTest;

import java.util.List;

public class TestRunner {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(JsonTaskDataAccessTest.class, SettingManagerTest.class,
                AddTaskTest.class, DeleteTaskTest.class, DisplayTaskTest.class,
                UpdateTaskTest.class, AddRecurTaskTest.class, RecurringTaskTest.class,
                DateTimeParserTest.class, UserInputParserTest.class, TaskDoneEventTest.class,
                TaskListCellTest.class);

        System.out.println();

        System.out.println(ANSI_GREEN + "Run count: " + result.getRunCount() + ANSI_RESET);
        System.out.println(ANSI_GREEN + "Run time: " + result.getRunTime() + ANSI_RESET);
        System.out.println(ANSI_CYAN + "===================================" + ANSI_RESET);
        if (result.wasSuccessful()) {
            System.out.println(ANSI_BLUE + "Wohooooo you passed all those tests!" + ANSI_RESET);
        } else {
            List<Failure> failures = result.getFailures();
            for (Failure f : failures) {
                System.out.println(ANSI_RED + f.toString() + ANSI_RESET);
            }
        }
    }
}
