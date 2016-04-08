package ui.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import model.Task;
import parser.Commands;
import parser.DetermineCommandType;
import parser.UserInputParser;

//@@author A0121558H
public class InputValidator extends UserInputParser {
	private static Logger loggerValidator = Logger.getLogger("checkIfValid in InputValidator");


	public boolean isAllValid(String input) {
		boolean check = false;

		if (checkUndoRedo(input)) {
			return true;
		}
		
		check = checkIfNull(input) || checkCommand(input) || checkTaskName(input)
				|| checkDate(input) || checkTime(input);

		return check;
	}

	private static boolean checkIfNull(String input) {
		return input.isEmpty();
	}

	private boolean checkUndoRedo(String input) {
		Commands cmd = getCommand(input);

		if (cmd == Commands.UNDO_TASK || cmd == Commands.REDO_TASK) {
			return true;
		}
		return false;
	}

	private boolean checkCommand(String input) {
		setAttributes(input);
		Commands cmd = DetermineCommandType.getCommand(command);

		for (Commands c : Commands.values()) {
			if (cmd == c) {
				return true;
			}
		}
		return false;
	}

	private boolean checkTaskName(String input) {
		setAttributes(input);

		return taskName.length() != 0;
	}

	private boolean checkTime(String input) {

		setAttributes(input);

		if (isTimeAvailable()) {
			if (isEqualDate(input)) {
				return endTime.isAfter(startTime);
			}
			return false;
		} else {
			return true;
		}
	}

	private boolean checkDate(String input) {

		setAttributes(input);

		if (isDateAvailable()) {
			return endDate.isAfter(startDate) || endDate.isEqual(startDate);
		} else {
			return true;
		}
	}

	private boolean isEqualDate(String input) {
		setAttributes(input);

		return endDate.isEqual(startDate);
	}

	private boolean isTimeAvailable() {
		return !(startTime == LocalTime.MIN) || !(endTime == LocalTime.MIN);
	}

	private boolean isDateAvailable() {
		return !(startDate == LocalDate.MAX) || !(endDate == LocalDate.MAX);
	}

	public boolean checkAllValid(String userInput) {
		return isAllValid(userInput);
	}

	// Checks if the added task will have any clashes with any range of tasks in
	// the list.
	public boolean checkIfClash(ObservableList<Task> list, Task task) {
		loggerValidator.log(Level.INFO, "Start of checkIfClash");
		
		LocalDate startDate = task.getStartDate();
		LocalDate endDate = task.getDueDate(); // TODO CHANGE TO ENDDATE
		LocalTime startTime = task.getStartTime();
		LocalTime endTime = task.getEndTime();
		loggerValidator.log(Level.INFO, "Before checking if two localdates");

		if (isNotTwoDates(startDate, endDate)) {
			return false;
		} else {
			for (Task t : list) {
				if (isDaysOverlap(t, startDate, endDate)) {
					return true;					
				} else if (isSameDates(t, startDate, endDate)) {
					if (isTimeOverlap(t, startTime, endTime)) {
						return true;
					} else {
						continue;
					}
				}
			}
		}
		loggerValidator.log(Level.INFO, "End of checkIfClash");
		return false;
	}

	private boolean isNotTwoDates(LocalDate start, LocalDate end) {
		return start == LocalDate.MAX || end == LocalDate.MAX || start == LocalDate.MIN || end == LocalDate.MIN;
	}
	private boolean isDaysOverlap(Task t, LocalDate start, LocalDate end) {
		return (start.isBefore(t.getDueDate()) || start.isEqual(t.getDueDate()))
				&& (end.isAfter(t.getStartDate()) || end.isEqual(t.getStartDate()));
	}
	private boolean isSameDates(Task t, LocalDate start, LocalDate end) {
		return start.isEqual(t.getStartDate()) && end.equals(t.getDueDate());
	}
	private boolean isTimeOverlap(Task t, LocalTime start, LocalTime end) {
		return (start.isBefore(t.getEndTime()) || start.equals(t.getEndTime()))
		&& (end.isAfter(t.getStartTime()) || end.equals(t.getStartTime()));
	}
}
