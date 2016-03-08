package controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXDialog;
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
import model.Task;
import parser.ParserInterface;
import parser.UserInputParser;
import view.TaskListCell;
import logic.*;
import parser.UserInputParser;
import parser.Commands;

/*
 * Author: A0133333U
 */

public class MainWindowController implements Initializable {

    private static final String String = null;
    private Main main;
    private UserInputParser parser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCellFactory();
        setMain(main);
    }

    /** Views. */

    @FXML
    Label label;
    @FXML
    JFXTextField commandBox;
    @FXML
    JFXListView<Task> taskListView;
    ObservableList<Task> guiList = FXCollections.observableArrayList();

    public void setMain(Main main) {
        this.main = main;
    }

    private boolean isEmptyInput(String input) {
        return input == null || input.isEmpty() || "".equals(input.trim());
    }

    @FXML
    private void handleEnterKeyPressed(KeyEvent event) throws Exception {
        if (!isEmptyInput(commandBox.getText()) && event.getCode().equals(KeyCode.ENTER)) {
            LocalDate startDate = LocalDate.now();
            LocalDate deadLine = LocalDate.now();
            UserInputParser parser = new UserInputParser();
            Logic logic = new Logic();
            parser.getCommand(commandBox.getText());
            logic.addTask(new Task());
            String input = parser.getTaskName(commandBox.getText());
            guiList.add(new Task(input, parser.getStartDate(commandBox.getText()),
                    parser.getEndDate(commandBox.getText())));
            //guiList.remove(parser.deleteIndexNumber(input + 1));

            taskListView.setItems(guiList);
            commandBox.clear();
        }
        /*
         * if delete logic.deleteTask(index of task) guiList.remove(task's
         * index) taskListView.setItems(guiList)
         * 
         */
    }

    private void setCellFactory() {
        taskListView.setCellFactory(param -> new TaskListCell());
    }

}
