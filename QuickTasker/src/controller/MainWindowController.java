package controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.sun.istack.internal.FinalArrayList;
import com.sun.org.glassfish.external.statistics.Statistic;
import com.sun.xml.internal.fastinfoset.algorithm.IEEE754FloatingPointEncodingAlgorithm;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import logic.Logic;
import dao.*;
import model.Task;
import parser.Commands;
import parser.ParserInterface;
import parser.UserInputParser;
import view.TaskListCell;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.AssertionError;

/**
 * Author: A0133333U
 * 
 * todo: create more classes! too many classes in one?
 */
public class MainWindowController implements Initializable {
    
    private static Logger logger;
    //private dao storage;
    private Main main;
    private ParserInterface parser = new UserInputParser();
    private Logic operations = new Logic();
   
    @FXML Label label;
    @FXML JFXTextField commandBox;
    @FXML JFXListView<Task> taskListView;
    ObservableList<Task> guiList = FXCollections.observableArrayList();
    ListChangeListener<? super Task> listener;
    
   
    
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
        logger = Logger.getLogger("MyLogger");
        logger.setLevel(Level.INFO);
        logger.log(Level.SEVERE, "Test logging"); 

    }

    public void setMain(Main main) {
        this.main = main;
    }

    private boolean isEmptyInput(String input) {
        return input == null || input.isEmpty() || "".equals(input.trim());
    }
    
    private boolean enterKeyIsPressed(KeyEvent event) {
        return KeyCode.ENTER.equals(event.getCode());
    }

    @FXML
    private void handleEnterKeyPressed(KeyEvent event) throws Exception {

        String userInput = commandBox.getText();
        if (!isEmptyInput(userInput) && enterKeyIsPressed(event)) {
            performOperations(userInput);
            logger.log(Level.SEVERE, userInput);
        }
    }

    private void performOperations(String userInput) throws Exception  {
        String taskName;
        LocalDate startDate;
        LocalDate dueDate;
        int taskIndex;
        if (parser.getCommand(userInput) == Commands.CREATE_TASK) {
            createTask(userInput);
        } else if (parser.getCommand(userInput) == Commands.DELETE_TASK) {
            deleteTask(userInput);
       // } else if (parser.getCommand(userInput) == Commands.UPDATE_TASK) {
       //     updateTask(userInput);
        } else if (userInput.contains("edit")) {
            editTask(userInput);
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
        refresh();
    }

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
    
   


    private void setCellFactory() {
        taskListView.setCellFactory(param -> new TaskListCell());
    }
    
  class SearchHighlightedTextCell extends ListCell<String> {
      private static final String HIGHLIGHT_CLASS = "search-highlight";
      private final StringProperty searchText;
      SearchHighlightedTextCell(StringProperty searchText) {
          this.searchText = searchText;
  }
  }
}


