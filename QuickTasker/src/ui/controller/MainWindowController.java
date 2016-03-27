package ui.controller;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import logic.Logic;
import model.RecurringTask;
import model.Task;
import parser.Commands;
import parser.ParserInterface;
import parser.UserInputParser;
import ui.model.TaskListCell;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ui.controller.TaskDoneEvent.TASK_COMPLETE;

/**
 * Author: A0133333U/A0130949Y/A0126077E
 */
public class MainWindowController implements Initializable {

	private static Logger logger;
	private Main main;
	private final ParserInterface parser = new UserInputParser();
	private final Logic operations = new Logic();

	@FXML
	private JFXTextField commandBox;
	@FXML
	private JFXListView<Task> printedPlanner;
	@FXML
	private Label userMessage;
	private ObservableList<Task> plannerEntries;
	private Timeline feedbackTimeline;

	// Display messages as visual feedback for users
	private static final String MESSAGE_WELCOME = "Welcome to quickTasker!";
	private static final String MESSAGE_ADD_CONFIRMED = "Task added to list.";
	private static final String MESSAGE_DELETE_CONFIRMED = "Task deleted from list.";
	private static final String MESSAGE_COMPLETED_CONFIRMED = "Task marked as completed.";
	private static final String MESSAGE_EDIT_CONFIRMED = "Task edited.";

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		setMain(main);
		initPlanner();
		setCellFactory();
		initLogger();
	}

	private void initLogger() {
		logger = Logger.getLogger("UILogger");
		logger.setLevel(Level.INFO);
	}

	private void initPlanner() {
		plannerEntries = FXCollections.observableArrayList(operations.getTasks());
		printedPlanner.setItems(plannerEntries);
	}
