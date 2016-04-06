package ui.controller;

import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.Logic;
import model.RecurringTask;
import model.Task;
import parser.Commands;
import parser.ParserInterface;
import parser.RecurringParser;
import parser.UserInputParser;
import ui.model.ApplicationColor;
import ui.model.TaskListCell;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ui.controller.TaskDoneEvent.TASK_COMPLETE;

/**
 * Author: A0133333U/A0130949Y/A0126077E
 */
public class MainWindowController implements Initializable {

    private static Logger logger;

    private Main main;
    private Stage stage;
    private final ParserInterface parser = new UserInputParser();
    private RecurringParser recurringParser = new RecurringParser();
    private ListView<String> ListViewOnlySearch = new ListView<>();
    private final Logic operations = new Logic();
    private SearchHelper util = new SearchHelper();
    @FXML
    JFXBadge tasksCounter;
    @FXML
    public AnchorPane root;
    @FXML
    private JFXTextField commandBox;
    @FXML
    private JFXListView<Task> printedPlanner;
    @FXML
    private StackPane mainContent;
    @FXML
    private JFXSnackbar snackbar;
    @FXML
    private AnchorPane commandBoxContainer;
    @FXML
    private VBox headerWrapper;
    @FXML
    private JFXToolbar headerContainer;
    @FXML
    private JFXRippler headerTitleContainer;
    @FXML
    private Label headerTitle;

    private ObservableList<Task> plannerEntries;


    // Display messages as visual feedback for users
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
        snackbar.registerSnackbarContainer(mainContent);
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
                        "Error occured at " + this.getClass().getName() + " within performOperation method.\n");
                e.printStackTrace();

            }
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
                sortTask(userInput);
