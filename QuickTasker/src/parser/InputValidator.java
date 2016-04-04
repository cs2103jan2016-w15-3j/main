package parser;

/**
 * 
 * @@author A0121558H
 * 
 */
public class InputValidator extends UserInputParser {

	private boolean isNull;
	private boolean isCommandValid;
	private boolean isTaskNameValid;
	private boolean isDateValid;
	private boolean isTimeValid;

	public InputValidator(String userCommand) {
		isNull = checkIfNull(userCommand);
		isCommandValid = checkCommand(userCommand);
		isTaskNameValid = checkTaskName(userCommand);
		isDateValid = checkDate(userCommand);
		isTimeValid = checkTime(userCommand);
	}
	public static boolean isAllValid(String input) {
		InputValidator inputValidator= new InputValidator(input);
		
		return inputValidator.isNull || inputValidator.isCommandValid ||
				inputValidator.isDateValid || inputValidator.isTaskNameValid 
				||inputValidator.isTimeValid;
	}

	private boolean checkIfNull(String input) {
		return input.isEmpty();
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

		if (isEqualDate(input)) {
			return endTime.isAfter(startTime);
		}
		return false;
	}

	private boolean checkDate(String input) {

		setAttributes(input);

		return endDate.isAfter(startDate) || endDate.isEqual(startDate);
	}

	private boolean isEqualDate(String input) {
		setAttributes(input);

		return endDate.isEqual(startDate);
	}

}
