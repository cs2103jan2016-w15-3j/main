package ui.controller;

import java.time.LocalDate;
import java.time.LocalTime;

import javafx.collections.ObservableList;
import model.Task;
import parser.Commands;
import parser.DetermineCommandType;
import parser.UserInputParser;

//@@author A0121558H
public class InputValidator extends UserInputParser {

	public boolean isAllValid(String input) {
		boolean check = false;

		if (checkUndoRedo(input)) {
			return true;
		}

		check = checkIfNull(input);
		check = checkCommand(input);
		check = checkTaskName(input);
		check = checkDate(input);
		check = checkTime(input);

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
		
		LocalDate startDate = task.getStartDate();
		LocalDate endDate = task.getDueDate(); // TODO CHANGE TO ENDDATE
		LocalTime startTime = task.getStartTime();
		LocalTime endTime = task.getEndTime();
		if (isNotTwoDates(startDate, endDate)) {
			return false;
		} else {
			for (Task t : list) {
				if ((startDate.isBefore(t.getDueDate()) || startDate.isEqual(t.getDueDate()))
						&& (endDate.isAfter(t.getStartDate()) || endDate.isEqual(t.getStartDate()))) {
					// There is overlap in days
					return true;					
				} else if (startDate.isEqual(t.getStartDate()) && endDate.equals(t.getDueDate())) {
					// Same dates
					if ((startTime.isBefore(t.getEndTime()) || startTime.equals(t.getEndTime()))
							&& (endTime.isAfter(t.getStartTime()) || endTime.equals(t.getStartTime()))) {
						// There is overlap in time
						return true;
					} else {
						continue;
					}
				}
			}
		}
		return false;
	}

	private boolean isNotTwoDates(LocalDate start, LocalDate end) {
		return start == LocalDate.MAX || end == LocalDate.MAX || start == LocalDate.MIN || end == LocalDate.MIN;
	}

}
