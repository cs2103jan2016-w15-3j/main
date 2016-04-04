package parser;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

public class UpdateParser extends UserInputParser{
	
	
	public void setAttributesForUpdates(String input) {
		DateTimeParser dateTimeParser = new DateTimeParser();
		removeWhiteSpaces(input);
		command = userCommand[0];
		determineLengthOfInput();
		userCommand = removeIndexToUpdate();

		if (!isFloatingUpdate()) {
			setTime(userCommand);
			userCommand = dateTimeParser.removeTime(userCommand);
			determineLengthOfInput();
			isEnglishDate();
			setDate(numToUse, lengthOfInput);
			userCommand = dateTimeParser.removeDate(userCommand);
			determineLengthOfInput();
			setTaskName();
		} else {
			determineLengthOfInput();
			userCommand = removeFloatingWord(getIndexForTaskNameUpdate());
			determineLengthOfInput();
			setFloatingTaskNameUpdate();
			setDateFloating();
			setTimeFloating();
		}
		System.out.println("task name:" + taskName);
		System.out.println("parser startdate " + startDate);
		System.out.println("parser enddate " + endDate);
		System.out.println("parser starttime " + startTime);
		System.out.println("parser endtime " + endTime);
	}
	private void setDateFloating() {
		startDate = LocalDate.MAX;
		endDate = LocalDate.MAX;
	}

	private void setTimeFloating() {
		startTime = LocalTime.MAX;
		endTime = LocalTime.MAX;
	}
	
	public int getIndex(String userInput) {
		removeWhiteSpaces(userInput);
		return Integer.parseInt(userCommand[1]) - 1;
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
		for (int i = 1; i < lengthOfInput; i++) {
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
		tempUserCommand.remove(1);
		return tempUserCommand.toArray(new String[tempUserCommand.size()]);
	}

	protected boolean isFloatingUpdate() {
		boolean check = false;

		for (String s : userCommand) {
			if (s.equals("floating")) {
				check = true;
			}
		}
		return check;
	}

}
