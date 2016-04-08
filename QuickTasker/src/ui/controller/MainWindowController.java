package ui.controller;

import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.Logic;
import model.RecurringTask;
import model.Task;
import org.ocpsoft.prettytime.shade.org.apache.commons.lang.NullArgumentException;
import parser.Commands;
import parser.RecurringParser;
import parser.UpdateParser;
import parser.UserInputParser;
import ui.model.TaskListCell;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.EmptyStackException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ui.controller.TaskDoneEvent.TASK_COMPLETE;

//@@author  A0133333U

public class MainWindowController implements Initializable {

    private static Logger logger;

    private Main main;
    private Stage stage;
    private final UserInputParser parser = new UserInputParser();
    private final UpdateParser updateParser = new UpdateParser();
    private RecurringParser recurringParser = new RecurringParser();
    private final Logic operations = new Logic();
    private SearchHelper util = new SearchHelper();

    @FXML private AnchorPane mainContentContainer;
    @FXML private StackPane overlayPane;
    @FXML JFXBadge tasksCounter;
    @FXML public BorderPane root;
    @FXML private JFXTextField commandBox;
    @FXML private JFXListView<Task> printedPlanner;

    @FXML private JFXSnackbar snackbar;
    @FXML private AnchorPane commandBoxContainer;
    @FXML private VBox headerWrapper;
    @FXML private JFXToolbar headerContainer;
    @FXML private JFXRippler headerTitleContainer;
    @FXML private Label headerTitle;

    private ObservableList<Task> plannerEntries;

    /**
     * Display messages as visual feedback for users.
     */
    private static final String MESSAGE_WELCOME = "Welcome to quickTasker!";
    private static final String MESSAGE_ADD_CONFIRMED = "Task added to list.";
    private static final String MESSAGE_DELETE_CONFIRMED = "Task deleted from list.";
    private static final String MESSAGE_COMPLETED_CONFIRMED = "Task marked as completed.";
    private static final String MESSAGE_EDIT_CONFIRMED = "Task edited.";

