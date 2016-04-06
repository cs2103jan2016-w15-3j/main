package parser;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * 
 * @@author A0121558H
 * 
 */
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

		if (cmd == Commands.UNDO_TASK || cmd == Commands.REDO) {
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

}
