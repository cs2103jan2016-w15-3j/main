package ui.model;

/*
 * Author: Xin Kenan, Lee Jia Lin
 */

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXListCell;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import model.Task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static ui.controller.TaskDoneEvent.TASK_COMPLETE;

public class TaskListCell extends JFXListCell<Task> {
    private final Label taskName = new Label();
    private final Label taskIndex = new Label();
    private final Label taskId = new Label();
    private final Label taskStartDate = new Label();
    private final Label taskDeadLine = new Label();
    private final Label taskStartTime = new Label();
    private final Label taskEndTime = new Label();
    private final JFXCheckBox checkBox = new JFXCheckBox();
    private final GridPane grid = new GridPane();
    private ObservableList<Task> tasks;
    private boolean handlerAdded = false;

    public TaskListCell(ObservableList<Task> list) {
        configureGrid();
        configureTaskName();
        configureDate();
        configureIcon();
        configureCheckBox();
        addControlsToGrid();
        tasks = list;
    }

    @Override
    public void updateItem(Task task, boolean empty) {
        super.updateItem(task, empty);
        if (empty) {
            clearContent();
        } else {
            if (!handlerAdded) {
                getListView().addEventFilter(TASK_COMPLETE, event -> {
                    event.consume();
                    handlerAdded = true;

                    checkBox.setAllowIndeterminate(false);
                    new Thread(() -> {
                        Thread.currentThread().setUncaughtExceptionHandler(
                                (t, e) -> Platform.runLater(System.out::println));
                        checkBox.fire();

                    }).start();
                        }

                );
            }
            addContent(task);
            setGraphic(grid);
        }

    }

    protected void addContent(Task task) {
        setTaskName(task);
        setTaskId(task);
        setTaskStartDate(task);
        setTaskDueDate(task);
        setTaskStartTime(task);
        setTaskEndTime(task);
        setGraphic(grid);
    }

    protected void setTaskId(Task task) {
        final int offset = 1;
        taskId.setText(String.valueOf(tasks.indexOf(task) + offset));
    }

    protected void setTaskName(Task task) {
        taskName.setText(task.getName());
    }

    protected void setTaskStartDate(Task task) {
        if (task.getStartDate() != null) {
            LocalDate startDate = task.getStartDate();
            // from here
            if (startDate == LocalDate.MIN || startDate == LocalDate.MAX) {
                taskStartDate.setText("Not Specified");
            } else {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                String dateString = formatter.format(startDate);
                taskStartDate.setText(dateString);
            }
            // stop here
        } else
            taskStartDate.setText("");
    }

    protected void setTaskDueDate(Task task) {
        if (task.getStartDate() != null) {
            LocalDate dueDate = task.getDueDate();
            // from here
            if (dueDate == LocalDate.MIN || dueDate == LocalDate.MAX) {
                taskDeadLine.setText("Not Specified");
            } else {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                String dateString = formatter.format(dueDate);
                taskDeadLine.setText(dateString);
            }
        } else
            taskDeadLine.setText("");
    }

    protected void setTaskStartTime(Task task) {
        /* if (task.getTime() != null) */
        final int offset = 1;
        taskStartTime.setText("start time here");
    }

    protected void setTaskEndTime(Task task) {
        final int offset = 1;
        taskEndTime.setText("end time here");
    }

    /**
     * Http://docs.oracle.com/javafx/2/layout/builtin_layouts.htm
     * https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/
     * GridPane.html
     */
    private void configureGrid() {
        grid.setHgap(10); // horizontal gap between grids
        grid.setVgap(5); // vertical gap between grids
        grid.setPadding(new Insets(0, 10, 0, 10));// set custom columns
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setMaxWidth(5);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setMaxWidth(20);
        ColumnConstraints column3 = new ColumnConstraints();
        column3.setHgrow(Priority.ALWAYS);
        grid.getColumnConstraints().addAll(column1, column2, column3);

    }

    private void configureTaskName() {
        taskName.setPrefWidth(230);
        taskName.setWrapText(true);
        taskName.setStyle("-fx-font-weight:bold; -fx-font-family: sans-serif; -fx-padding:10px");
    }

    private void configureDate() {
        taskStartDate.setStyle("-fx-font-weight:bold;-fx-padding:10px");
        taskDeadLine.setStyle("-fx-font-weight:bold;-fx-padding:10px");
    }

    private void configureCheckBox() {
        checkBox.getStyleClass().add("task-check-box");
    }

    private void clearContent() {
        setText(null);
        setGraphic(null);
    }

    private void configureIcon() {
        // todo : implement awesome font icons and custom css
        // if (taskDeadLine.getText() < LocalDate.now().) {

    }

    private void setOverdue() {

    }

    private void addControlsToGrid() {
        grid.add(taskId, 0, 0);
        grid.add(checkBox, 1, 0);
        grid.add(new HBox(taskName), 2, 0); // add(Node child, int columnIndex,
        // int
        grid.add(taskStartDate, 3, 0); // rowIndex, int colspan, int
        grid.add(taskStartTime, 3, 1);
        grid.add(taskDeadLine, 4, 0);
        grid.add(taskEndTime, 4, 1);

    }
}