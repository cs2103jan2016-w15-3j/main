package parser;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.logging.*;

/**
 * @author A0121558H Dawson
 */
public class UserInputParser implements ParserInterface {

    private String taskName;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String command;
    private String[] userCommand;
    private int lengthOfInput;
    private int numToUse;
    private static Logger loggerTaskName = Logger.getLogger("setTaskName");

    public UserInputParser() {
        this.taskName = "";
        this.startDate = LocalDate.MIN;
        this.endDate = LocalDate.MIN;
        this.startTime = LocalTime.MIN;
        this.endTime = LocalTime.MIN;
        this.command = "";
    }

    public void setAttributes(String userInput) {
        DateTimeParser dateTimeParser = new DateTimeParser();

        removeWhiteSpaces(userInput);
        setTime();
        userCommand = dateTimeParser.removeTime(userCommand);
        determineLengthOfInput();
        command = userCommand[0];
        isEnglishDate();
        taskName = setTaskName();
        setDate(numToUse);
        System.out.println("numTouse:" + numToUse);
        System.out.println("parser startdate " + startDate);
        System.out.println("parser enddate " + endDate);
        System.out.println("parser starttime " + startTime);
        System.out.println("parser endtime " + endTime);
    }

    public void setAttributesForGetCommands(String userInput) throws setAttributeException {
        removeWhiteSpaces(userInput);
        determineLengthOfInput();
        command = userCommand[0];
    }

    public static class setAttributeException extends RuntimeException {
    }

    /**
     * Checks if it is a floating task. If yes, returns null.
     */
    public LocalDate setEndDate() {
        return stringToLocalDate(userCommand[lengthOfInput - 1]);
    }

    public LocalDate setStartDate() {
        return stringToLocalDate(userCommand[lengthOfInput - 2]);
    }

    // LOGGING
    public String setTaskName() {
        loggerTaskName.log(Level.INFO, "Start of process");
        String output = "";

        try {
            if (numToUse == 1) {
                int taskNameIndex = lengthOfInput - 1;

                for (int i = 1; i < taskNameIndex; i++) {
                    output += userCommand[i] + " ";
                }
                output = output.trim();
            } else if (numToUse == 3) {
                // floating task
                int taskNameIndex = lengthOfInput;

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
        } catch (Exception e) {
            loggerTaskName.log(Level.WARNING, "Error in taskname processing", e);
        }
        loggerTaskName.log(Level.INFO, "end of processing");

        return output;
    }

    // TRYCATCH
    public String[] removeWhiteSpaces(String input) {

        try {
            userCommand = input.split("\\s+");
        } catch (Exception e) {// change into logger
            System.out.println("Error in splitting");
            System.out.println("Please enter again");
        }
        return userCommand;
    }

    public void determineLengthOfInput() {
        lengthOfInput = userCommand.length;
    }

    private boolean isFloating() {

        DateTimeParser parser = new DateTimeParser();

        return (!parser.isLocalDate(userCommand[lengthOfInput - 1])
                && !parser.isLocalDate(userCommand[lengthOfInput - 2]));
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

    public LocalTime getStartTime(String userInput) {
        setAttributes(userInput);

        return startTime;
    }

    public LocalTime getEndTime(String userInput) {
        setAttributes(userInput);

        return endTime;
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
     * Methods for updates
     **/

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
            startDate = endDate = stringToLocalDate("tomorrow");
        } else if (numToSetDate == 2) {
            startDate = endDate = stringToLocalDate(
                    userCommand[lengthOfInput - 2] + " " + userCommand[lengthOfInput - 1]);
        } else if (numToSetDate == 3) {// floating task
            startDate = LocalDate.MIN;
            endDate = LocalDate.MIN;// placeholder for null
        } else if (numToSetDate == 4) {
            startDate = endDate = stringToLocalDate("today");
        }
    }

    public void setTime() {
        DateTimeParser parser = new DateTimeParser();
        ArrayList<Integer> indicesTime = parser.indicesToDetermineTime(userCommand);

        if (indicesTime.size() == 0) {
            return;
        }
        ArrayList<LocalTime> localTimes = parser.parseTime(userCommand, indicesTime);

        if (indicesTime.size() == 1) {
            startTime = localTimes.get(0);
            endTime = null;
        }
        if (indicesTime.size() == 2) {
            startTime = localTimes.get(0);
            endTime = localTimes.get(1);
        }
    }

    private int isEnglishDate() {
        // 1 is tmr
        // 2 is either next day or day after
        // 3 is floating task
        // 0 is not english
        // 4 is today
        String toCheck = userCommand[lengthOfInput - 2] + " " + userCommand[lengthOfInput - 1];
        numToUse = 0;

        if (userCommand[lengthOfInput - 1].equals("tomorrow")) {
            numToUse = 1;
        } else if (toCheck.equals("next day") || toCheck.equals("day after")) {
            numToUse = 2;
        } else if (isFloating()) {
            numToUse = 3;
        } else if (userCommand[lengthOfInput - 1].equals("today")) {
            numToUse = 4;
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