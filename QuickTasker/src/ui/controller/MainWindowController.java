package ui.controller;

import com.jfoenix.controls.JFXBadge;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import common.InvalidStringException;
import common.UIOperationException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logic.Logic;
import model.RecurringTask;
import model.Task;
import parser.*;
import ui.model.TaskListCell;

import java.io.IOException;
import java.net.URL;
import java.util.EmptyStackException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ui.controller.TaskDoneEvent.TASK_COMPLETE;

// @@author A0126077E

public class MainWindowController implements Initializable {

    static Logger logger;
    private final UiOperationDelegator uiOperationDelegator = new UiOperationDelegator(this);
    private Main main;
    private Stage stage;
    private final UserInputParser parser = new UserInputParser();
    private final UpdateParser updateParser = new UpdateParser();
    private final RecurringParser recurringParser = new RecurringParser();
    private final DirectoryParser directoryParser = new DirectoryParser();
    private final SearchParser searchParser = new SearchParser();
    private final InputValidator inputValidator = new InputValidator();
    private final Logic operations = new Logic();
    private SearchHelper search = new SearchHelper();

    @FXML private AnchorPane mainContentContainer;
    @FXML private JFXBadge tasksCounter;
    @FXML private JFXTextField commandBox;
    @FXML private JFXListView<Task> printedPlanner;

    @FXML private JFXSnackbar snackbar;
    @FXML private Label headerTitle;

    private ObservableList<Task> plannerEntries;
    private ObservableList<Task> filteredEntries;

    private Stage helpStage;

    /**
     * Display messages as visual feedback for users.
     */
    // @@author A0133333U
    private static final String MESSAGE_ADD_CONFIRMED = "Your task is added to QuickTasker.";
    private static final String MESSAGE_DELETE_CONFIRMED = "Your task is deleted from QuickTasker.";
    private static final String MESSAGE_EDIT_CONFIRMED = "Your task is updated in QuickTasker.";
    private static final String MESSAGE_FOR_DATE_CHANGE = "Dates updated in QuickTasker";
    // @@author A0130949Y
    private static final String MESSAGE_FOR_CLEARING = "All tasks are removed from QuickTasker.";
    private static final String MESSAGE_FOR_STOPPING_RECUR = "index %d is not recurring now";
    private static final String MESSAGE_FOR_UNDO = "Undone last operation. Yay!";
    private static final String MESSAGE_FOR_REDO = "Redo the last undo. Yay!";
    private static final String MESSAGE_FOR_CLASHING_TIME_SLOTS = "WARNING: YOU HAVE CLASHING TIME SLOTS";
    private static final String ERROR_MESSAGE_FOR_WRONG_INDEX = "The index you entered is invalid!";
    private static final String ERROR_MESSAGE_FOR_INVALID_INDEX = "This index is not a number!";
    private static final String ERROR_MESSAGE_FOR_NOT_RECURRING_TASK = "This index is not a recurring task!";
    private static final String ERROR_MESSAGE_FOR_NO_TASK_ENTERED = "Did you enter a recurring task?";
    private static final String ERROR_MESSAGE_FOR_EMPTY_TASK = "Did you enter a task correctly?";
    private static final String ERROR_MESSAGE_FOR_REDO_ERROR = "Did you undo before this?";
    private static final String ERROR_MESSAGE_FOR_UNDO_ERROR = "No operations to undo before this.";

    private static final String ERROR_MESSAGE_FOR_INVALID_INPUT = "Invalid Input. Please retype.";

    public MainWindowController() {}

    public static Logger getLogger() {
        return logger;
    }

    @Override
    public void initialize(URL location, ResourceBundle b) {
        initLogger();
        initPlanner();
        setCellFactory();
    }

    // @@author A0133333U
    private void initLogger() {
        logger = Logger.getLogger("UILogger");
        logger.setLevel(Level.INFO);
    }

    // @@author A0126077E
    private void initPlanner() {
        plannerEntries = FXCollections.observableArrayList(operations.getTasks());
        printedPlanner.setItems(plannerEntries);
        printedPlanner.setDepthProperty(1);
        snackbar.registerSnackbarContainer(mainContentContainer);
        tasksCounter.setText(plannerEntries.size() + "");
        commandBox.requestFocus();
    }

    // @@author A0126077E
    void setStage(Stage s) {
        this.stage = s;
    }

    public void setMain(Main m) {
        this.main = m;
    }

    // this method will return a boolean value of true or false, depending on
    // the input
    // @@author A0133333U
    protected boolean isEmptyInput(String input) {
        return input == null || input.isEmpty() || "".equals(input.trim());
    }

