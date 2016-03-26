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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public class TaskListCell extends JFXListCell<Task> {
    private final Label taskName = new Label();
    private final Label taskId = new Label();
    private final Label taskStartDate = new Label();
    private final Label taskDeadLine = new Label();
    private final Label taskStartTime = new Label();
    private final Label taskEndTime = new Label();
    private final JFXCheckBox checkBox = new JFXCheckBox();
    private final GridPane grid = new GridPane();
    private ObservableList<Task> tasks;
    private Task task;

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
            //if (task.isDone()) checkBox.fire();
/*            if (!checkBox.isSelected()) {
                getListView().addEventHandler(TASK_COMPLETE, event -> {
                    int selected = getListView().getSelectionModel().getSelectedIndex();
                    System.out.println("The task name is :" + task.getName());
                    System.out.println("Event task name is :" + event.getTask().getName());
                    System.out.println("===================");
                    //checkBox.setAllowIndeterminate(false);
                    new Thread(() -> {
                        Thread.currentThread().setUncaughtExceptionHandler(
                                (t, e) -> Platform.runLater(System.out::println));
                        checkBox.fire();
                        // event.getTask should be right
                        // however the problem is checkbox, checkbox moves around different cells.
                        // bind checkout to task ?
                    }).start();
                });
            }*/
            addContent(task);
            setGraphic(grid);
        }

    }

    protected void addContent(Task task) {
        setCheckBox(task);
        //setTaskName(task);
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

    public JFXCheckBox getCheckbox() {
        return checkBox;
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
    	if (task.getStartTime() != null) {
            String startTime = task.getStartTime().toString();
            System.out.println("start time is " +startTime);
            // from here
/*            if (startTime == LocalTime.MIN || startTime == LocalTime.MAX) {
                taskStartTime.setText("Not specified here");
            } else {*/
               // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
              //  String timeString = formatter.toFormat(startTime);
            String timeString = startTime;
                taskStartTime.setText(timeString);
         //} 
            // stop here
        } else
        	
            taskStartTime.setText("Not specified");
    
}
    
    

    protected void setTaskEndTime(Task task) {
    	if (task.getEndTime() != null) {
           String endTime = task.getEndTime().toString();
            System.out.println("end time is " + endTime);
            // from here
            /*if (endTime == LocalTime.MIN || endTime == LocalTime.MAX) {
                taskEndTime.setText("Not Specified here");
            } else {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                */
                String timeString = endTime;
             taskEndTime.setText(timeString);
           // }
        } else
            taskEndTime.setText("Not specified");
    }
    
    public void fireCheckBox() {
        checkBox.fire();
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
        grid.getColumnConstraints().addAll(column1, column3);

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

    public void setCheckBox(Task task) {
        checkBox.setText(task.getName());
        if (task.isDone()) checkBox.fire();
    }
}