/*
	// @author: A0133333U
	private FadeTransition initFadeIn(Node node, int duration) {
		FadeTransition fadeIn = new FadeTransition(new Duration(duration));
		fadeIn.setNode(node);
		fadeIn.setToValue(1);
		return fadeIn;
	}

	// @author: A0133333U
	private FadeTransition initFadeOut(Node node, int duration) {
		FadeTransition fadeOut = new FadeTransition(new Duration(duration));
		fadeOut.setNode(node);
		fadeOut.setToValue(0);
		return fadeOut;
	}
	
	// @author: A0133333U
	public void setFeedback(String messageToUser) {
		FadeTransition fadeIn = initFadeIn(userMessage, 500);
		FadeTransition fadeOut = initFadeOut(userMessage, 1000);

		feedbackTimeline.stop();
		feedbackTimeline = generateFeedbackTimeline(feedback, fadeIn,
		fadeOut);
		feedbackTimeline.play();
	}
	
	// @author: A0133333U
    private void initFeedbackLabel(String messageToUser) {
        userMessage.setOpacity(0);
        userMessage.setText(messageToUser);
    }

	// @author: A013333U
	private Timeline generateFeedbackTimeline(String feedback, FadeTransition fadeIn, FadeTransition fadeOut) {
		return new Timeline(new KeyFrame(new Duration(1), // cannot set duration to 0
				new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						initFeedbackLabel(feedback);
						fadeIn.play();
					}
				}), new KeyFrame(Duration.seconds(8), new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						fadeOut.play();
					}
				}));
	}*/

	void setMain(Main main) {
		this.main = main;

	}

	protected boolean isEmptyInput(String input) {
		return input == null || input.isEmpty() || "".equals(input.trim());
	}

	private boolean enterKeyIsPressed(KeyEvent event) {
		return KeyCode.ENTER.equals(event.getCode());
	}

	@FXML
	private void handleEnterKeyPressed(KeyEvent event) {
		String userInput = commandBox.getText();
		if (!isEmptyInput(userInput) && enterKeyIsPressed(event)) {
			logger.log(Level.INFO, "User typed in : <" + userInput + "> command string");
			try {
				performOperations(userInput);
			} catch (UIOperationException e) {
				logger.log(Level.SEVERE,
						"Error occured at " + this.getClass().getName() + " within performOperation method. \n");
			}

		}
	}

	private class UIOperationException extends RuntimeException {
	}

	private void performOperations(String userInput) throws UIOperationException {
		try {
			if (parser.getCommand(userInput) == Commands.CREATE_TASK) {
				createTask(userInput);
			} else if (parser.getCommand(userInput) == Commands.DELETE_TASK) {
				deleteTask(userInput);
			} else if (parser.getCommand(userInput) == Commands.UPDATE_TASK) {
				updateTask(userInput);
			} else if (parser.getCommand(userInput) == Commands.UNDO_TASK) {
				undoTask();
			} else if (parser.getCommand(userInput) == Commands.REDO) {
				redoTask();
			} else if (parser.getCommand(userInput) == Commands.EXIT) {
				operations.exit();
			} else if (parser.getCommand(userInput) == Commands.SORT_TASK) {
				System.out.println("getting the command");
				sortTask(userInput);
			} else if (userInput.contains("mark")) {
				markTaskCompleted(userInput);
			}
		} catch (Exception e) {
			throw new UIOperationException();
		}
	}

	private void markTaskCompleted(String userInput) throws Exception {
		int i = parser.getIndexForDone(userInput);
		Task task = plannerEntries.get(i);
		task.setDone(true); // logic should handle
		printedPlanner.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		printedPlanner.getSelectionModel().select(i);
		printedPlanner.fireEvent(new TaskDoneEvent(task));
		javafx.concurrent.Task<Void> sleeper;
		sleeper = makeSleeper(500);
		sleeper.setOnSucceeded(event -> {
			printedPlanner.getSelectionModel().clearSelection();
			commandBox.clear();
		});
		new Thread(sleeper).start();

	}

	private javafx.concurrent.Task<Void> makeSleeper(int duration) {
		return new javafx.concurrent.Task<Void>() {
			@Override
			protected Void call() throws Exception {
				try {
					Thread.sleep(duration);
				} catch (InterruptedException e) {
					// do nothing. handles library bug
				}
				return null;
			}
		};
	}

	private void sortTask(String userInput) {
		System.out.println("Sorting");
		plannerEntries = FXCollections.observableArrayList(operations.sort());
		afterOperation();
	}

	private void redoTask() {
		plannerEntries = FXCollections.observableArrayList(operations.redo());
		afterOperation();
	}

	private void undoTask() {
		plannerEntries = FXCollections.observableArrayList(operations.undo());
		afterOperation();
	}

	private void updateTask(String userInput) throws Exception {
		int indexOfTask = parser.getIndexForUpdate(userInput);
		printedPlanner.getSelectionModel().select(indexOfTask);
		Task newTask = new Task(parser.getTaskNameForUpdate(userInput), parser.getStartDateForUpdate(userInput),
				parser.getEndDateForUpdate(userInput));
		/*
		 * plannerEntries.remove(indexOfTask);
		 * plannerEntries.add(indexOfTask,newTask);
		 */

		plannerEntries = FXCollections.observableArrayList(operations.updateTask(newTask, indexOfTask));
		printedPlanner.getSelectionModel().clearSelection();
		afterOperation();
	}

	private void deleteTask(String userInput) throws Exception {
		int taskIndex;
		taskIndex = parser.getTaskIndex(userInput);
		// plannerEntries.remove(taskIndex);
		plannerEntries = FXCollections.observableArrayList(operations.deleteTask(taskIndex));
		afterOperation();
	}

	private void refresh() {
		printedPlanner.setItems(plannerEntries);
	}

	private void createTask(String userInput) throws Exception {
		Task newTask = makeTask(parser.getTaskName(userInput), parser.getStartDate(userInput),
				parser.getEndDate(userInput), parser.getStartTime(userInput), parser.getEndTime(userInput));
		/*
		 * plannerEntries.add(newTask); printedPlanner.setItems(plannerEntries);
		 * commandBox.clear(); operations.addTask(newTask);
		 */
		plannerEntries = FXCollections.observableArrayList(operations.addTask(newTask));
		afterOperation();
	}

	private void createRecurringTask(String userInput) throws Exception {
		RecurringTask newTask = makeRecurringTask("taskName", LocalDate.now(), LocalDate.now(), "week");
		plannerEntries = FXCollections.observableArrayList(operations.addTask(newTask));
		afterOperation();
	}

	private void afterOperation() {
		setCellFactory();
		refresh();
		commandBox.clear();
	}

	private Task makeTask(String taskName, LocalDate startDate, LocalDate dueDate, LocalTime startTime, LocalTime endTime) throws Exception {
		return new Task(taskName, startDate, dueDate, startTime, endTime);
	}

	private RecurringTask makeRecurringTask(String taskName, LocalDate startDate, LocalDate dueDate, String type)
			throws Exception {
		return new RecurringTask(taskName, startDate, dueDate, type);
	}

	private void setCellFactory() {
		printedPlanner.setCellFactory(param -> {

			TaskListCell listCell = new TaskListCell(plannerEntries);
			printedPlanner.addEventFilter(TASK_COMPLETE, event -> new Thread(() -> {
				Thread.currentThread().setUncaughtExceptionHandler((t, e) -> Platform.runLater(System.out::println));
				if (listCell.getTask().equals(event.getTask()))
					listCell.getCheckBox().fire();
			}).start());
			/*
			 * plannerEntries.addListener(new ListChangeListener<Task>() {
			 * 
			 * @Override public void onChanged(Change<? extends Task> c) {
			 * listCell.updateIndex(plannerEntries); } });
			 */
			return listCell;

		});
	}

	class SearchHighlightedTextCell extends ListCell<String> {
		private static final String HIGHLIGHT_CLASS = "search-highlight";
		private final StringProperty searchText;

		SearchHighlightedTextCell(StringProperty searchText) {
			this.searchText = searchText;
		}
	}
}