    // @@author A0126077E
    private boolean enterKeyIsPressed(KeyEvent e) {
        return KeyCode.ENTER.equals(e.getCode());
    }

    // This method will log the user input, as well as passes valid input to the performOperations method
    // @@author A0126077E
    @FXML
    private void handleEnterKeyPressed(KeyEvent event) {
        String userInput = commandBox.getText();
        if (isEmptyInput(userInput) || !enterKeyIsPressed(event)) return;
        logger.log(Level.INFO, "User typed in : <" + userInput + "> command string");
        try {
            uiOperationDelegator.performOperations(userInput);
        } catch (UIOperationException e) {
            logger.log(Level.SEVERE,
                    "Error occured at " + getClass().getName() + " within performOperation method.\n");
            e.printStackTrace();
        } catch (InvalidStringException e) {
            displayMessage(ERROR_MESSAGE_FOR_INVALID_INPUT);
        }
    }

    //@@author A0121558H
    protected void showTasks(String userInput) {
        String whatToShow = determineShow(userInput);
        if (whatToShow.equals("all")) {
            showAll();
        } else if (whatToShow.equals("today")) {
            showToday();
        } else if (whatToShow.equals("tomorrow")) {
            showTomorrow();
        } else if (whatToShow.equals("floating")) {
            showFloating();
        } else if (whatToShow.equals("monday")) {
            showMonday();
        } else if (whatToShow.equals("tuesday")) {
            showTuesday();
        } else if (whatToShow.equals("wednesday")) {
            showWednesday();
        } else if (whatToShow.equals("thursday")) {
            showThursday();
        } else if (whatToShow.equals("friday")) {
            showFriday();
        } else if (whatToShow.equals("saturday")) {
            showSaturday();
        } else if (whatToShow.equals("sunday")) {
            showSunday();
        } else if (whatToShow.equals("overdue")) {
            showOverdue();
        } else {
            viewArchived();
        }
    }

    private String determineShow(String input) {
        String[] withoutWhiteSpaces = input.split("\\s+");
        return withoutWhiteSpaces[1];
    }

    // @@author A0126077E
    void changeTheme(String userInput) {
        Scene scene = main.getScene();
        ThemeChanger themer = new ThemeChanger();
        Platform.runLater(() -> {
            try {
                themer.change(userInput, scene);
                commandBox.clear();
            } catch (Exception e) {
                logger.warning("Thread Exception occurred trying to set element property" + e.toString());
            }
        });
    }

    // @@author A0126077E
    void showTomorrow() {
        printedPlanner.setItems(plannerEntries.filtered(task -> search.isItDisplayedInTomorrowView(task)));
        headerTitle.setText("Tasks: Tomorrow");
        updateTaskCounter();
        commandBox.clear();
    }

    // @@author A0126077E
    private void updateTaskCounter() {
        tasksCounter.setText(printedPlanner.getItems().size() + "");
    }

    // @@author A0126077E
    void showFloating() {
        printedPlanner.setItems(plannerEntries.filtered(task -> search.isFloatingTask(task)));
        headerTitle.setText("Tasks: Floating");
        updateTaskCounter();
        commandBox.clear();
    }

    void showAll() {
        plannerEntries = FXCollections.observableArrayList(operations.getTasks());
        afterOperation();
        headerTitle.setText("Tasks: All");
        setGenericIcon();
        updateTaskCounter();
        commandBox.clear();
    }

    // @@author A0126077E
    void showToday() {

        printedPlanner.setItems(plannerEntries.filtered(task -> search.isItDisplayedInTodayView(task)));
        headerTitle.setText("Tasks: Today + Floating");
        setGenericIcon();
        updateTaskCounter();
        commandBox.clear();
    }

    void searchTask(String userInput) throws Exception {

        if (search.isKeywordSearch()) {
            String taskName = parser.getTaskName(userInput);
            headerTitle.setText("Search Results  \"" + taskName + "\":");
            printedPlanner.setItems(plannerEntries.filtered(task -> search.containsKeyWord(task, taskName)));
            updateTaskCounter();
        }

        commandBox.clear();
    }

    // @@author A0130949Y
    // display tasks on monday
    void showMonday() {
        printedPlanner.setItems(plannerEntries.filtered(task -> search.isTaskDueOnMonday(task)));
        headerTitle.setText("Tasks: Monday");
        setUpDisplay();
    }

    // @@author A0130949Y
    private void setUpDisplay() {
        setGenericIcon();
        updateTaskCounter();
        commandBox.clear();
    }

