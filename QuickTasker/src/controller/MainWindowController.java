package controller;

import java.awt.color.ICC_ColorSpace;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.PrimitiveIterator.OfDouble;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import jdk.internal.org.objectweb.asm.commons.StaticInitMerger;
import model.Task;
import view.TaskListCell;

public class MainWindowController implements Initializable {
    private Main main;
    private ParserInterface parser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCellFactory();
        setMain(main);
    }

    /** Views. */
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
            list.add(new Task(commandBox.getText(), startDate, deadLine));
            taskListView.setItems(list);
            commandBox.clear();
            String commandStrin = commandBox.getText();
            }
        }
        
    }
    
    /*
     *Author:  A0133333U
     * When input is empty and enter key is hit, no empty cell is created
     */
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
