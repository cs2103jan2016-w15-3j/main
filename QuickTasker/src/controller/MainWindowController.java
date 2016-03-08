package controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.PrimitiveIterator.OfDouble;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.sun.jmx.snmp.SnmpUnknownSubSystemException;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import com.sun.org.apache.xalan.internal.xsltc.dom.UnionIterator;

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

public class MainWindowController implements Initializable {
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
    ObservableList<Task> list = FXCollections.observableArrayList();

    public void setMain(Main main) {
        this.main = main;
    }

    @FXML
    private void handleEnterKeyPressed(KeyEvent event) throws Exception {
        if (event.getCode().equals(KeyCode.ENTER)) {
            LocalDate startDate = /*
                                   * parser.getStartDate(commandBox.getText())
                                   */LocalDate.now();
            LocalDate deadLine = LocalDate.now();
            parser = new UserInputParser();
            Logic logic = new Logic();
            logic.addTask(new Task());
            String input = parser.getTaskName(commandBox.getText());
            list.add(new Task(input/* commandBox.getText() */, parser.getStartDate(commandBox.getText())/* startDate */,
                    parser.getEndDate(commandBox.getText())/* deadLine) */));
            taskListView.setItems(list);
            commandBox.clear();
        }
    }

    private boolean isEmptyInput(String input) {
        return input == null || input.isEmpty() || "".equals(input.trim());
    }

    private void setCellFactory() {
        taskListView.setCellFactory(param -> new TaskListCell());
    }

}
