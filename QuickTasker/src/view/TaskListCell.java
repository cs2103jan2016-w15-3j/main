package view;

import com.jfoenix.controls.JFXListCell;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import model.Task;

public class TaskListCell extends JFXListCell<Task> {
    private final Label taskDate = new Label();
    private final Label taskName = new Label();
    private final GridPane grid = new GridPane();

    @Override
    public void updateItem(Task task, boolean empty) {
        super.updateItem(task, empty);
        if (empty) {
            clearContent();
        } else {
            clearContent();
            setTaskName(task);
            setText(task.getName());
        }
    }

    private void configureGrid() {
        // todo set proper margin and padding
        // set proper hGrow
        /* grid.setHgap(10);
        grid.setVgap(4);
        grid.setPadding(new Insets(0, 10, 0, 10));
        
        ColumnConstraints column1 = new ColumnConstraints(32);
        ColumnConstraints column2 = new ColumnConstraints(USE_COMPUTED_SIZE , USE_COMPUTED_SIZE, Double.MAX_VALUE);
        column2.setHgrow(Priority.NEVER);
        ColumnConstraints column3 = new ColumnConstraints(30 , 50 , Double.MAX_VALUE);
        column3.setHgrow(Priority.ALWAYS);
        column3.setFillWidth(true);
        ColumnConstraints column4 = new ColumnConstraints(USE_COMPUTED_SIZE , USE_COMPUTED_SIZE , Double.MAX_VALUE);
        column4.setHgrow(Priority.NEVER);
        ColumnConstraints column5 = new ColumnConstraints(30 , 50 , Double.MAX_VALUE);
        column5.setHgrow(Priority.ALWAYS);
        column5.setFillWidth(true);
        ColumnConstraints column6 = new ColumnConstraints(10, 12, 16);
        column6.setHgrow(Priority.NEVER);
        column6.setFillWidth(false);
        grid.getColumnConstraints().addAll(column1, column2, column3, column4, column5, column6);*/
    }

    private void configureTaskName() {
        taskName.setStyle("-fx-font-weight:bold;");
    }

    private void configureDate() {
        taskDate.setStyle("-fx-font-weightt:bold");
    }

    private void clearContent() {
        setText(null);
        setGraphic(null);
    }

    private void configureIcon() {
        // todo : implement awesome font icons and custom css
    }

    private void setTaskName(Task task) {
        taskName.setText(task.getName());
    }

    private void setTaskDate(Task task) {

    }

    private void addControlsToGrid() {
        grid.add(taskName, 0, 0, 1, 2);
    }

    @Override
    public String toString() {
        return taskName.toString();
    }
}
