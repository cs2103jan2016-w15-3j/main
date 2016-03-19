package view;

/*
 * Author: Xin Kenan, Lee Jia Lin
 */

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXListCell;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import model.Task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TaskListCell extends JFXListCell<Task> {
    private final Label taskStartDate = new Label();
    private final Label taskDeadLine = new Label();
    private final Label taskName = new Label();
    private final Label taskIndex = new Label();
    private final Label taskId = new Label();
    private final JFXCheckBox checkBox = new JFXCheckBox();
    private final GridPane grid = new GridPane();
    private ObservableList<Task> tasks;

    public TaskListCell(ObservableList<Task> list) {
        configureGrid();
        configureTaskName();
        configureDate();
        configureIcon();
        configureCheckBox();
        addControlsToGrid();
        tasks = list;
    }

    @Override public void updateItem(Task task, boolean empty) {
        super.updateItem(task, empty);
        if (empty) {
            clearContent();
        } else {
            addContent(task);
            setGraphic(grid);
        }
    }

    private void addContent(Task task) {
        setTaskName(task);
        setTaskId(task);
        setTaskStartDate(task);
        setTaskDueDate(task);
        setGraphic(grid);
    }

    private void setTaskId(Task task) {
        taskId.setText(String.valueOf(task.getId()));
    }

    private void setTaskName(Task task) {
        taskName.setText(task.getName());
    }

    private void setTaskStartDate(Task task) {
        LocalDate startDate = task.getStartDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String dateString = formatter.format(startDate);
        taskStartDate.setText(dateString);
    }

    private void setTaskDueDate(Task task) {
        LocalDate dueDate = task.getDueDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String dateString = formatter.format(dueDate);
        taskDeadLine.setText(dateString);
    }

    /**
     * Http://docs.oracle.com/javafx/2/layout/builtin_layouts.htm
     * https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/ GridPane.html
     */
    private void configureGrid() {
        grid.setHgap(10); // horizontal gap between grids
        grid.setVgap(5); // vertical gap between grids
        grid.setPadding(new Insets(0, 10, 0, 10));
        // set custom columns

        ColumnConstraints taskIdColumn = new ColumnConstraints();
        taskIdColumn.setMaxWidth(50);
        ColumnConstraints checkBoxColumn = new ColumnConstraints();
        checkBoxColumn.setPercentWidth(3);
        ColumnConstraints taskDetailColumn = new ColumnConstraints();
        taskDetailColumn.setPercentWidth(58);
        ColumnConstraints startDateColumn = new ColumnConstraints();
        startDateColumn.setPercentWidth(20);
        ColumnConstraints dueDateColumn = new ColumnConstraints();
        startDateColumn.setPercentWidth(20);
        grid.getColumnConstraints()
                .addAll(taskIdColumn, taskDetailColumn, startDateColumn, dueDateColumn);

    }

    private void configureTaskName() {
        taskName.setStyle("-fx-font-weight:bold; -fx-font-family: sans-serif; -fx-padding:10px");
    }

    private void configureDate() {
        taskStartDate.setStyle("-fx-font-weightt:bold;-fx-padding:10px");
        taskDeadLine.setStyle("-fx-font-weightt:bold;-fx-padding:10px");
    }

    private void configureCheckBox() {
        checkBox.setMaxWidth(10);
    }

    private void clearContent() {
        setText(null);
        setGraphic(null);
    }

    private void configureIcon() {
        // todo : implement awesome font icons and custom css
        // icons to be applied to relevant tasks

    }

    private void addControlsToGrid() {
        grid.add(taskId, 0, 0);
        grid.add(checkBox, 1, 0);
        grid.add(taskName, 2, 0); // add(Node child, int columnIndex, int
        grid.add(taskStartDate, 3, 0, 1, 1); // rowIndex, int colspan, int
        grid.add(taskDeadLine, 4, 0, 1, 1);
    }
}
