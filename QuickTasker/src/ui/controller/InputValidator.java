package ui.controller;

//@@author A0121558H
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import model.Task;
import parser.Commands;
import parser.RecurringParser;
import parser.UpdateParser;
import parser.UserInputParser;

public class InputValidator {
	private static Logger loggerValidator = Logger.getLogger("checkIfValid in InputValidator");
	private static final UserInputParser userInputParser = new UserInputParser();
	private static final UpdateParser updateParser = new UpdateParser();
	private static final RecurringParser recurringParser = new RecurringParser();
	Commands cmd;

	public boolean checkAllValid(String userInput) {
		return isAllValid(userInput);
	}

	// Checks if the added task will have any clashes with any range of tasks in
	// the list.
	public boolean checkIfClash(ObservableList<Task> list, Task task) {
		loggerValidator.log(Level.INFO, "Start of checkIfClash");

		LocalDate startDate = task.getStartDate();
		LocalDate endDate = task.getDueDate();
		LocalTime startTime = task.getStartTime();
		LocalTime endTime = task.getEndTime();
		loggerValidator.log(Level.INFO, "Before checking if two localdates");

		if (isNotTwoDates(startDate, endDate)) {
			// It is a floating task
			return false;
		} else if (isOneDate(startDate, endDate)) {
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

	private static boolean isNull(String input) {
		return input.isEmpty();
	}

	private boolean checkUndoRedo(String input) {
		cmd = userInputParser.getCommand(input);

		if (cmd == Commands.UNDO_TASK || cmd == Commands.REDO_TASK) {
			return true;
		}
		return false;
	}

	private boolean checkCommand(String input) {
		cmd = userInputParser.getCommand(input);

		for (Commands c : Commands.values()) {
			if (cmd == c) {
				return true;
			}
		}
		return false;
	}

	private boolean checkTaskName(String input) {
		if (cmd == Commands.CREATE_TASK) {
			return userInputParser.getTaskName(input).length() != 0;
		} else if (cmd == Commands.UPDATE_TASK) {
			return updateParser.getTaskName(input).length() != 0;
		} else if (cmd == Commands.RECUR_TASK) {
			return recurringParser.getTaskName(input).length() != 0;
		} else {
			return true;
		}
	}

	private boolean checkTime(String input) {
		if (cmd == Commands.CREATE_TASK) {
			LocalDate startAddDate = userInputParser.getStartDate(input);
			LocalDate endAddDate = userInputParser.getEndDate(input);
			LocalTime startAddTime = userInputParser.getStartTime(input);
			LocalTime endAddTime = userInputParser.getEndTime(input);
			if (isSameDate(startAddDate, endAddDate) && isTimeAvailable(startAddTime, endAddTime)) {
				return endAddTime.isAfter(startAddTime);
			}
			return true;
		} else if (cmd == Commands.UPDATE_TASK) {
			LocalDate startUpdateDate = updateParser.getStartDate(input);
			LocalDate endUpdateDate = updateParser.getEndDate(input);
			LocalTime startUpdateTime = updateParser.getStartTime(input);
			LocalTime endUpdateTime = updateParser.getEndTime(input);
			if (isSameDate(startUpdateDate, endUpdateDate) && isTimeAvailable(startUpdateTime, endUpdateTime)) {
				return endUpdateTime.isAfter(startUpdateTime);
			}
			return true;
		} else if (cmd == Commands.RECUR_TASK) {
			LocalDate startRecurDate = recurringParser.getStartDate(input);
			LocalDate endRecurDate = recurringParser.getEndDate(input);
			LocalTime startRecur = recurringParser.getStartTime(input);
			LocalTime endRecur = recurringParser.getEndTime(input);
			if (isSameDate(startRecurDate, endRecurDate) && isTimeAvailable(startRecur, endRecur)) {
				return endRecur.isAfter(startRecur);
			}
			return true;
		}
		return true;
	}

	private boolean isSameDate(LocalDate start, LocalDate end) {
		return start.isEqual(end);
	}

	private boolean checkDate(String input) {
		if (cmd == Commands.CREATE_TASK) {
			LocalDate startAdd = userInputParser.getStartDate(input);
			LocalDate endAdd = userInputParser.getEndDate(input);
			if (isDateAvailable(startAdd, endAdd)) {
				return endAdd.isAfter(startAdd) || endAdd.isEqual(startAdd);
			}
			return true;
		} else if (cmd == Commands.UPDATE_TASK) {
			LocalDate startUpdate = updateParser.getStartDate(input);
			LocalDate endUpdate = updateParser.getEndDate(input);
			if (isDateAvailable(startUpdate, endUpdate)) {
				return endUpdate.isAfter(startUpdate) || endUpdate.isEqual(startUpdate);
			}
			return true;
		} else if (cmd == Commands.RECUR_TASK) {
			LocalDate startRecur = recurringParser.getStartDate(input);
			LocalDate endRecur = recurringParser.getEndDate(input);
			if (isDateAvailable(startRecur, endRecur)) {
				return endRecur.isAfter(startRecur) || endRecur.isEqual(startRecur);
			}
			return true;
		}
		return true;
	}

	private boolean isTimeAvailable(LocalTime start, LocalTime end) {
		return !(start == LocalTime.MIN) || !(end == LocalTime.MAX);
	}

	private boolean isDateAvailable(LocalDate start, LocalDate end) {
		return !(start == LocalDate.MIN) && !(end == LocalDate.MAX);
	}

	private boolean isOneDate(LocalDate start, LocalDate end) {

		return (start.isEqual(LocalDate.MIN) && !end.isEqual(LocalDate.MAX))
				|| (!start.isEqual(LocalDate.MIN) && end.isEqual(LocalDate.MAX));
	}

	private boolean isAllValid(String input) {
		boolean check = false;

		if (checkUndoRedo(input)) {
			return true;
		}
		check = !isNull(input) && checkCommand(input) && checkTaskName(input) && checkDate(input) && checkTime(input);
		return check;
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
