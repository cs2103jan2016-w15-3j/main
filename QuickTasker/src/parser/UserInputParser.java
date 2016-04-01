package parser;

/**
 * TODO
 * FIX "edge of tomorrow" tomorrow
 * scan from behind. stop once hit date
 */

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author A0121558H Dawson
 */
public class UserInputParser implements ParserInterface {

	protected String taskName;
	protected LocalDate startDate;
	protected LocalDate endDate;
	protected LocalTime startTime;
	protected LocalTime endTime;
    protected String command;
    protected String[] userCommand;
    private int lengthOfInput;
    private int numToUse;
    private static Logger loggerTaskName = Logger.getLogger("setTaskName");
    
    private static final int NUMBER_NORMAL =0;
    private static final int NUMBER_TOMORROW=1;
    private static final int NUMBER_NEXT_DAY_DAY_AFTER=2;
    private static final int NUMBER_FLOATING=3;
    private static final int NUMBER_TODAY=4;
    private static final int NUMBER_ONLY_ONE_DATE=5;

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

        setTime(userCommand);
        userCommand = dateTimeParser.removeTime(userCommand);

        determineLengthOfInput();
        isEnglishDate();
        setDate(numToUse, lengthOfInput);
        userCommand = dateTimeParser.removeDate(userCommand);

        determineLengthOfInput();
        command = userCommand[0];
        setTaskName();
        System.out.println("task name:" + taskName);
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
    public void setTaskName() {
        // UPDATED AS OF 23/3/2016

        loggerTaskName.log(Level.INFO, "Start of process");
        String output = "";

        try {
            for (int i = 1; i < lengthOfInput; i++) {
                output += userCommand[i] + " ";
            }
            output = output.trim();

        } catch (Exception e) {
            loggerTaskName.log(Level.WARNING, "Error in taskname processing", e);
        }
        loggerTaskName.log(Level.INFO, "end of processing");

        taskName = output;
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

        return (!parser.isDate(userCommand[lengthOfInput - 1]) && !parser
                .isDate(userCommand[lengthOfInput - 2]));
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

    public int getIndexForDone(String userInput) {
        removeWhiteSpaces(userInput);
        return Integer.parseInt(userCommand[1]) - 1;
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

    public LocalTime getStartTimeForUpdate(String userInput) {
        setAttributesForUpdates(userInput);
        return startTime;
    }

    public LocalTime getEndTimeForUpdate(String userInput) {
        setAttributesForUpdates(userInput);
        return endTime;
    }

    public void setAttributesForUpdates(String input) {
        //TODO update date only 
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

    private void setFloatingTaskNameUpdate() {
        String output = "";
        for (int i = 1; i < lengthOfInput; i++) {
            output += userCommand[i];
            output += " ";
        }
        output = output.trim();
        taskName = output;
    }

    private void setDateFloating() {
        startDate = LocalDate.MAX;
        endDate = LocalDate.MAX;
    }

    private void setTimeFloating() {
        startTime = LocalTime.MAX;
        endTime = LocalTime.MAX;
    }

    private String[] removeFloatingWord(int indexToRemove) {
        ArrayList<String> tempUserCommand = new ArrayList<String>(Arrays.asList(userCommand));
        tempUserCommand.remove(indexToRemove);
        return tempUserCommand.toArray(new String[tempUserCommand.size()]);

    }

    private int getIndexForTaskNameUpdate() {
        int index = 0;
        for (int i = lengthOfInput; i >= 0; i--) {

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
    /* Methods for updates end */

    public void setDate(int numToSetDate, int length) {
        if (numToSetDate == NUMBER_NORMAL) {
            startDate = stringToLocalDate(userCommand[length - 2]);
            endDate = stringToLocalDate(userCommand[length - 1]);
        } else if (numToSetDate == NUMBER_TOMORROW) {
            startDate = endDate = stringToLocalDate("tomorrow");
        } else if (numToSetDate == NUMBER_NEXT_DAY_DAY_AFTER) {
            startDate = endDate = stringToLocalDate(
                    userCommand[length - 2] + " " + userCommand[length - 1]);
        } else if (numToSetDate == NUMBER_FLOATING) {// floating task
            startDate = LocalDate.MAX;
            endDate = LocalDate.MAX;// placeholder for null
        } else if (numToSetDate == NUMBER_TODAY) {
            startDate = endDate = stringToLocalDate("today");
        } else if (numToSetDate == NUMBER_ONLY_ONE_DATE) {
            startDate = stringToLocalDate(userCommand[length - 1]);
            endDate = LocalDate.MIN;
        }
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

    public void setTime(String[] input) {
        DateTimeParser parser = new DateTimeParser();
        ArrayList<Integer> indicesTime = parser.indicesToDetermineTime(input);

        for (Integer i : indicesTime)
            System.out.println("i: " + i);

        if (indicesTime.size() == 0) {
            return;
        }
        ArrayList<LocalTime> localTimes = parser.parseTime(input, indicesTime);

        if (indicesTime.size() == 1) {
            startTime = localTimes.get(0);
            endTime = null;
        }
        if (indicesTime.size() == 2) {
            startTime = localTimes.get(0);
            endTime = localTimes.get(1);
        }
    }

    private void isEnglishDate() {
        // 1 is tmr
        // 2 is either next day or day after
        // 3 is floating task
        // 0 is not two dates
        // 4 is today
        // 5 is only one date
        DateTimeParser parser = new DateTimeParser();
        String toCheck = userCommand[lengthOfInput - 2] + " " + userCommand[lengthOfInput - 1];
        numToUse = 0;

        if (userCommand[lengthOfInput - 1].equals("tomorrow")) {
            numToUse = NUMBER_TOMORROW;
        } else if (toCheck.equals("next day") || toCheck.equals("day after")) {
            numToUse = NUMBER_NEXT_DAY_DAY_AFTER;
        } else if (isFloating()) {
            numToUse = NUMBER_FLOATING;
        } else if (userCommand[lengthOfInput - 1].equals("today")) {
            numToUse = NUMBER_TODAY;
        } else if (parser.isDate(userCommand[lengthOfInput - 1]) && !parser
                .isDate(userCommand[lengthOfInput - 2])) {
            numToUse = NUMBER_ONLY_ONE_DATE;
        }
    }

    public String setTaskNameForUpdates() {
        String output = "";
        int taskNameIndex = lengthOfInput - 2;

        for (int i = 2; i < taskNameIndex; i++) {
            output += userCommand[i] + " ";
        }
        return output.trim();
    }

    private void print() {
        System.out.print("IN PRINT()");

        for (String s : userCommand) {
            System.out.print(s);
            System.out.print(" ");
        }
    }

}