    public MainWindowController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initPlanner();
        setCellFactory();
        initLogger();
    }

    private void initLogger() {
        logger = Logger.getLogger("UILogger");
        logger.setLevel(Level.INFO);
    }

    private void initPlanner() {
        plannerEntries = FXCollections.observableArrayList(operations.getTasks());
        printedPlanner.setItems(plannerEntries);
        printedPlanner.setDepthProperty(1);
        snackbar.registerSnackbarContainer(mainContentContainer);
        tasksCounter.setText(plannerEntries.size() + "");
        commandBox.requestFocus();
    }

    void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setMain(Main main) {
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
            logger.log(Level.INFO, "User typed in : <" + userInput + "> command string");
            try {
                performOperations(userInput);
            } catch (UIOperationException e) {
                logger.log(Level.SEVERE,
                        "Error occured at " + getClass().getName() + " within performOperation method.\n");
                e.printStackTrace();

            }
        }
    }

    private class UIOperationException extends RuntimeException {
    }

    private void performOperations(String userInput) throws UIOperationException {
/*
        InputValidator inputValidator = new InputValidator();
        System.out.println("inputValidator.checkAllValid(userInput) " + inputValidator.checkAllValid(userInput));*/
        //if (inputValidator.checkAllValid(userInput)) {
        try {
            if (parser.getCommand(userInput) == Commands.CREATE_TASK) {
                createTask(userInput);
            } else if (parser.getCommand(userInput) == Commands.DELETE_TASK) {
                deleteTask(userInput);
            } else if (parser.getCommand(userInput) == Commands.UPDATE_TASK) {
                updateTask(userInput);
            } else if (parser.getCommand(userInput) == Commands.UNDO_TASK) {
                undoTask();
            } else if (parser.getCommand(userInput) == Commands.REDO_TASK) {
                redoTask();
            } else if (parser.getCommand(userInput) == Commands.EXIT) {
                operations.exit();
            } else if (parser.getCommand(userInput) == Commands.SORT_TASK) {
                sortTask(userInput);
            } else if (parser.getCommand(userInput) == Commands.MARK_TASK) {
                markTaskCompleted(userInput);
            } else if (parser.getCommand(userInput) == Commands.RECUR_TASK) {
                createRecurringTask(userInput);
            } else if (parser.getCommand(userInput) == Commands.SKIP_TASK) {
                skipRecurringTask(userInput);
            } else if (userInput.contains("clear")) {
                clearTasks(userInput);
            } else if (userInput.contains("stop")) {
                stopRecurringTask(userInput);
            } else if ("show today".equals(userInput) || "view today".equals(userInput)) {
                showToday();
            } else if ("show tomorrow".equals(userInput) || "view tomorrow".equals(userInput)) {
                showTomorrow();
                showAll();
            } else if ("view floating".equals(userInput) || "show floating".equals(userInput)) {
                showFloating();
            } else if ("show all".equals(userInput) || "view all".equals(userInput)) {
                showAll();
            } else if (parser.getCommand(userInput) == Commands.SEARCH_TASK) {
                searchTask(userInput);
            } else if (userInput.contains("theme")) {
                Scene scene = main.getScene();
                ThemeChanger themer = new ThemeChanger();
                Platform.runLater(() -> {
                    try {
                        themer.change(userInput, scene);
                        commandBox.clear();
                        //an event with a button maybe
                    } catch (Exception e) {
                    }
                });
            } else if ("change directory".equals(userInput)) {
                changeDirectory(userInput);
            } else if ("view archived".equals(userInput)) {
                viewArchived();
            } else if ("back".equals(userInput)) {
                viewTasks();
            }
        } catch (Exception e) {
            throw new UIOperationException();
        }
    }

    private void showTomorrow() {
        printedPlanner.setItems(plannerEntries.filtered(task -> util.isItDisplayedInTomorrowView(task)));
        headerTitle.setText("Tasks: Tomorrow");
        updateTaskCounter();
        commandBox.clear();
    }

    private void updateTaskCounter() {
        tasksCounter.setText(printedPlanner.getItems().size() + "");
    }

    private void showFloating() {
        printedPlanner.setItems(plannerEntries.filtered(task -> util.isFloatingTask(task)));
        headerTitle.setText("Tasks: Floating");
        updateTaskCounter();
        commandBox.clear();
    }

    private void searchTask(String userInput) throws Exception {

        if (isKeywordSearch()) {
            String taskName = parser.getTaskName(userInput);
            headerTitle.setText("Search Results  \"" + taskName + "\":");
            printedPlanner.setItems(plannerEntries.filtered(task -> util.containsKeyWord(task, taskName)));
            updateTaskCounter();
        }

        // other types of search
        // to be written
        commandBox.clear();
    }

    /**
     * To be moved inside serach helper when tidy up.
     */
    private boolean isKeywordSearch() {
        return true;
    }

    private void showAll() {
        printedPlanner.setItems(plannerEntries);
        headerTitle.setText("Tasks: All");
        updateTaskCounter();
        commandBox.clear();
    }

    private void showToday() {

        printedPlanner.setItems(plannerEntries.filtered(task -> util.isItDisplayedInTodayView(task)));
        headerTitle.setText("Tasks: Today + Floating");
        updateTaskCounter();
        commandBox.clear();
    }

    /**
     * Author A0130949Y.
     */
    private void markTaskCompleted(String userInput) throws Exception {
        try {
            int i = parser.getIndexForDone(userInput);
            Task task = plannerEntries.get(i);
            operations.markAsDone(task.getId());
            task.setDone(true); // logic should handle
            tickCheckBoxForMark(task, i);
        } catch (ArrayIndexOutOfBoundsException e) {
            snackbar.fireEvent(new JFXSnackbar.SnackbarEvent("Index is invalid!", "", 1500, (b) -> {
            }));
        } catch (NumberFormatException e) {
            snackbar.fireEvent(new JFXSnackbar.SnackbarEvent("Index is not a number", "", 1500, (b) -> {
            }));
        } catch (IndexOutOfBoundsException e) {
            snackbar.fireEvent(new JFXSnackbar.SnackbarEvent("Index is invalid!", "", 1500, (b) -> {
            }));
        }
    }

    /**
     * Author kenan.
     */
    private void tickCheckBoxForMark(Task task, int i) {
        printedPlanner.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        printedPlanner.getSelectionModel().select(i);
        printedPlanner.fireEvent(new TaskDoneEvent(task));
        javafx.concurrent.Task<Void> sleeper;
        sleeper = makeSleeper(500);
        sleeper.setOnSucceeded(event -> {
            printedPlanner.getSelectionModel().clearSelection();
            commandBox.clear();
        });
        new Thread(sleeper).start();
    }

    private javafx.concurrent.Task<Void> makeSleeper(int duration) {
        return new javafx.concurrent.Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(duration);
                } catch (InterruptedException e) {
                    // do nothing. handles library bug
                }
                return null;
            }
        };
    }

    /**
     * Author A0130949Y.
     */
    private void viewTasks() {
        plannerEntries = FXCollections.observableArrayList(operations.getTasks());
        afterOperation();
    }

    private void viewArchived() {
        plannerEntries = FXCollections.observableArrayList(operations.getArchivedTasks());
        afterOperation();
        headerTitle.setText("Tasks: Archived");
    }

    private void sortTask(String userInput) {
        System.out.println("Sorting");
        plannerEntries = FXCollections.observableArrayList(operations.sort());
        afterOperation();
    }

    private void clearTasks(String userInput) {
        plannerEntries = FXCollections.observableArrayList(operations.clear());
        afterOperation();
    }

    private final String ERROR_MESSAGE_FOR_WRONG_INDEX = "Index is invalid!";
    private final String ERROR_MESSAGE_FOR_INVALID_INDEX = "Index is not a number!";
    private final String ERROR_MESSAGE_FOR_SKIPPING_RECURRING_TASK = "This index is not a recurring task!";

    private void skipRecurringTask(String userInput) throws Exception {
        try {
            int index = parser.getTaskIndex(userInput);
            Task task = plannerEntries.get(index);
            if (!(task instanceof RecurringTask)) {
                snackbar.fireEvent(
                        new JFXSnackbar.SnackbarEvent(ERROR_MESSAGE_FOR_SKIPPING_RECURRING_TASK, "", 1500,
                                (b) -> {
                                }));
            }
            plannerEntries = FXCollections.observableArrayList(operations.skip(index));
            afterOperation();
        } catch (IndexOutOfBoundsException e) {
            snackbar.fireEvent(new JFXSnackbar.SnackbarEvent(ERROR_MESSAGE_FOR_WRONG_INDEX, "", 1500, (b) -> {
            }));
        } catch (NumberFormatException e) {
            snackbar.fireEvent(
                    new JFXSnackbar.SnackbarEvent(ERROR_MESSAGE_FOR_INVALID_INDEX, "", 1500, (b) -> {
                    }));
        }
    }

    private void stopRecurringTask(String userInput) throws Exception {
        operations.stopRecurring(parser.getTaskIndex(userInput));
        afterOperation();
    }

    private void redoTask() {
        try {
            plannerEntries = FXCollections.observableArrayList(operations.redo());
            afterOperation();
        } catch (EmptyStackException e) {
            snackbar.fireEvent(new JFXSnackbar.SnackbarEvent("Error with redo", "", 1500, (b) -> {
            }));
        }
    }

    private void undoTask() {
        try {
            plannerEntries = FXCollections.observableArrayList(operations.undo());
            afterOperation();
        } catch (EmptyStackException e) {
            snackbar.fireEvent(new JFXSnackbar.SnackbarEvent("Error with undo", "", 1500, (b) -> {
            }));
        }
    }

    private void updateTask(String userInput) throws Exception {
        try {
            int indexOfTask = updateParser.getTaskIndex(userInput);
            Task task = plannerEntries.get(indexOfTask);
            printedPlanner.getSelectionModel().select(indexOfTask);
            Task newTask = makeTask(updateParser.getTaskName(userInput), updateParser.getStartDate(userInput),
                    updateParser.getEndDate(userInput), updateParser.getStartTime(userInput),
                    updateParser.getEndTime(userInput));
            plannerEntries = FXCollections.observableArrayList(operations.updateTask(newTask, indexOfTask));
            // }
            printedPlanner.getSelectionModel().clearSelection();
            afterOperation();
            snackbar.fireEvent(new JFXSnackbar.SnackbarEvent("Task updated.", "", 1500, (b) -> {
            }));
        } catch (IndexOutOfBoundsException e) {
            snackbar.fireEvent(new JFXSnackbar.SnackbarEvent(ERROR_MESSAGE_FOR_WRONG_INDEX, "", 1500, (b) -> {
            }));
        } catch (NumberFormatException e) {
            snackbar.fireEvent(
                    new JFXSnackbar.SnackbarEvent(ERROR_MESSAGE_FOR_INVALID_INDEX, "", 1500, (b) -> {
                    }));
        }
    }

    private void deleteTask(String userInput) throws Exception {
        try {
            int taskIndex;
            taskIndex = parser.getTaskIndex(userInput);
            // plannerEntries.remove(taskIndex);
            plannerEntries = FXCollections.observableArrayList(operations.deleteTask(taskIndex));
            afterOperation();
            snackbar.fireEvent(new JFXSnackbar.SnackbarEvent("Task Removed.", "", 1500, (b) -> {
            }));
        } catch (NumberFormatException e) {
            snackbar.fireEvent(
                    new JFXSnackbar.SnackbarEvent(ERROR_MESSAGE_FOR_INVALID_INDEX, "", 1500, (b) -> {
                    }));
        } catch (IllegalArgumentException e) {
            snackbar.fireEvent(new JFXSnackbar.SnackbarEvent(ERROR_MESSAGE_FOR_WRONG_INDEX, "", 1500, (b) -> {
            }));
        } catch (IndexOutOfBoundsException e) {
            snackbar.fireEvent(new JFXSnackbar.SnackbarEvent(ERROR_MESSAGE_FOR_WRONG_INDEX, "", 1500, (b) -> {
            }));
        }
    }

    private void changeDirectory(String userInput) throws Exception {
        operations.changeDir(userInput);
    }

    private void createTask(String userInput) throws Exception {
        try {
            Task newTask = makeTask(parser.getTaskName(userInput), parser.getStartDate(userInput),
                    parser.getEndDate(userInput), parser.getStartTime(userInput),
                    parser.getEndTime(userInput));
            plannerEntries = FXCollections.observableArrayList(operations.addTask(newTask));
            afterOperation();
            snackbar.fireEvent(new JFXSnackbar.SnackbarEvent("New task created", "", 1500, (b) -> {
            }));
            printedPlanner.scrollTo(printedPlanner.getItems().size() - 1);
        } catch (NullArgumentException e) {
            snackbar.fireEvent(new JFXSnackbar.SnackbarEvent("Task cannot be null!", "", 1500, (b) -> {
            }));
        } catch (IndexOutOfBoundsException e) {
            snackbar.fireEvent(new JFXSnackbar.SnackbarEvent("Did you enter a task?", "", 1500, (b) -> {
            }));
        }
    }

    private void createRecurringTask(String userInput) throws Exception {
        try {
            RecurringTask newTask = makeRecurringTask(recurringParser.getTaskName(userInput),
                    recurringParser.getTaskStartDate(userInput), recurringParser.getTaskEndDate(userInput),
                    recurringParser.getRecurDuration(userInput), recurringParser.getTaskStartTime(userInput),
                    recurringParser.getTaskEndTime(userInput), recurringParser.getNumToRecur(userInput));
            plannerEntries = FXCollections.observableArrayList(operations.addTask(newTask));
            afterOperation();
        } catch (IndexOutOfBoundsException e) {
            snackbar.fireEvent(
                    new JFXSnackbar.SnackbarEvent("Did you enter a recurring task?", "", 1500, (b) -> {
                    }));
        } catch (NullArgumentException e) {
            snackbar.fireEvent(
                    new JFXSnackbar.SnackbarEvent("Recurring task cannot be null!", "", 1500, (b) -> {
                    }));
        } catch (NumberFormatException e) {
            snackbar.fireEvent(
                    new JFXSnackbar.SnackbarEvent("Did you enter a recurring task?", "", 1500, (b) -> {
                    }));
        }
    }

    private void afterOperation() {
        setCellFactory();
        refresh();
        updateTaskCounter();
        commandBox.clear();
    }

    private Task makeTask(String taskName, LocalDate startDate, LocalDate dueDate, LocalTime startTime,
            LocalTime endTime) throws Exception {
        return new Task(taskName, startDate, dueDate, startTime, endTime);
    }

    private RecurringTask makeRecurringTask(String taskName, LocalDate startDate, LocalDate dueDate,
            String type, LocalTime startTime, LocalTime endTime, int numberToRecur) throws Exception {
        return new RecurringTask(taskName, startDate, dueDate, type, startTime, endTime, numberToRecur);
    }

    /**
     * Author kenan.
     */
    private void refresh() {
        printedPlanner.setItems(plannerEntries);
    }

    private void setCellFactory() {
        printedPlanner.setCellFactory(param -> {

            TaskListCell listCell = new TaskListCell(plannerEntries);
            printedPlanner.addEventFilter(TASK_COMPLETE, event -> new Thread(() -> {
                Thread.currentThread()
                        .setUncaughtExceptionHandler((t, e) -> Platform.runLater(System.out::println));
                if (listCell.getTask().equals(event.getTask())) listCell.getCheckBox().fire();
                listCell.removeEventFilter(TASK_COMPLETE, null);
            }).start());
            return listCell;
        });
    }

    class SearchHighlightedTextCell extends ListCell<String> {
        private static final String HIGHLIGHT_CLASS = "search-highlight";
        private final StringProperty searchText;

        SearchHighlightedTextCell(StringProperty searchText) {
            this.searchText = searchText;
        }
    }
}
