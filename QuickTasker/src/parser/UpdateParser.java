package parser;
//@@author A0121558H

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UpdateParser extends UserInputParser {
	private int numToRecur;
	private String durationToRecur;
	private final int INDEX_OF_TASK = 1;
	private static Logger loggerUpdate = Logger.getLogger("setAttributesForUpdates in UpdateParser");

	public void setAttributesForUpdates(String input) {
		loggerUpdate.log(Level.INFO, "Start of setAttributesForUpdates");

		DateTimeParser dateTimeParser = new DateTimeParser();
		removeWhiteSpaces(input);
		command = userCommand[0];
		determineLengthOfInput();
		userCommand = removeIndexToUpdate();
		determineLengthOfInput();

		loggerUpdate.log(Level.INFO, "Before checking if is floating update");

		if (!isFloatingUpdate()) {
			loggerUpdate.log(Level.INFO, "Not floating update");
			if (!isRecurUpdate()) {
				loggerUpdate.log(Level.INFO, "Not recurring update");
				if (!isTimeUpdate()) {
					setTime(userCommand);
					userCommand = dateTimeParser.removeTime(userCommand);
					determineLengthOfInput();
					isEnglishDate();
					setDate(numToUse, lengthOfInput);
					userCommand = dateTimeParser.removeDate(userCommand);
					determineLengthOfInput();
					setTaskNameForUpdates();
				} else {
					setTime(userCommand);
				}
			} else {
				setNumToRecur();
				setDurationToRecur();
			}
		} else {
			loggerUpdate.log(Level.INFO, "Floating update");

			determineLengthOfInput();
			userCommand = removeFloatingWord(getIndexForTaskNameUpdate());
			determineLengthOfInput();
			setFloatingTaskNameUpdate();
			setDateFloating();
			setTimeFloating();
		}
		loggerUpdate.log(Level.INFO, "End of setAttributesForUpdates");

		System.out.println("task name:" + taskName);
		System.out.println("parser startdate " + startDate);
		System.out.println("parser enddate " + endDate);
		System.out.println("parser starttime " + startTime);
		System.out.println("parser endtime " + endTime);
	}

	private void setDateFloating() {
		startDate = LocalDate.MIN;
		endDate = LocalDate.MAX;
	}

	private void setTimeFloating() {
		startTime = LocalTime.MIN;
		endTime = LocalTime.MAX;
	}

	private void setTaskNameForUpdates() {

		String output = "";

		for (int i = 1; i < lengthOfInput; i++) {
			System.out.println("I:  " + userCommand[i]);
			output += userCommand[i] + " ";
		}
		output = output.trim();
		taskName = output;
	}

	public int getIndexForUpdates(String userInput) {
		removeWhiteSpaces(userInput);
		return Integer.parseInt(userCommand[1]);
	}

	public String getTaskName(String userInput) {
		setAttributesForUpdates(userInput);
		return taskName;
	}

	public LocalDate getStartDate(String userInput) {
		setAttributesForUpdates(userInput);
		return startDate;
	}

	public LocalDate getEndDate(String userInput) {
		setAttributesForUpdates(userInput);
		return endDate;
	}

	public LocalTime getStartTime(String userInput) {
		setAttributesForUpdates(userInput);
		return startTime;
	}

	public LocalTime getEndTime(String userInput) {
		setAttributesForUpdates(userInput);
		return endTime;
	}

	private void setFloatingTaskNameUpdate() {
		String output = "";
		for (int i = 2; i < lengthOfInput; i++) {
			output += userCommand[i];
			output += " ";
		}
		output = output.trim();
		taskName = output;
	}

	private String[] removeFloatingWord(int indexToRemove) {
		ArrayList<String> tempUserCommand = new ArrayList<String>(Arrays.asList(userCommand));
		tempUserCommand.remove(indexToRemove);
		return tempUserCommand.toArray(new String[tempUserCommand.size()]);
	}

	private int getIndexForTaskNameUpdate() {
		int index = 0;
		for (int i = lengthOfInput; i > 0; i--) {

			if (userCommand[i - 1].equals("floating")) {
				index = i - 1;
				break;
			}
		}
		return index;
	}

	private String[] removeIndexToUpdate() {
		ArrayList<String> tempUserCommand = new ArrayList<String>(Arrays.asList((userCommand)));
		tempUserCommand.remove(INDEX_OF_TASK);
		return tempUserCommand.toArray(new String[tempUserCommand.size()]);
	}

	private boolean isFloatingUpdate() {
		boolean check = false;

		for (String s : userCommand) {
			if (s.equals("floating")) {
				check = true;
			}
		}
		return check;
	}

	private boolean isTimeUpdate() {
		DateTimeParser parser = new DateTimeParser();
		return lengthOfInput==3 && parser.isTime(userCommand[lengthOfInput - 2])
				&& parser.isTime(userCommand[lengthOfInput - 1]);
	}

	// remeber to shift getter at bottom
	private boolean isRecurUpdate() {
		return userCommand[1].equalsIgnoreCase("recur");
	}

	private void setNumToRecur() {
		numToRecur = Integer.parseInt(userCommand[lengthOfInput - 2]);
	}

	private void setDurationToRecur() {
		durationToRecur = userCommand[lengthOfInput - 1];
	}

	public int getNumToRecur(String input) {
		setAttributesForUpdates(input);
		return numToRecur;
	}

	public String getDurationToRecur(String input) {
		setAttributesForUpdates(input);
		return durationToRecur;
	}
}
