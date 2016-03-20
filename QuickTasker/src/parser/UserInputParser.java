package parser;

import java.time.LocalDate;

/**
 * .
 *
 * @author A0121558H
 */
public class UserInputParser implements ParserInterface {

    private String taskName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String command;
    private String[] userCommand;
    private int lengthOfInput;
    private int numToUse;

    public UserInputParser() {
        this.taskName = "";
        this.startDate = LocalDate.MIN;
        this.endDate = LocalDate.MAX;
        this.command = "";
    }

    public void setAttributes(String userInput) {
        removeWhiteSpaces(userInput);
        determineLengthOfInput();
        command = userCommand[0];
        checkIfEnglishDate();
        taskName = setTaskName();
        setDate(checkIfEnglishDate());
        System.out.println("parser startdate " + startDate);
        System.out.println("parser enddate " + endDate);
    }

    public void setAttributesForGetCommands(String userInput) throws setAttributeException {
        removeWhiteSpaces(userInput);
        determineLengthOfInput();
        command = userCommand[0];
    }

    public static class setAttributeException extends RuntimeException {
    }

    public LocalDate setEndDate() {
        return stringToLocalDate(userCommand[lengthOfInput - 1]);
    }

    public LocalDate setStartDate() {
        return stringToLocalDate(userCommand[lengthOfInput - 2]);
    }

    public String setTaskName() {
        String output = "";
        if (numToUse == 1) {
            int taskNameIndex = lengthOfInput - 1;

            System.out.println("index " + taskNameIndex);
            for (int i = 1; i < taskNameIndex; i++) {
                output += userCommand[i] + " ";
            }
            output = output.trim();
        } else {
            int taskNameIndex = lengthOfInput - 2;

            System.out.println("index " + taskNameIndex);
            for (int i = 1; i < taskNameIndex; i++) {
                output += userCommand[i] + " ";
            }
            output = output.trim();
        }
        return output;
    }

    public String[] removeWhiteSpaces(String input) {
        userCommand = input.split("\\s+");
        return userCommand;
    }

    public void determineLengthOfInput() {
        lengthOfInput = userCommand.length;
    }

    public static LocalDate stringToLocalDate(String date) {

        DateTimeParser parser = new DateTimeParser();
        return parser.parseDate(date);
    }

    public LocalDate getStartDate(String userInput) {
        setAttributes(userInput);
        return startDate;
    }

    public LocalDate getEndDate(String userInput) {
        setAttributes(userInput);

        return endDate;
    }

    public String getTaskName(String userInput) {
        setAttributes(userInput);
        return taskName;
    }

    public int getTaskIndex(String input) {
        String[] splitted = input.split("\\s+");
        return Integer.parseInt(splitted[1]) - 1;
    }

    public Commands getCommand(String userInput) {
        setAttributesForGetCommands(userInput);
        return DetermineCommandType.getCommand(command);
    }

    /**
     * Methods for updates *.
     */

    public int getIndexForUpdate(String userInput) {
        removeWhiteSpaces(userInput);
        return Integer.parseInt(userCommand[1]) - 1;
    }

    public String getTaskNameForUpdate(String userInput) {
        setAttributesForUpdates(userInput);
        return taskName;
    }

    public LocalDate getStartDateForUpdate(String userInput) {
        setAttributesForUpdates(userInput);
        return startDate;
    }

    public LocalDate getEndDateForUpdate(String userInput) {
        setAttributesForUpdates(userInput);
        return endDate;
    }

    public void setAttributesForUpdates(String input) {
        removeWhiteSpaces(input);
        command = userCommand[0];
        determineLengthOfInput();
        taskName = setTaskNameForUpdates();
        startDate = stringToLocalDate(userCommand[lengthOfInput - 2]);
        endDate = stringToLocalDate(userCommand[lengthOfInput - 1]);
    }

    private void setDate(int numToSetDate) {
        if (numToSetDate == 0) {
            startDate = stringToLocalDate(userCommand[lengthOfInput - 2]);
            endDate = stringToLocalDate(userCommand[lengthOfInput - 1]);
        } else if (numToSetDate == 1) {
            startDate = endDate = stringToLocalDate("today");

        } else {
            startDate = endDate = stringToLocalDate(
                    userCommand[lengthOfInput - 2] + " " + userCommand[lengthOfInput - 1]);
        }
    }

    private int checkIfEnglishDate() {
        // 1 is tmr
        // 2 is either next day or day after
        // 0 is not english
        String toCheck = userCommand[lengthOfInput - 2] + " " + userCommand[lengthOfInput - 1];
        numToUse = 0;

        if (userCommand[lengthOfInput - 1].equals("tomorrow")) {
            numToUse = 1;
        } else if (toCheck.equals("next day") || toCheck.equals("day after")) {
            numToUse = 2;
        }
        return numToUse;
    }

    public String setTaskNameForUpdates() {
        String output = "";
        int taskNameIndex = lengthOfInput - 2;

        for (int i = 2; i < taskNameIndex; i++) {
            output += userCommand[i] + " ";
        }
        return output.trim();
    }
}