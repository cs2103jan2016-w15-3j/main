package ui.model;

/*
 * Author: Xin Kenan, A0133333U
 */

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXPopup;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import model.Task;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TaskListCell extends JFXListCell<Task> {

    private final Label taskStartDate = new Label();
    private final Label taskDueDate = new Label();
    private final Label taskName = new Label();
    private final Label taskStartTime = new Label();
    private final Label taskEndTime = new Label();
    private final Label taskId = new Label();
    private final JFXCheckBox checkBox = new JFXCheckBox();
    private final JFXPopup searchBox = new JFXPopup();
    private final GridPane grid = new GridPane();
    private final ObservableList<Task> tasks;
    private Task myTask;

    public TaskListCell(ObservableList<Task> list) {
        configureGrid();
        configureTaskName();
        configureDate();
        configureTime();
        configureIcon();
        configureCheckBox();
        addControlsToGrid();
        tasks = list;
    }

    private void configureGrid() {
        grid.setHgap(10); // horizontal gap between grids
        grid.setVgap(5); // vertical gap between grids
        grid.setPadding(new Insets(0, 10, 0, 10));// set custom columns
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setMinWidth(25);
        column1.setMaxWidth(25);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setMaxWidth(20);
        ColumnConstraints column3 = new ColumnConstraints();
        column3.setHgrow(Priority.ALWAYS);
        ColumnConstraints column4 = new ColumnConstraints();
        column4.setMinWidth(80);
        ColumnConstraints column5 = new ColumnConstraints();
        column5.setMinWidth(80);
        grid.getColumnConstraints().addAll(column1, column2, column3,column4,column5);
    }

    private void configureTaskName() {
        setWrapIfTaskNameLong();
        taskName.getStyleClass().add("task-name");
    }

    private void setWrapIfTaskNameLong() {
        taskName.setWrapText(true);
        setPrefWidth(0);
    }

    private void configureDate() {
        taskStartDate.getStyleClass().add("task-date");
        taskDueDate.getStyleClass().add("task-date");
        GridPane.setHalignment(taskStartDate, HPos.RIGHT);
        GridPane.setHalignment(taskDueDate, HPos.RIGHT);
    }

    private void configureTime() {
        taskStartDate.getStyleClass().add("task-time");
        taskEndTime.getStyleClass().add(".task-time");
        GridPane.setHalignment(taskStartTime, HPos.RIGHT);
        GridPane.setHalignment(taskEndTime, HPos.RIGHT);
    }

    private void configureIcon() {
        // lateIcon.getStyleClass().add("late-icon");

    }

    private void configureCheckBox() {
        checkBox.getStyleClass().add("task-check-box");
    }

    private void clearContent() {
        setText(null);
        setGraphic(null);
    }

    private void configureSearchBox() {
        // searchBox.getStyleClass.().add("search-box");

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
    // From Kenan banana:
    // right now, we use "-" rather than "not specified" because it helps covers up the problem that
    // when task name is too long, scroll bar appear, which doesnt look good
    // It will be shortly replaced with icons in the next iterat

    protected void setTaskStartDate(Task task) {
        if (!task.getTaskType().equals("floating")) {

            LocalDate startDate = task.getStartDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
            String dateString = formatter.format(startDate);
            taskStartDate.setText(dateString);
        } else taskStartDate.setText("");
    }

    protected void setTaskDueDate(Task task) {

        if (!task.getTaskType().equals("floating")) {

            LocalDate dueDate = task.getDueDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
            String dateString = formatter.format(dueDate);
            taskDueDate.setText(dateString);
        } else taskDueDate.setText("-");
    }

    protected void setTaskStartTime(Task task) {
        if (!task.getTaskType().equals("floating") && !task.getTaskType().equals("wholeDayEvent")) {
            LocalTime startTime = task.getStartTime();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            String timeString = formatter.format(startTime);
            taskStartTime.setText(timeString);
        } else {
            taskStartTime.setText("");
        }
    }

    protected void setTaskEndTime(Task task) {

        if (!task.getTaskType().equals("floating") && !task.getTaskType().equals("wholeDayEvent")) {
            LocalTime endTime = task.getEndTime();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            String timeString = formatter.format(endTime);
            System.out.println(timeString);
            taskEndTime.setText(timeString);
        } else {
            taskEndTime.setText("-");
        }
    }

    public JFXPopup getSearchBox() {
        return searchBox;
    }

    public JFXCheckBox getCheckBox() {
        return checkBox;
    }

    public Task getTask() {
        return myTask;
    }

    private void addControlsToGrid() {
        grid.add(taskId, 0, 0, 1, 2);
        grid.add(checkBox, 1, 0, 1, 2);
        grid.add(new HBox(taskName), 2, 0, 1, 2);
        grid.add(taskStartDate, 3, 0);
        grid.add(taskStartTime, 3, 1);
        grid.add(taskDueDate, 4, 0);
        grid.add(taskEndTime, 4, 1);

    }
}
