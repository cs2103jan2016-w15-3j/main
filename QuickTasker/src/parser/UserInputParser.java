package parser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *. 
 * @author A0121558H
 *
 */
public class UserInputParser {

	private String taskName;
	private LocalDate startDate;
	private LocalDate endDate;
	private String command;
	private String[] userCommand;
	private static int lengthOfInput;

	/**
	 * Constructor for Parser object.
	 * 
	 * @throws Exception
	 **/
	/**
	 * Public UserInputParser(String userInput) throws Exception {
	 * 
	 * }
	 *.*/
	public void setAttributes(String userInput) throws Exception {
		removeWhiteSpaces(userInput);
		determineLengthOfInput();
		command = userCommand[0];
		taskName = setTaskName();
		startDate = setStartDate();
		endDate = setEndDate();
	}

	/**
	 * Checks if it is a floating task. If yes, returns null.
	 * 
	 * @return
	 * @throws Exception
	 */
	public LocalDate setEndDate() throws Exception {
		if (!checkIfFloatingTask()) {
			return stringToLocalDate(userCommand[lengthOfInput - 1]);
		} else {
			return null;
		}
	}

	public LocalDate setStartDate() throws Exception {
		if (checkIfFloatingTask()) {
			return stringToLocalDate(userCommand[lengthOfInput - 1]);
		} else {
			return stringToLocalDate(userCommand[lengthOfInput - 2]);
		}
	}

	public String setTaskName() throws Exception {
		String output = "";
		int taskNameIndex = lengthOfInput - 3;

		if (checkIfFloatingTask()) {
			taskNameIndex--;
		}

		for (int i = 1; i < taskNameIndex; i++) {
			output += userCommand[i];
		}
		return output;
	}

	public String[] removeWhiteSpaces(String input) {
		userCommand = input.split(" ");
		return userCommand;
	}

	public void determineLengthOfInput() {
		lengthOfInput = userCommand.length;
	}

	public static LocalDate stringToLocalDate(String date) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		return LocalDate.parse(date, formatter);
	}

	public boolean checkIfFloatingTask() throws Exception {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		try {
			LocalDate.parse(userCommand[lengthOfInput - 1], formatter);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see parser.ParserInterface#getStartDate()
	 */

	public LocalDate getStartDate(String userInput) throws Exception {
		setAttributes(userInput);
		return startDate;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see parser.ParserInterface#getEndDate()
	 */
	public LocalDate getEndDate(String userInput) throws Exception {
		setAttributes(userInput);
		return endDate;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see parser.ParserInterface#getCommand()
	 */
	public Commands getCommand(String userInput) throws Exception {
		setAttributes(userInput);
		return DetermineCommandType.getCommand(command);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see parser.ParserInterface#getTaskName()
	 */
	public String getTaskName(String userInput) throws Exception {
		setAttributes(userInput);
		return taskName;
	}

	/**
	 * 1)check if only one word in userCommand like add 2)check date format
	 * 3)check cannot empty taskname.
	 */

}
