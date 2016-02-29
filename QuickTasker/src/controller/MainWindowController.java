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
import view.TaskListCell;

public class MainWindowController implements Initializable {
    private Main main;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCellFactory();
        setMain(main);
    }

    // Views
    /*
     * Author: 
     */
    @FXML Label label;
    @FXML JFXTextField commandBox;

    @FXML JFXListView<Task> taskListView;
    ObservableList<Task> observableList = FXCollections.observableArrayList();

    public void setMain(Main main) {
        this.main = main;
    }

    @FXML
    
    private void handleEnterKeyPressed(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            LocalDate startDate = LocalDate.now();
            LocalDate deadLine = LocalDate.now();
            String taskDescription = commandBox.getText(); 
            observableList.add(new Task(taskDescription, startDate, deadLine));
            taskListView.setItems(observableList);
            commandBox.clear();
        }
    }
    
    private void getCommandType(String commandString) {
        //InputParser parser = new Parser();
            // string commandType = parser.determineCommand(commandString); 
        // if command is "add"
          // private void addTask(){
          // String startDate = parser.getStartDate(commandString);
          // String deadLine = parser.getDeadLine(commandString);
          // String taskDetail = parser.getTaskDetail(commandString);
          // Task task = new Task(taskDetail, startDate, deadline);
          // logic.addTask(task); // main logic class add task into internal list and 
          // this.observableList = logic.getTasks() // returns list of task can be linkedlist, arraylist, map etc. anything
          // taskListview.setItems(observableList); // refresh ui
        //}
        
        // if command is "delete"
          // private void deleteTask(int num){
          //  int taskTodelete = parser.getTaskToDelete(String commandString):
          //  Task task = observableList.get(taskToDelete - 1); 
          //  logic.deleteTask(task) //think of what parameter is needed to locate a task in a list for deletion?
          //  this.observableList = logic.getTasks();
          //  taskListView.setItesm(observableList); refresh ui
        // if command is "update"
         // private void updateTask(){
         // similar to addTask
         // }
        // if command is "undo"
         // private void undo(){
          //  //this feature requires 
          //  logic.undoPrevious()// logic calls  
          //
        
        
    }
    

    private void setCellFactory() {
        taskListView.setCellFactory(param -> new TaskListCell());
    }

}
