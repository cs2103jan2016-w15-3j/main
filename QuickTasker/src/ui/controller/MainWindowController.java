package ui.controller;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import data.JsonTaskDataAccess;
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
    private JsonTaskDataAccess storage;
    private Main main;
    private ParserInterface parser = new UserInputParser();
    private Logic operations = new Logic();

    @FXML private Label label;
    @FXML private AnchorPane container;
    @FXML private JFXTextField commandBox;
    @FXML private JFXListView<Task> taskListView;

    private ObservableList<Task> guiList = FXCollections.observableArrayList();

    // Display messages as visual feedback for users
    private static final String MESSAGE_WELCOME = "Welcome to quickTasker!";
    private static final String MESSAGE_ADD_CONFIRMED = "Task added to list.";
    private static final String MESSAGE_DELETE_CONFIRMED = "Task deleted from list.";
    private static final String MESSAGE_COMPLETED_CONFIRMED = "Task marked as completed.";
    private static final String MESSAGE_EDIT_CONFIRMED = "Task edited.";

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setCellFactory();
        setMain(main);
        initLogger();


    }

    private void initLogger() {
        logger = Logger.getLogger("UILogger");
        logger.setLevel(Level.INFO);
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
        String taskName;
        LocalDate startDate;
        LocalDate dueDate;
        int taskIndex;
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
            }
        } catch (Exception e) {
            throw new UIOperationException();
        }
        assert (false); // execution should not reach here
    }

    private void editTask(String userInput) throws Exception {
        String[] input;
        int indexToEdit;
        boolean editAll = false;
        Task taskToEdit;

        // if it's edit all
        if (userInput.toLowerCase().contains("all")) {

        }
        int taskIndex = parser.getTaskIndex(userInput);

        refresh();
    }

    private void markTaskCompleted(String userInput) throws Exception {

        int i = parser.getIndexForDone(userInput);
        Task task = guiList.get(i);
        task.setDone(true); // logic should handle
        taskListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        taskListView.getSelectionModel().select(i);
        taskListView.fireEvent(new TaskDoneEvent());
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
            taskListView.getSelectionModel().clearSelection();
            commandBox.clear();
        });
        new Thread(sleeper).start();
        // Do not refresh entire list to avoid stack overflow of Event object
    }
    
    private void sortTask(String userInput) {
        System.out.println("Sorting");
        guiList = FXCollections.observableArrayList(operations.sort());
        afterOperation(); 
    }
    
    private void redoTask() {
        guiList = FXCollections.observableArrayList(operations.redo());
        afterOperation();
    }

    private void undoTask() {
        guiList = FXCollections.observableArrayList(operations.undo());
        afterOperation();
    }

    private void updateTask(String userInput) throws Exception {
        Task newTask = new Task(parser.getTaskNameForUpdate(userInput),
                parser.getStartDateForUpdate(userInput), parser.getEndDateForUpdate(userInput));
        guiList = FXCollections.observableArrayList(
                operations.updateTask(newTask, parser.getIndexForUpdate(userInput)));
        afterOperation();
    }

    private void deleteTask(String userInput) throws Exception {
        int taskIndex;
        taskIndex = parser.getTaskIndex(userInput);
        guiList = FXCollections.observableArrayList(operations.deleteTask(taskIndex));
        afterOperation();
    }

    private void refresh() {
        taskListView.setItems(guiList);
    }

    private void createTask(String userInput) throws Exception {
        Task newTask = makeTask(parser.getTaskName(userInput), parser.getStartDate(userInput),
                parser.getEndDate(userInput));
        guiList = FXCollections.observableArrayList(operations.addTask(newTask));
        afterOperation();
    }
    
    private void createRecurringTask(String userInput) throws Exception {
        RecurringTask newTask = makeRecurringTask("taskName", LocalDate.now(), LocalDate.now(), "week");
        guiList = FXCollections.observableArrayList(operations.addTask(newTask));
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
    
    private RecurringTask makeRecurringTask(String taskName, LocalDate startDate, LocalDate dueDate, String type)
            throws Exception {
        return new RecurringTask(taskName, startDate, dueDate, type);
    }

    private void setCellFactory() {
        taskListView.setCellFactory(param -> new TaskListCell(guiList));
    }

    class SearchHighlightedTextCell extends ListCell<String> {
        private static final String HIGHLIGHT_CLASS = "search-highlight";
        private final StringProperty searchText;

        SearchHighlightedTextCell(StringProperty searchText) {
            this.searchText = searchText;
        }
    }
}
