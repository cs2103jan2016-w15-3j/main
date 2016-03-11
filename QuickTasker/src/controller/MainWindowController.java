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
    ObservableList<Task> guiList = FXCollections.observableArrayList();
    
    
    // Display messages as visual feedback for users
    private static final String MESSAGE_WELCOME = "Welcome to quickTasker!";
    private static final String MESSAGE_ADD_CONFIRMED = "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCellFactory();
        setMain(main);
        
    }

    public void setMain(Main main) {
        this.main = main;
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
        } else if (parser.getCommand(userInput) == Commands.EXIT){
            operations.exit();
        }
    }

    private void deleteTask(String userInput) throws Exception {
        int taskIndex;
        taskIndex = parser.getTaskIndex(userInput);
        guiList.remove(taskIndex);
    //    updateList(guiList, taskIndex);
        refresh();
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
        String taskName;
        LocalDate startDate;
        LocalDate dueDate;
        taskName = parser.getTaskName(userInput);
        startDate = parser.getStartDate(userInput);
        dueDate = parser.getEndDate(userInput);
        Task newTask = new Task(taskName, startDate, dueDate);
        operations.addTask(newTask);
        guiList.add(newTask);
        refresh();
        commandBox.clear();
    }

    private boolean enterKeyIsPressed(KeyEvent event) {
        return KeyCode.ENTER.equals(event.getCode());
    }

    private void setCellFactory() {
        taskListView.setCellFactory(param -> new TaskListCell(guiList));
    }

}