    // @@author A0130949Y
    void showTuesday() {
        printedPlanner.setItems(plannerEntries.filtered(task -> search.isTaskDueOnTuesday(task)));
        headerTitle.setText("Tasks: Tuesday");
        setUpDisplay();
    }

    // @@author A0130949Y
    void showWednesday() {
        printedPlanner.setItems(plannerEntries.filtered(task -> search.isTaskDueOnWednesday(task)));
        headerTitle.setText("Tasks: Wednesday");
        setUpDisplay();
    }

    // @@author A0130949Y
    void showThursday() {
        printedPlanner.setItems(plannerEntries.filtered(task -> search.isTaskDueOnThursday(task)));
        headerTitle.setText("Tasks: Thursday");
        setUpDisplay();
    }

    // @@author A0130949Y
    void showFriday() {
        printedPlanner.setItems(plannerEntries.filtered(task -> search.isTaskDueOnFriday(task)));
        headerTitle.setText("Tasks: Friday");
        setUpDisplay();
    }

    // @@author A0130949Y
    void showSaturday() {
        printedPlanner.setItems(plannerEntries.filtered(task -> search.isTaskDueOnSaturday(task)));
        headerTitle.setText("Tasks: Saturday");
        setUpDisplay();
    }

    // @@author A0130949Y
    void showSunday() {
        printedPlanner.setItems(plannerEntries.filtered(task -> search.isTaskDueOnSunday(task)));
        headerTitle.setText("Tasks: Sunday");
        setUpDisplay();
    }

    //@@author A0133333U Display a Help popup when user types in 'help'.

