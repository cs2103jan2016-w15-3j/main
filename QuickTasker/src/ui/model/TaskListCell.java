package ui.model;

//@@author A0126077E

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXRippler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    // @@author A0133333U
    private final Label taskStartTime = new Label();
    private final Label taskEndTime = new Label();
    private final Label taskId = new Label();
    private final Label lateIcon = new Label();
    private final JFXCheckBox checkBox = new JFXCheckBox();
  //@@author A0126077E
    private final JFXPopup searchBox = new JFXPopup();
    private final GridPane grid = new GridPane();
    private JFXRippler rippler = new JFXRippler();
    private Task myTask;
    private final int offset = 1;

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
        column3.setMinWidth(80);
        ColumnConstraints column4 = new ColumnConstraints();
        column3.setHgrow(Priority.ALWAYS);
        ColumnConstraints column5 = new ColumnConstraints();
        column5.setMinWidth(80);
        ColumnConstraints column6 = new ColumnConstraints();
        column6.setMinWidth(80);
        grid.getColumnConstraints().addAll(column1, column2, column3, column4, column5, column6);
    }

    private void configureTaskName() {
        setWrapIfTaskNameLong();
        taskName.getStyleClass().add("task-name");
        GridPane.setHalignment(taskName, HPos.LEFT);
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

    // @@author A0133333U
    private void configureTime() {
        taskStartDate.getStyleClass().add("task-time");
        taskEndTime.getStyleClass().add("task-time");
        GridPane.setHalignment(taskStartTime, HPos.RIGHT);
        GridPane.setHalignment(taskEndTime, HPos.RIGHT);
    }

    // @@author A0133333U
    private void configureIcon() {
        lateIcon.getStyleClass().add("late-icon");
    }

    private void configureCheckBox() {
        checkBox.getStyleClass().add("task-check-box");
    }

    private void clearContent() {
        setText(null);
        setGraphic(null);
    }


    @Override
    public void updateItem(Task task, boolean empty) {
        super.updateItem(task, empty);
        if (empty) clearContent();
        else {

            this.myTask = task;
            addContent(task);
            rippler = new JFXRippler(grid);
            setGraphic(rippler);
        }
    }

    protected void addContent(Task task) {
        setTaskName(task);
        setIcon(task);
        setTaskId(task);

        setTaskStartDate(task);
        setTaskDueDate(task);

        //setTaskStartTime(task);
        setTaskEndTime(task);

        setGraphic(grid);
    }
    
    //@@author A0133333U
    protected void setIcon(Task task) {
        Image image = new Image(getClass().getResourceAsStream("/img/late-icon.png"));
    	ImageView imageView = new ImageView(image);
    	imageView.setFitHeight(25);
    	imageView.setFitWidth(25);
    	imageView.setPreserveRatio(true);
        lateIcon.setGraphic(imageView);
    }

    // @@author A0133333U
    protected void setTaskId(Task task) {
        taskId.setText(String.valueOf(getIndex() + offset));    
    }

    protected void setTaskName(Task task) {
        taskName.setText(task.getName());
    }

    protected void setTaskStartDate(Task task) {
        if (task != null && task.getStartDate() != null && !task.getStartDate().equals(LocalDate.MIN))
            setStartDateInText(task);
        else taskStartDate.setText("");
    }

    private void setStartDateInText(Task task) {
        taskStartDate.setText(DateTimeFormatter.ofPattern("dd MMM yyyy").format(task.getStartDate()));
    }

    private boolean isNotFloatingTask(Task task) {
        return !"floating".equals(task.getTaskType());
    }

    protected void setTaskDueDate(Task task) {

        taskDueDate.setText(task == null || task.getDueDate() == null || task.getDueDate().equals(LocalDate.MAX) ?
                "-" :
                DateTimeFormatter.ofPattern("dd MMM yyyy").format(task.getDueDate()));
    }

    // @@author A0133333U
    protected void setTaskStartTime(Task task) {
        taskStartTime.setText(task == null || !isNotFloatingTask(task) || !isNotEvent(task) || !timeCheck(task) ?
                "" :
                DateTimeFormatter.ofPattern("HH:mm").format(task.getStartTime()));
    }

    // @@author A0133333U
    protected void setTaskEndTime(Task task) {

        if (task == null || !isNotFloatingTask(task) || !isNotEvent(task) || !timeCheck(task)) taskEndTime.setText("-");
        else {
            LocalTime endTime = task.getEndTime();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            String timeString = formatter.format(endTime);
            System.out.println(timeString);
            taskEndTime.setText(timeString);
        }
    }

    private boolean timeCheck(Task task) {
        return task.getStartTime() != null || task.getEndTime() != null;
    }

    private boolean isNotEvent(Task task) {
        return !"wholeDayEvent".equals(task.getTaskType());
    }

    public JFXCheckBox getCheckBox() {
        return checkBox;
    }

    public Task getTask() {
        return myTask;
    }

    private void addControlsToGrid() {
        grid.add(taskId, 0, 0, 1, 2);
        grid.add(lateIcon, 1, 0, 1, 2);
        grid.add(checkBox, 2, 0, 1, 2);
        grid.add(new HBox(taskName), 3, 0, 1, 2);
        grid.add(taskStartDate, 4, 0);
        grid.add(taskStartTime, 4, 1);
        grid.add(taskDueDate, 5, 0);
        grid.add(taskEndTime, 5, 1);
    }
}
