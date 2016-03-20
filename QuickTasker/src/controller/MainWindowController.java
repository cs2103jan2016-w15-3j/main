package controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import logic.Logic;
import logic.UpdateTask;
import model.Task;
import parser.Commands;
import parser.ParserInterface;
import parser.UserInputParser;
import view.TaskListCell;

/**
 * Author: A0133333U
 */
public class MainWindowController implements Initializable {
    private Main main;
    private ParserInterface parser = new UserInputParser();
    private Logic operations = new Logic();
   

    @FXML Label label;
    @FXML JFXTextField commandBox;
    @FXML JFXListView<Task> taskListView;
    @FXML Label header;
    ObservableList<Task> guiList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCellFactory();
        setMain(main);
    }

    public void setMain(Main main) {
        this.main = main;
    }

    private boolean isEmptyInput(String input) {
        return input == null || input.isEmpty() || "".equals(input.trim());
    }

    @FXML
    private void handleEnterKeyPressed(KeyEvent event) throws Exception {
        String userInput = commandBox.getText();
        if (!isEmptyInput(userInput) && enterKeyIsPressed(event)) {
            performOperations(userInput);
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
        } else if (parser.getCommand(userInput) == Commands.UPDATE_TASK) {
            updateTask(userInput);
        } else if (parser.getCommand(userInput) == Commands.UNDO) {
            undoTask();
        } else if (parser.getCommand(userInput) == Commands.REDO) {
            redoTask();
        } else if (parser.getCommand(userInput) == Commands.EXIT){
            System.exit(0);
            operations.exit();
        }
    }

    private void redoTask() {
        guiList = FXCollections.observableArrayList(operations.redo());
        afterOperation();;
    }
    
    private void undoTask() {
        guiList = FXCollections.observableArrayList(operations.undo());
        afterOperation();;
    }
    
    private void updateTask(String userInput) throws Exception {
        Task newTask = new Task(parser.getTaskNameForUpdate(userInput), parser.getStartDateForUpdate(userInput), parser.getEndDateForUpdate(userInput));
        guiList = FXCollections.observableArrayList(operations.updateTask(newTask, parser.getIndexForUpdate(userInput)));
        afterOperation();
    }

    private void deleteTask(String userInput) throws Exception {
        int taskIndex;
        taskIndex = parser.getTaskIndex(userInput);
        guiList = FXCollections.observableArrayList(operations.deleteTask(taskIndex));
        afterOperation();
    }
 /**   private void updateList(ObservableList<Task> list, int index) {
        for (int i=index;i< list.size(); i++) {
            Task.decrementId(list.get(i));
        }
    }
**/
    private void refresh() {
        taskListView.setItems(guiList);
    }

    private void createTask(String userInput) throws Exception {
        Task newTask = makeTask(parser.getTaskName(userInput), parser.getStartDate(userInput), parser.getEndDate(userInput));
        guiList = FXCollections.observableArrayList(operations.addTask(newTask));
        afterOperation();
    }

    private void afterOperation() {
        setCellFactory();
        refresh();
        commandBox.clear();
    }

    private Task makeTask(String taskName, LocalDate startDate, LocalDate dueDate) throws Exception {
        Task newTask = new Task(taskName, startDate, dueDate);
        return newTask;
    }

    private boolean enterKeyIsPressed(KeyEvent event) {
        return KeyCode.ENTER.equals(event.getCode());
    }

    private void setCellFactory() {
        taskListView.setCellFactory(param -> new TaskListCell(guiList));
    }

}
