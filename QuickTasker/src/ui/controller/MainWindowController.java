package ui.controller;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import logic.Logic;
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
 * Author: A0133333U
 * <p>
 * todo: create more classes! too many classes in one?
 */

public class MainWindowController implements Initializable {

    private static Logger logger;
    //private data storage;
    private Main main;
    private ParserInterface parser = new UserInputParser();
    private Logic operations = new Logic();

    @FXML Label label;
    @FXML JFXTextField commandBox;
    @FXML JFXListView<Task> taskListView;
    private ObservableList<Task> guiList = FXCollections.observableArrayList();
    private ListChangeListener<? super Task> listener;

    // Display messages as visual feedback for users
    private static final String MESSAGE_WELCOME = "Welcome to quickTasker!";
    private static final String MESSAGE_ADD_CONFIRMED = "Task added to list.";
    private static final String MESSAGE_DELETE_CONFIRMED = "Task deleted from list.";
    private static final String MESSAGE_COMPLETED_CONFIRMED = "Task marked as completed.";
    private static final String MESSAGE_EDIT_CONFIRMED = "Task edited.";

    @Override public void initialize(URL location, ResourceBundle resources) {

        setCellFactory();
        setMain(main);
        logger = Logger.getLogger("MyLogger");
        logger.setLevel(Level.INFO);
        logger.log(Level.SEVERE, "Test logging");

    }

    void setMain(Main main) {
        this.main = main;
    }

    private boolean isEmptyInput(String input) {
        return input == null || input.isEmpty() || "".equals(input.trim());
    }

    private boolean enterKeyIsPressed(KeyEvent event) {
        return KeyCode.ENTER.equals(event.getCode());
    }

    @FXML private void handleEnterKeyPressed(KeyEvent event) throws Exception {
        String userInput = commandBox.getText();
        if (!isEmptyInput(userInput) && enterKeyIsPressed(event)) {
            performOperations(userInput);
            logger.log(Level.SEVERE, userInput);
        }
    }

    private void performOperations(String userInput) throws Exception {
        String taskName;
        LocalDate startDate;
        LocalDate dueDate;
        int taskIndex;
        if (parser.getCommand(userInput) == Commands.CREATE_TASK) {
            createTask(userInput);
        } else if (parser.getCommand(userInput) == Commands.DELETE_TASK) {
            deleteTask(userInput);
        } else if (parser.getCommand(userInput) == Commands.EXIT) {
            operations.exit();
            // } else if (parser.getCommand(userInput) == Commands.UPDATE_TASK) {
            //     updateTask(userInput);
        } else if (userInput.contains("edit")) {
            editTask(userInput);
        }else if (userInput.contains("1")){
            //taskListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

            Task task = guiList.get(0);
            task.setDone(true);
            System.out.println(task.isDone());
        }
        assert (false); // execution should not reach here
    }

    private void editTask(String userInput) throws Exception {
        int taskIndex = parser.getTaskIndex(userInput);
        ListCell<Task> listCell;
        guiList.addListener(listener);
        taskListView.getSelectionModel();
        taskListView.getFocusModel().focus(taskIndex);
        taskListView.scrollTo(taskIndex);
        // pass to parser --> logic -->
        refresh();
    }

    private void deleteTask(String userInput) throws Exception {
        int taskIndex;
        taskIndex = parser.getTaskIndex(userInput);
        guiList.remove(taskIndex);
        // updateList(guiList, taskIndex);
        refresh();
    }

    /**
     * Private void updateList(ObservableList<Task> list, int index) { for (int
     * i=index;i< list.size(); i++) { Task.decrementId(list.get(i)); } }
     **/
    private void refresh() {
        taskListView.setItems(guiList);
    }

    private void createTask(String userInput) throws Exception {
        String taskName;
        LocalDate startDate;
        LocalDate dueDate;
        taskName = parser.getTaskName(userInput);
        startDate = parser.getStartDate(userInput);
        dueDate = parser.getEndDate(userInput);
        Task newTask = new Task(taskName, startDate, dueDate);
        operations.addTask(newTask);
        guiList.add(newTask);
        taskListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Task>() {
            @Override
            public void changed(ObservableValue<? extends Task> observable, Task oldValue,
                    Task newValue) {
                System.out.println("changed");
            }
        });
        refresh();
        commandBox.clear();
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