/*            } else if (userInput.contains("edit")) {
                editTask(userInput);*/
            } else if (parser.getCommand(userInput) == Commands.MARK) {
                markTaskCompleted(userInput);
            } else if (parser.getCommand(userInput) == Commands.RECUR_TASK) {
                createRecurringTask(userInput);
            } else if (parser.getCommand(userInput) == Commands.SKIP) {
                skipRecurringTask(userInput);
            } else if (userInput.contains("clear")) {
                clearTasks(userInput);
            } else if (userInput.contains("stop")) {
                stopRecurringTask(userInput);
            } else if (userInput.equals("show today") || userInput.equals("view today")) {
                showToday();
            } else if (userInput.equals("show tomorrow") || userInput.equals("view tomorrow")) {
                showTomorrow();
                showAll();
            } else if (userInput.equals("view floating") || userInput.equals("show floating")) {
                showFloating();
            } else if (userInput.equals("show all") || userInput.equals("view all")) {
                showAll();
            } else if (parser.getCommand(userInput) == Commands.SEARCH_TASK) {
                searchTask(userInput);
            } else if (userInput.equals("hi")) {
                Platform.runLater(() -> {
                    try {
                        main.getDecorator().setStyle("-fx-decorator-color: derive(" + ApplicationColor.BLUE.getHexCode() + ",-20%);");
                        headerContainer.setStyle("-fx-background-color: " + ApplicationColor.BLUE.getHexCode());
                        System.out.println(tasksCounter.getChildren());
                        commandBox.clear();
                        JFXDatePicker datePicker = new JFXDatePicker();

                        datePicker.show();
                        //an event with a button maybe
                    } catch (Exception e) {
                    }
                });
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

    // to be moved inside serach helper when tidy up
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

    private void markTaskCompleted(String userInput) throws Exception {
        int i = parser.getIndexForDone(userInput);
        Task task = plannerEntries.get(i);
        operations.markAsDone(i);
        task.setDone(true); // logic should handle
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

    private void sortTask(String userInput) {
        System.out.println("Sorting");
        plannerEntries = FXCollections.observableArrayList(operations.sort());
        afterOperation();
    }

    private void clearTasks(String userInput) {
        plannerEntries = FXCollections.observableArrayList(operations.clear());
        afterOperation();
    }

    private void skipRecurringTask(String userInput) throws Exception {
        plannerEntries = FXCollections.observableArrayList(operations.skip(parser.getTaskIndex(userInput)));
        afterOperation();
    }

    private void stopRecurringTask(String userInput) throws Exception {
        operations.stopRecurring(parser.getTaskIndex(userInput));
        afterOperation();
    }

    private void redoTask() {
        plannerEntries = FXCollections.observableArrayList(operations.redo());
        afterOperation();
    }

/*	// @@author: A0133333U
    // Takes in user input and shows display for all tasks with search term
	// inside on a new list
	private void searchTask(String userInput) {
		for (Task task : plannerEntries) {
			String taskDescription = task.getName().toLowerCase();
			LocalDate taskStartDate = task.getStartDate();
			LocalDate taskEndDate = task.getDueDate();
			if (taskDescription.contains(userInput.toLowerCase())) {
				plannerEntries.clear();
				plannerEntries.add(task);
			} else {
				snackbar.fireEvent(new JFXSnackbar.SnackbarEvent("Invalid search term!", "", 1500, (b) -> {
				}));
			}
		afterOperation();
		snackbar.fireEvent(new JFXSnackbar.SnackbarEvent("Search results returned.", "", 1500, (b) -> {
			}));
		}
	}*/

    private void undoTask() {
        plannerEntries = FXCollections.observableArrayList(operations.undo());
        afterOperation();
    }

    /*    private void searchingTask(String userInput) {
            for (Task task : plannerEntries) {
                String taskDescription = task.getName();
                LocalDate taskStartDate = task.getStartDate();
                LocalDate taskEndDate = task.getDueDate();
                if (taskDescription.contains(userInput.toLowerCase())) {
                    //filteredEntries.clear();
                    filteredEntries.add(task);
                    System.out.println("filteredlist" + filteredEntries.size());
                    snackbar.fireEvent(new JFXSnackbar.SnackbarEvent("Search results returned.", "", 1500, (b) -> {
                    }));
                } else {
                    snackbar.fireEvent(new JFXSnackbar.SnackbarEvent("Invalid search term!", "", 1500, (b) -> {
                    }));

                }
            afterSearch();

            }
        }
        */
    private void showTasks() {

    }

    private void updateTask(String userInput) throws Exception {
        int indexOfTask = parser.getIndexForUpdate(userInput);
        printedPlanner.getSelectionModel().select(indexOfTask);
        Task newTask = makeTask(parser.getTaskNameForUpdate(userInput), parser.getStartDateForUpdate(userInput),
                parser.getEndDateForUpdate(userInput), parser.getStartTimeForUpdate(userInput),
                parser.getEndTimeForUpdate(userInput));
        /*
		 * plannerEntries.remove(indexOfTask);
		 * plannerEntries.add(indexOfTask,newTask);
		 */

        plannerEntries = FXCollections.observableArrayList(operations.updateTask(newTask, indexOfTask));
        printedPlanner.getSelectionModel().clearSelection();
        afterOperation();
        snackbar.fireEvent(new JFXSnackbar.SnackbarEvent("Task updated.", "", 1500, (b) -> {
        }));
    }

    private void deleteTask(String userInput) throws Exception {
        int taskIndex;
        taskIndex = parser.getTaskIndex(userInput);
        // plannerEntries.remove(taskIndex);
        plannerEntries = FXCollections.observableArrayList(operations.deleteTask(taskIndex));
        afterOperation();
        snackbar.fireEvent(new JFXSnackbar.SnackbarEvent("Task Removed.", "", 1500, (b) -> {
        }));

    }

    private void refresh() {
        printedPlanner.setItems(plannerEntries);
    }

    private void createTask(String userInput) throws Exception {
        Task newTask = makeTask(parser.getTaskName(userInput), parser.getStartDate(userInput),
                parser.getEndDate(userInput), parser.getStartTime(userInput), parser.getEndTime(userInput));
        snackbar.fireEvent(new JFXSnackbar.SnackbarEvent("New task created", "", 1500, (b) -> {
        }));

		/*
		 * plannerEntries.add(newTask); printedPlanner.setItems(plannerEntries);
		 * commandBox.clear(); operations.addTask(newTask);
		 */
        plannerEntries = FXCollections.observableArrayList(operations.addTask(newTask));
        afterOperation();
        printedPlanner.scrollTo(printedPlanner.getItems().size() - 1);
    }

    private void createRecurringTask(String userInput) throws Exception {
        RecurringTask newTask = makeRecurringTask(recurringParser.getTaskName(userInput),
                recurringParser.getTaskStartDate(userInput), recurringParser.getTaskEndDate(userInput),
                recurringParser.getRecurDuration(userInput), recurringParser.getTaskStartTime(userInput),
                recurringParser.getTaskEndTime(userInput), recurringParser.getNumToRecur(userInput));
        plannerEntries = FXCollections.observableArrayList(operations.addTask(newTask));
        afterOperation();
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

    private RecurringTask makeRecurringTask(String taskName, LocalDate startDate, LocalDate dueDate, String type,
                                            LocalTime startTime, LocalTime endTime, int numberToRecur) throws Exception {
        return new RecurringTask(taskName, startDate, dueDate, type, startTime, endTime, numberToRecur);
    }

    private void setCellFactory() {
        printedPlanner.setCellFactory(param -> {

            TaskListCell listCell = new TaskListCell(plannerEntries);
            printedPlanner.addEventFilter(TASK_COMPLETE, event -> new Thread(() -> {
                Thread.currentThread().setUncaughtExceptionHandler((t, e) -> Platform.runLater(System.out::println));
                if (listCell.getTask().equals(event.getTask()))
                    listCell.getCheckBox().fire();
                listCell.removeEventFilter(TASK_COMPLETE, null);
            }).start());
			/*
			 * plannerEntries.addListener(new ListChangeListener<Task>() {
			 * 
			 * @Override public void onChanged(Change<? extends Task> c) {
			 * listCell.updateIndex(plannerEntries); } });
			 */
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
