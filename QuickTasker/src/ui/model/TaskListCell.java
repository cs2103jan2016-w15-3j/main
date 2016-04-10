package ui.model;

//@@author A0126077E

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXRippler;
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
    private JFXRippler rippler = new JFXRippler();
    private Task myTask;

    public TaskListCell() {
        configureGrid();
        configureTaskName();
        configureDate();
        configureTime();
        configureIcon();
        configureCheckBox();
        addControlsToGrid();
        addGridToRippler();
    }

    private void addGridToRippler() {
        rippler.setControl(grid);
    }

    public JFXRippler getRippler() {
        return this.rippler;
    }

    private void configureGrid() {
        grid.setHgap(10); // horizontal gap between grids
        grid.setVgap(5); // vertical gap between grids
        grid.setPadding(new Insets(0, 10, 0, 10));// set custom columns
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setMinWidth(20);
        column1.setMaxWidth(20);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setMaxWidth(20);
        ColumnConstraints column3 = new ColumnConstraints();
        column3.setHgrow(Priority.ALWAYS);
        ColumnConstraints column4 = new ColumnConstraints();
        column4.setMinWidth(80);
        ColumnConstraints column5 = new ColumnConstraints();
        column5.setMinWidth(80);
        grid.getColumnConstraints().addAll(column1, column2, column3, column4, column5);
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
        taskEndTime.getStyleClass().add("task-time");
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
    public void updateItem(Task t, boolean empty) {
        super.updateItem(t, empty);
        if (empty) clearContent();
        else {

            this.myTask = t;
            addContent(t);
            rippler = new JFXRippler(grid);
            setGraphic(rippler);
        }
    }

    protected void addContent(Task t) {
        setTaskName(t);
        setTaskId(t);

        setTaskStartDate(t);
        setTaskDueDate(t);

        setTaskStartTime(t);
        setTaskEndTime(t);

        setGraphic(grid);
    }

    protected void setTaskId(Task t) {
        taskId.setText(String.valueOf(getIndex() + 1));
    }

    protected void setTaskName(Task t) {
        taskName.setText(t.getName());
    }

    protected void setTaskStartDate(Task t) {
        if (t != null && t.getStartDate() != null && !t.getStartDate().equals(LocalDate.MIN)) setStartDateInText(t);
        else taskStartDate.setText("");
    }

    private void setStartDateInText(Task t) {
        taskStartDate.setText(DateTimeFormatter.ofPattern("dd MMM yyyy").format(t.getStartDate()));
    }

    private boolean isNotFloatingTask(Task t) {
        return !"floating".equals(t.getTaskType());
    }

    protected void setTaskDueDate(Task t) {

        taskDueDate.setText(t == null || t.getDueDate() == null || t.getDueDate().equals(LocalDate.MAX) ? "-"
                : DateTimeFormatter.ofPattern("dd MMM yyyy").format(t.getDueDate()));
    }

    protected void setTaskStartTime(Task t) {
        taskStartTime.setText(t == null || !isNotFloatingTask(t) || !isNotEvent(t) || !timeCheck(t) ? ""
                : DateTimeFormatter.ofPattern("HH:mm").format(t.getStartTime()));
    }

    protected void setTaskEndTime(Task t) {

        if (t == null || !isNotFloatingTask(t) || !isNotEvent(t) || !timeCheck(t)) taskEndTime.setText("-");
        else {
            LocalTime endTime = t.getEndTime();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            String timeString = formatter.format(endTime);
            System.out.println(timeString);
            taskEndTime.setText(timeString);
        }
    }

    private boolean timeCheck(Task t) {
        return t.getStartTime() != null || t.getEndTime() != null;
    }

    private boolean isNotEvent(Task t) {
        return !"wholeDayEvent".equals(t.getTaskType());
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
