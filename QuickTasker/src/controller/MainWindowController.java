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
    ObservableList<Task> list = FXCollections.observableArrayList();

    public void setMain(Main main) {
        this.main = main;
    }

    @FXML
    
    private void handleEnterKeyPressed(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            LocalDate startDate = LocalDate.now();
            LocalDate deadLine = LocalDate.now();
            list.add(new Task("RandomTask", startDate, deadLine));
            taskListView.setItems(list);
            commandBox.clear();
        }
    }

    private void setCellFactory() {
        taskListView.setCellFactory(param -> new TaskListCell());
    }

}