    void showHelp() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Help.fxml"));
        Scene helpScene = new Scene(root);
        Stage helpStage = new Stage();
        helpScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.ESCAPE) {
                    helpStage.close();
                }
            }
        });
        helpStage.setTitle("QuickTasker Help");
        helpStage.getIcons().add(new Image("img/help.png"));
        helpStage.initModality(Modality.APPLICATION_MODAL);
        helpStage.setScene(helpScene);
        helpStage.setResizable(false);
        helpStage.show();
        commandBox.clear();
    }

    //@@author A0133333U
    // this method will set the generic task icon in the header titles, except in overdue
    protected void setGenericIcon() {
        Image image = new Image(getClass().getResourceAsStream("/img/task-icon.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(25);
        imageView.setFitWidth(25);
        imageView.setPreserveRatio(true);
        headerTitle.setGraphic(imageView);
    }

    // @@author A0133333U
    // sets different icons for header title of 'archived'
    protected void setArchivedIcon() {
        Image image = new Image(getClass().getResourceAsStream("/img/archived.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(25);
        imageView.setFitWidth(25);
        imageView.setPreserveRatio(true);
        headerTitle.setGraphic(imageView);
    }

    // @@author A0133333U
    // this method will set and assign the warning icon to the header title when overdue tasks are shown
    private void setWarningIcon() {
        Image warningImage = new Image(getClass().getResourceAsStream("/img/warning.png"));
        ImageView imageWarning = new ImageView(warningImage);
        imageWarning.setFitHeight(25);
        imageWarning.setFitWidth(25);
        imageWarning.setPreserveRatio(true);
        headerTitle.setGraphic(imageWarning);
    }

    //@@author A0133333U
    // method displays list of overdue items
    void showOverdue() {
        printedPlanner.setItems(plannerEntries.filtered(task -> search.isTaskOverdue(task)));
        headerTitle.setText("Tasks: Overdue");
        setWarningIcon();
        updateTaskCounter();
        commandBox.clear();
    }

    // @@author A0130949Y
    void markTaskCompleted(String userInput) throws Exception {
        try {
            int i = parser.getIndexForDone(userInput);
            Task task = plannerEntries.get(i);
            printedPlanner.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            printedPlanner.getSelectionModel().select(i);
            printedPlanner.fireEvent(new TaskDoneEvent(task));
        } catch (NumberFormatException e) {
            displayMessage(ERROR_MESSAGE_FOR_WRONG_INDEX);
        } catch (IndexOutOfBoundsException e) {
            displayMessage(ERROR_MESSAGE_FOR_INVALID_INDEX);
        }
    }

    // @@author A0126077E
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

    // @@author A0130949Y
    void viewArchived() {
        plannerEntries = FXCollections.observableArrayList(operations.getArchivedTasks());
        afterOperation();
        headerTitle.setText("Tasks: Archived");
        setArchivedIcon();
    }

    // @@author A0130949Y
    void clearTasks() {
        plannerEntries = FXCollections.observableArrayList(operations.clear());
        afterOperation();
        displayMessage(MESSAGE_FOR_CLEARING);
    }

    // @@author A0130949Y
    // skip 1 iteration for that recurring task in that index
    void skipRecurringTask(String userInput) throws Exception {
        try {
            skipRecurringTaskOperation(userInput);
            afterOperation();
        } catch (IndexOutOfBoundsException e) {
            displayMessage(ERROR_MESSAGE_FOR_WRONG_INDEX);
        } catch (NumberFormatException e) {
            displayMessage(ERROR_MESSAGE_FOR_INVALID_INDEX);
        }
    }

    // @@author A0130949Y
    private void skipRecurringTaskOperation(String userInput) {
        int index = parser.getTaskIndex(userInput);
        if (!(plannerEntries.get(index) instanceof RecurringTask))
            displayMessage(ERROR_MESSAGE_FOR_NOT_RECURRING_TASK);
        else {
            plannerEntries = FXCollections.observableArrayList(operations.skip(index));
            displayMessage(MESSAGE_FOR_DATE_CHANGE);
        }
    }
    // @@author A0130949Y

    private static final int OFFSET = 1;

    void stopRecurringTask(String userInput) throws Exception {
        int taskIndex = parser.getTaskIndex(userInput);
        if (plannerEntries.get(taskIndex) instanceof RecurringTask) {
            plannerEntries = FXCollections.observableArrayList(operations.stopRecurring(taskIndex));
            displayMessage(String.format(MESSAGE_FOR_STOPPING_RECUR, taskIndex + OFFSET));
        } else {
            displayMessage(ERROR_MESSAGE_FOR_NOT_RECURRING_TASK);
        }
        afterOperation();
    }

    // @@author A0130949Y
    void redoTask() {
        try {
            plannerEntries = FXCollections.observableArrayList(operations.redo());
            displayMessage(MESSAGE_FOR_REDO);
            afterOperation();
        } catch (EmptyStackException e) {
            displayMessage(ERROR_MESSAGE_FOR_REDO_ERROR);
        }
    }

    // @@author A0130949Y
    void undoTask() {
        try {
            plannerEntries = FXCollections.observableArrayList(operations.undo());
            displayMessage(MESSAGE_FOR_UNDO);
            afterOperation();
        } catch (EmptyStackException e) {
            displayMessage(ERROR_MESSAGE_FOR_UNDO_ERROR);
        }
    }

    // @@author A0130949Y
    void updateTask(String userInput) throws Exception {
        try {
            updateTaskOperation(userInput);
            afterOperation();
            displayMessage(MESSAGE_EDIT_CONFIRMED);
        } catch (IndexOutOfBoundsException e) {
            displayMessage(ERROR_MESSAGE_FOR_WRONG_INDEX);
        } catch (NumberFormatException e) {
            displayMessage(ERROR_MESSAGE_FOR_INVALID_INDEX);
        }
    }

    // @@author A0130949Y
    private void updateTaskOperation(String userInput) {
        int indexOfTask = updateParser.getTaskIndex(userInput);
        printedPlanner.getSelectionModel().select(indexOfTask);
        Task newTask = makeTaskForUpdate(userInput);
        plannerEntries = FXCollections.observableArrayList(operations.updateTask(newTask, indexOfTask));
        printedPlanner.getSelectionModel().clearSelection();
    }

    // @@author A0130949Y
    void deleteTask(String userInput) throws Exception {
        try {
            deleteTaskOperation(userInput);
            afterOperation();
            displayMessage(MESSAGE_DELETE_CONFIRMED);
        } catch (NumberFormatException e) {
            displayMessage(ERROR_MESSAGE_FOR_INVALID_INDEX);
        } catch (IllegalArgumentException e) {
            displayMessage(ERROR_MESSAGE_FOR_WRONG_INDEX);
        } catch (IndexOutOfBoundsException e) {
            displayMessage(ERROR_MESSAGE_FOR_WRONG_INDEX);
        }
    }

    // @@author A0130949Y
    private void deleteTaskOperation(String userInput) {
        plannerEntries = FXCollections
                .observableArrayList(operations.deleteTask(parser.getTaskIndex(userInput)));
    }

    // @@author A0126077E
    void changeDirectory(String userInput) throws Exception {
        operations.changeDir(directoryParser.getFilePath(userInput));
        afterOperation();
    }

    // @@author A0130949Y
    void addTask(String userInput) throws Exception {
        try {
            addTaskOperation(userInput);
            afterOperation();
            displayMessage(MESSAGE_ADD_CONFIRMED);
            printedPlanner.scrollTo(printedPlanner.getItems().size() - 1);
        } catch (IndexOutOfBoundsException e) {
            displayMessage(ERROR_MESSAGE_FOR_EMPTY_TASK);
        }
    }

    // @@author A0130949Y
    private void addTaskOperation(String userInput) throws Exception {
        Task newTask = makeTask(userInput);
        //if (isTimeSlotClashing(newTask)) displayMessage(MESSAGE_FOR_CLASHING_TIME_SLOTS);
        plannerEntries = FXCollections.observableArrayList(operations.addTask(newTask));
    }

    // @@author A0130949Y
    void addRecurringTask(String userInput) throws Exception {
        try {
            addRecurringTaskOperation(userInput);
            afterOperation();
            displayMessage(MESSAGE_ADD_CONFIRMED);
        } catch (IndexOutOfBoundsException e) {
            displayMessage(ERROR_MESSAGE_FOR_NO_TASK_ENTERED);
        } catch (NumberFormatException e) {
            displayMessage(ERROR_MESSAGE_FOR_NO_TASK_ENTERED);
        } catch (NullPointerException e) {
            displayMessage(ERROR_MESSAGE_FOR_EMPTY_TASK);
        }
    }

    // @@author A0130949Y
    private void addRecurringTaskOperation(String userInput) throws Exception {
        RecurringTask newTask = makeRecurringTask(userInput);
        //if (isTimeSlotClashing(newTask)) displayMessage(MESSAGE_FOR_CLASHING_TIME_SLOTS);
        plannerEntries = FXCollections.observableArrayList(operations.addTask(newTask));
    }

    // @@author A0130949Y
    // clear the command box and set up new list for view
    private void afterOperation() {
        setCellFactory();
        refresh();
        updateTaskCounter();
        commandBox.clear();
    }

    // @@author A0130949Y
    private Task makeTask(String userInput) throws Exception {
        return new Task(parser.getTaskName(userInput), parser.getStartDate(userInput),
                parser.getEndDate(userInput), parser.getStartTime(userInput), parser.getEndTime(userInput));
    }

    // @@author A0130949Y
    private RecurringTask makeRecurringTask(String userInput) throws Exception {
        return new RecurringTask(recurringParser.getTaskName(userInput),
                recurringParser.getTaskStartDate(userInput), recurringParser.getTaskEndDate(userInput),
                recurringParser.getRecurDuration(userInput), recurringParser.getTaskStartTime(userInput),
                recurringParser.getTaskEndTime(userInput), recurringParser.getNumToRecur(userInput));
    }

    // @@author A0130949Y
    private Task makeTaskForUpdate(String userInput) {
        return new Task(updateParser.getTaskName(userInput), updateParser.getStartDate(userInput),
                updateParser.getEndDate(userInput), updateParser.getStartTime(userInput),
                updateParser.getEndTime(userInput));
    }

    // @@author A0130949Y
    private static final int OFFSET_FOR_CHECKING_TIME = 1;

    // @@author A0126077E
    private void displayMessage(String message) {
        snackbar.fireEvent(new JFXSnackbar.SnackbarEvent(message, "", 1500, (b) -> {
        }));
    }

    // @@author A0126077E
    private void refresh() {
        printedPlanner.setItems(plannerEntries);
    }

    // @@author A0126077E
    private void setCellFactory() {
        printedPlanner.setCellFactory(param -> {
            TaskListCell listCell = new TaskListCell();
            printedPlanner.addEventFilter(TASK_COMPLETE, event -> new Thread(() -> {
                Thread.currentThread().setUncaughtExceptionHandler((task, e) -> Platform.runLater(
                        () -> logger.info("JFonix library throws exception when accessing checkbox from different thread.\n"
                                + " Would be fixed when library release new version")));
                fireCheckBoxAndRemoveEventFilter(listCell, event);
            }).start());
            return listCell;
        });
    }

    private void fireCheckBoxAndRemoveEventFilter(TaskListCell c, TaskDoneEvent e) {
        if (listCellContainsSelectedTask(c, e)) {
            c.getCheckBox().fire();
            javafx.concurrent.Task<Void> sleeper = makeSleeper(500);
            sleeper.setOnSucceeded(evt -> {
                printedPlanner.getSelectionModel().clearSelection();
                plannerEntries.remove(e.getTask());
                operations.markAsDone(e.getTask().getId());
                commandBox.clear();
                afterOperation();
            });
            new Thread(sleeper).start();
        }
        c.removeEventFilter(TASK_COMPLETE, null);
    }

    private boolean listCellContainsSelectedTask(TaskListCell c, TaskDoneEvent e) {
        return c.getTask().equals(e.getTask());
    }

    public Logic getOperations() {
        return operations;
    }

    public UserInputParser getParser() {
        return parser;
    }
}
