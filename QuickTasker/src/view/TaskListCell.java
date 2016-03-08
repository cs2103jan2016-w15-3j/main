package view;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import com.jfoenix.controls.JFXListCell;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import model.Task;
import logic.Logic;

public class TaskListCell extends JFXListCell<Task> {
    private final Label taskStartDate = new Label();
    private final Label taskDeadLine = new Label();
    private final Label taskName = new Label();
    private final GridPane grid = new GridPane();

    public TaskListCell() {
        configureGrid();
        configureTaskName();
        configureDate();
        addControlsToGrid();
    }

    @Override
    public void updateItem(Task task, boolean empty) {
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
        setTaskStartDate(task);
        setTaskDueDate(task);
        setGraphic(grid);
    }

    /**
     * http://docs.oracle.com/javafx/2/layout/builtin_layouts.htm
     * https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/GridPane.html
     */
    private void configureGrid() {
        grid.setHgap(10); // horizontal gap between grids
        grid.setVgap(5); // vertical gap between grids
        grid.setPadding(new Insets(0, 10, 0, 10));
        // grid.setGridLinesVisible(true); // debugging
        // set custom columns
        ColumnConstraints taskDetailColumn = new ColumnConstraints();
        taskDetailColumn.setPercentWidth(60);
        ColumnConstraints startDateColumn = new ColumnConstraints();
        startDateColumn.setPercentWidth(20);
        ColumnConstraints dueDateColumn = new ColumnConstraints();
        startDateColumn.setPercentWidth(20);

        grid.getColumnConstraints().addAll(taskDetailColumn, startDateColumn, dueDateColumn);

        // todo set proper margin and padding
        // set proper hGrow
        /*
         * grid.setHgap(10); grid.setVgap(4); grid.setPadding(new Insets(0, 10,
         * 0, 10));
         * 
         * ColumnConstraints column1 = new ColumnConstraints(32);
         * ColumnConstraints column2 = new ColumnConstraints(USE_COMPUTED_SIZE ,
         * USE_COMPUTED_SIZE, Double.MAX_VALUE);
         * column2.setHgrow(Priority.NEVER); ColumnConstraints column3 = new
         * ColumnConstraints(30 , 50 , Double.MAX_VALUE);
         * column3.setHgrow(Priority.ALWAYS); column3.setFillWidth(true);
         * ColumnConstraints column4 = new ColumnConstraints(USE_COMPUTED_SIZE ,
         * USE_COMPUTED_SIZE , Double.MAX_VALUE);
         * column4.setHgrow(Priority.NEVER); ColumnConstraints column5 = new
         * ColumnConstraints(30 , 50 , Double.MAX_VALUE);
         * column5.setHgrow(Priority.ALWAYS); column5.setFillWidth(true);
         * ColumnConstraints column6 = new ColumnConstraints(10, 12, 16);
         * column6.setHgrow(Priority.NEVER); column6.setFillWidth(false);
         * grid.getColumnConstraints().addAll(column1, column2, column3,
         * column4, column5, column6);
         */
    }

    private void configureTaskName() {
        taskName.setStyle("-fx-font-weight:bold; -fx-padding:10px");
    }

    private void configureDate() {
        taskStartDate.setStyle("-fx-font-weightt:bold;-fx-padding:10px");
        taskDeadLine.setStyle("-fx-font-weightt:bold;-fx-padding:10px");
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

    private void setTaskStartDate(Task task) {
        LocalDate startDate = task.getStartDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String dateString = formatter.format(startDate);
        taskStartDate.setText(dateString);
    }

    private void setTaskDueDate(Task task) {
        LocalDate dueDate = task.getDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String dateString = formatter.format(dueDate);
        taskDeadLine.setText(dateString);
    }

    private void addControlsToGrid() {
        grid.add(taskName, 0, 0); // add(Node child, int columnIndex, int
        grid.add(taskStartDate, 1, 0, 1, 1); // rowIndex, int colspan, int rowspan)
        grid.add(taskDeadLine, 2, 0, 1, 1);
    }
    
    private void displayTaskList(Task task) {
        
    }

}
