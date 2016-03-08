package controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.PrimitiveIterator.OfDouble;

import javax.xml.ws.handler.LogicalHandler;

import org.omg.PortableServer.POA;

import com.jfoenix.controls.JFXDialog;
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
import parser.ParserInterface;
import view.TaskListCell;
import logic.*;
import parser.UserInputParser;
import parser.Commands;

/*
 * Author: A0133333U
 */

public class MainWindowController implements Initializable {

    private Main main;
    private UserInputParser parser;



    /* Views */
    @FXML
    Label label;
    @FXML
    JFXTextField commandBox;
    @FXML
    JFXListView<Task> taskListView;
    ObservableList<Task> list = FXCollections.observableArrayList();

    public void setMain(Main main) {
        this.main = main;
    }


    @FXML
    private void handleEnterKeyPressed(KeyEvent event) throws Exception {
        if (event.getCode().equals(KeyCode.ENTER)) {
            LocalDate startDate = LocalDate.now();
            LocalDate deadLine = LocalDate.now();
            String userInput = commandBox.getText();
           /* UserInputParser parser = new UserInputParser();
            Commands command = parser.getCommand(userInput);
            if (command.equals("add")) {
                list.add(new Task(parser.getTaskName(userInput), startDate, deadLine));
            } else if (command.equals("delete")) {
                // list remove
            } else if (command.equals("view")) {
                // todo: implement view by index
            } else if (command.equals("edit")){
                
            } else {
               JFXDialog dialog = new JFXDialog();
               dialog.show();
            }
        */
            String commandString = commandBox.getText();
            list.add(new Task(commandString, startDate, deadLine));
            taskListView.setItems(list);
            commandBox.clear();
            
        }
}
  
    @FXML
    private void handleDeleteTask(KeyEvent event) {
        // if (event.get)
    }

    private boolean isEmptyInput(String input) {
        return input == null || input.isEmpty() || "".equals(input.trim());
    }

    private void setCellFactory() {
        taskListView.setCellFactory(param -> new TaskListCell());
    }

}
