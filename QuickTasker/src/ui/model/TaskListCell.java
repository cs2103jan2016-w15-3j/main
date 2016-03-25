package ui.model;

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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import model.Task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TaskListCell extends JFXListCell<Task> {
    private final Label taskStartDate = new Label();
    private final Label taskEndDate = new Label();
    private final Label taskName = new Label();
    private final Label taskIndex = new Label();
    private final Label taskId = new Label();
    private final JFXCheckBox checkBox = new JFXCheckBox();
    private final GridPane grid = new GridPane();
    private final ObservableList<Task> tasks;
    private Task myTask;

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
            this.myTask = task;
            addContent(task);
            setGraphic(grid);
        }
    }

    protected void addContent(Task task) {
        setTaskName(task);
        setTaskId(task);
        setTaskStartDate(task);
        setTaskDueDate(task);
        setGraphic(grid);
    }

    /*public void updateIndex(ObservableList<Task> tasks){
        setTaskId(myTask);
        System.out.println(this.indexProperty());
    }*/

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
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
            String dateString = formatter.format(startDate);
            taskStartDate.setText(dateString);
        } else taskStartDate.setText("");
    }

    protected void setTaskDueDate(Task task) {
        if (task.getStartDate() != null) {
            LocalDate dueDate = task.getDueDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
            String dateString = formatter.format(dueDate);
            taskEndDate.setText(dateString);
        } else taskEndDate.setText("");
    }

    private void configureGrid() {
        grid.setHgap(10); // horizontal gap between grids
        grid.setVgap(5); // vertical gap between grids
        grid.setPadding(new Insets(0, 5, 0, 5));// set custom columns
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setMaxWidth(5);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setMaxWidth(20);
        ColumnConstraints column3 = new ColumnConstraints();
        column3.setHgrow(Priority.ALWAYS);
        grid.getColumnConstraints().addAll(column1, column2, column3);

    }

    private void configureTaskName() {

        taskName.setWrapText(true);
        taskName.setStyle("-fx-font-weight:bold; -fx-font-family: sans-serif; -fx-padding:10px");
    }

    private void configureDate() {
        taskStartDate.getStyleClass().add("start-date");
        taskEndDate.getStyleClass().add("end-date");
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
        // icons to be applied to relevant tasks
    }

    public JFXCheckBox getCheckBox() {
        return checkBox;
    }

    public Task getTask() {
        return myTask;
    }

    private void addControlsToGrid() {
        grid.add(taskId, 0, 0);
        grid.add(checkBox, 1, 0);
        grid.add(new HBox(taskName), 2, 0);
        grid.add(taskStartDate, 3, 0);
        grid.add(taskEndDate, 4, 0);
    }
}
