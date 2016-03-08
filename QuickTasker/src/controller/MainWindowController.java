package controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.sun.javafx.scene.paint.GradientUtils.Parser;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Task;
import view.TaskListCell;
import logic.*;
import parser.UserInputParser;


public class MainWindowController implements Initializable {
    private Main main;
    private ParserInterface parser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCellFactory();
        setMain(main);
    }

    /* Views */
    @FXML Label label;
    @FXML JFXTextField commandBox;
    @FXML JFXListView<Task> taskListView;
    ObservableList<Task> list = FXCollections.observableArrayList();

    public void setMain(Main main) {
        this.main = main;
    }

    @FXML
    private void handleEnterKeyPressed(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            if (!isEmptyInput(commandBox.getText())) {
            LocalDate startDate = LocalDate.now();
            LocalDate deadLine = LocalDate.now();
            String userInput = commandBox.getText();
            UserInputParser parser = new UserInputParser();
            Command<String> command = parser.getCommand(userInput);
            if (command.equals("add")) {
                list.add(new Task(parser.getTaskName(userInput), startDate, deadLine));
            }
            taskListView.setItems(list);
            commandBox.clear();
            String commandStrin = commandBox.getText();
            }
        }      
    }
    
    @FXML
    private void handleDeleteTask(KeyEvent event) {
        if (event.get)
    }
    
    private boolean isEmptyInput(String input){
        if(input == null || input.isEmpty() || input.trim().equals("")) {
            return true;
        }
        return false;
    }
 
    private void setCellFactory() {
        taskListView.setCellFactory(param -> new TaskListCell());
    }

}
