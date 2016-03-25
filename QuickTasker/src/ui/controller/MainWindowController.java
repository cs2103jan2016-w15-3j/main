package ui.controller;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import logic.Logic;
import model.RecurringTask;
import model.Task;
import parser.Commands;
import parser.ParserInterface;
import parser.UserInputParser;
import ui.model.TaskListCell;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Author: A0133333U/A0130949Y/A0126077E
 */
public class MainWindowController implements Initializable {

    private static Logger logger;
    private static MainWindowController mainWindowController;
    private Main main;
    private ParserInterface parser = new UserInputParser();
    private Logic operations = new Logic();

    @FXML private Label label;
    @FXML private AnchorPane container;
    @FXML private JFXTextField commandBox;
    /* Naming explanation for team : To be removed */
    /* this naming theme is based on the idea that
       variable names should be abstractions resembling real life objects ,
       such that they make sense even to a layman and could be read like a story/procedure/manual
     */
    @FXML private JFXListView<Task> printedPlanner;
    // a printed planner presents plannerEntries in formatted manner
    private ObservableList<Task> plannerEntries;
    // planner entries are raw entries of tasks/events,
    // think of them as those stuff you wrote on toilet paper but not translated into a
    // printedPlanner yet

    // Display messages as visual feedback for users
    private static final String MESSAGE_WELCOME = "Welcome to quickTasker!";
    private static final String MESSAGE_ADD_CONFIRMED = "Task added to list.";
    private static final String MESSAGE_DELETE_CONFIRMED = "Task deleted from list.";
    private static final String MESSAGE_COMPLETED_CONFIRMED = "Task marked as completed.";
    private static final String MESSAGE_EDIT_CONFIRMED = "Task edited.";

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setMain(main);
        operations = new Logic();
        parser = new UserInputParser();
        initPlanner();
        setCellFactory();
        initLogger();
    }

    private void initLogger() {
        logger = Logger.getLogger("UILogger");
        logger.setLevel(Level.INFO);
    }

    private void initPlanner() {
        plannerEntries = FXCollections.observableArrayList(operations.loadSavedTask());
        printedPlanner.setItems(plannerEntries);
    }

    void setMain(Main main) {
        this.main = main;
    }

    protected boolean isEmptyInput(String input) {
        return input == null || input.isEmpty() || "".equals(input.trim());
    }

    private boolean enterKeyIsPressed(KeyEvent event) {
        return KeyCode.ENTER.equals(event.getCode());
    }

    @FXML
    private void handleEnterKeyPressed(KeyEvent event) {
        String userInput = commandBox.getText();
        if (!isEmptyInput(userInput) && enterKeyIsPressed(event)) {
            try {
                performOperations(userInput);
            } catch (UIOperationException e) {
                logger.log(Level.SEVERE, "Error occured at " + this.getClass().getName()
                        + " within performOperation method.");
            }
            logger.log(Level.INFO, "User typed in : <" + userInput + "> command string");
        }
    }

    private class UIOperationException extends RuntimeException {
    }

    private void performOperations(String userInput) throws UIOperationException {
        try {
            if (parser.getCommand(userInput) == Commands.CREATE_TASK) {
                createTask(userInput);
            } else if (parser.getCommand(userInput) == Commands.DELETE_TASK) {
                deleteTask(userInput);
            } else if (parser.getCommand(userInput) == Commands.UPDATE_TASK) {
                updateTask(userInput);
            } else if (parser.getCommand(userInput) == Commands.UNDO_TASK) {
                undoTask();
            } else if (parser.getCommand(userInput) == Commands.REDO) {
                redoTask();
            } else if (parser.getCommand(userInput) == Commands.EXIT) {
                operations.exit();
            } else if (parser.getCommand(userInput) == Commands.SORT_TASK) {
                System.out.println("getting the command");
                sortTask(userInput);
            } else if (userInput.contains("edit")) {
                editTask(userInput);
            } else if (userInput.contains("mark")) {
                markTaskCompleted(userInput);
            } else if (userInput.contains("recur")) {
                createRecurringTask(userInput);
            }
        } catch (Exception e) {
            throw new UIOperationException();
        }
    }

    private void editTask(String userInput) throws Exception {
        String[] input;
        int indexToEdit;
        boolean editAll = false;
        Task taskToEdit;
        int taskIndex = parser.getTaskIndex(userInput);
        refresh();
    }

    private void markTaskCompleted(String userInput) throws Exception {

        int i = parser.getIndexForDone(userInput);
        Task task = plannerEntries.get(i);
        task.setDone(true); // logic should handle
        printedPlanner.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        printedPlanner.getSelectionModel().select(i);
        printedPlanner.fireEvent(new TaskDoneEvent());
        javafx.concurrent.Task<Void> sleeper = new javafx.concurrent.Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    // do nothing. handles libray bug
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(event -> {
            printedPlanner.getSelectionModel().clearSelection();
            commandBox.clear();
        });
        new Thread(sleeper).start();
        // Do not refresh entire list to avoid stack overflow of Event object
    }

    private void sortTask(String userInput) {
        System.out.println("Sorting");
        plannerEntries = FXCollections.observableArrayList(operations.sort());
        afterOperation();
    }

    private void redoTask() {
        plannerEntries = FXCollections.observableArrayList(operations.redo());
        afterOperation();
    }

    private void undoTask() {
        plannerEntries = FXCollections.observableArrayList(operations.undo());
        afterOperation();
    }

    private void updateTask(String userInput) throws Exception {
        Task newTask = new Task(parser.getTaskNameForUpdate(userInput),
                parser.getStartDateForUpdate(userInput), parser.getEndDateForUpdate(userInput));
        plannerEntries = FXCollections.observableArrayList(
                operations.updateTask(newTask, parser.getIndexForUpdate(userInput)));
        afterOperation();
    }

    private void deleteTask(String userInput) throws Exception {
        int taskIndex;
        taskIndex = parser.getTaskIndex(userInput);
        plannerEntries = FXCollections.observableArrayList(operations.deleteTask(taskIndex));
        afterOperation();
    }

    private void refresh() {
        printedPlanner.setItems(plannerEntries);
    }

    private void createTask(String userInput) throws Exception {
        Task newTask = makeTask(parser.getTaskName(userInput), parser.getStartDate(userInput),
                parser.getEndDate(userInput));
        plannerEntries = FXCollections.observableArrayList(operations.addTask(newTask));
        afterOperation();
    }

    private void createRecurringTask(String userInput) throws Exception {
        Task newTask = makeRecurringTask("taskName", LocalDate.of(2016, 03, 20), LocalDate.of(2016, 03, 23),
                "week");
        plannerEntries = FXCollections.observableArrayList(operations.addTask(newTask));
        afterOperation();
    }

    private void afterOperation() {
        setCellFactory();
        refresh();
        commandBox.clear();
    }

    private Task makeTask(String taskName, LocalDate startDate, LocalDate dueDate)
            throws Exception {
        return new Task(taskName, startDate, dueDate);
    }

    private Task makeRecurringTask(String taskName, LocalDate startDate, LocalDate dueDate,
            String type) throws Exception {
        return new Task(taskName, startDate, dueDate, type);
    }

    private void setCellFactory() {
        printedPlanner.setCellFactory(param -> new TaskListCell(plannerEntries));
    }

    class SearchHighlightedTextCell extends ListCell<String> {
        private static final String HIGHLIGHT_CLASS = "search-highlight";
        private final StringProperty searchText;

        SearchHighlightedTextCell(StringProperty searchText) {
            this.searchText = searchText;
        }
    }
}
