package parser;

import java.time.LocalDate;
import java.util.logging.*;


/**
 * 
 * @author A0121558H Dawson
 *
 */
public class UserInputParser implements ParserInterface {

    private String taskName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String command;
    private String[] userCommand;
    private int lengthOfInput;
    private int numToUse;
    private static Logger loggerTaskName = Logger.getLogger("setTaskName");

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
        isEnglishDate();
        taskName = setTaskName();
        setDate(numToUse);
        System.out.println("numTouse:" + numToUse);
        System.out.println("parser startdate " + startDate);
        System.out.println("parser enddate " + endDate);
    }

    public void setAttributesForGetCommands(String userInput) throws setAttributeException {
        removeWhiteSpaces(userInput);
        determineLengthOfInput();
        command = userCommand[0];
    }

    public static class setAttributeException extends RuntimeException {
        // the purpose of setting cusom exception is such that you can tell
        // exactly which class,
        // and which method causes the exception, rather than seeing a bunch of
        // red.
        // The purpose of using RuntimeException is because it is the only
        // 'unchcked' exception.
        // A quick google will provide you some insights on how chcked
        // expcetions are failed
        // experiments in various programming languages including Java
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
            if (numToUse == 1 || numToUse == 3) {
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

        return (parser.isLocalDate(userCommand[lengthOfInput - 1])
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

    /** Methods for updates **/

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

    private void setAttributesForUpdates(String input) {
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
        } else if (numToSetDate == 4) {
            startDate = endDate = stringToLocalDate("today");
        } else if (numToSetDate == 1) {
            startDate = endDate = stringToLocalDate("tomorrow");
        } else if (numToSetDate == 2) {
            startDate = endDate = stringToLocalDate(
                    userCommand[lengthOfInput - 2] + " " + userCommand[lengthOfInput - 1]);
        } else if (numToSetDate == 3) {// floating task only accept date format
                                       // now
            startDate = stringToLocalDate(userCommand[lengthOfInput - 1]);
            endDate = LocalDate.of(12, 12, 12);// palceholder for null
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