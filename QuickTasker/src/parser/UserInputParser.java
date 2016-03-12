package parser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

/**
 * .
 * 
 * @author A0121558H
 *
 */
public class UserInputParser implements ParserInterface {

    private String taskName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String command;
    private String[] userCommand;
    private int lengthOfInput;

    public UserInputParser() {
        this.taskName = "";
        this.startDate = LocalDate.MIN;
        this.endDate = LocalDate.MAX;
        this.command = "";
    }

    /**
     * Constructor for Parser object.
     * 
     * @throws Exception
     **/
    /**
     * Public UserInputParser(String userInput) throws Exception {
     * 
     * } .
     */
    public void setAttributes(String userInput) {
        removeWhiteSpaces(userInput);
        determineLengthOfInput();
        command = userCommand[0];
        taskName = setTaskName();
        startDate = setStartDate();
        endDate = setEndDate();
    }

    public void setAttributesForGetCommands(String userInput) throws setAttributeException {
        removeWhiteSpaces(userInput);
        determineLengthOfInput();
        command = userCommand[0];
    }

    public static class setAttributeException extends RuntimeException {}

    public LocalDate setEndDate() {
        return stringToLocalDate(userCommand[lengthOfInput - 1]);
    }

    public LocalDate setStartDate() {
        return stringToLocalDate(userCommand[lengthOfInput - 2]);
    }

    public String setTaskName() {
        String output = "";
        int taskNameIndex = lengthOfInput - 2;

        for (int i = 1; i < taskNameIndex; i++) {
            output += userCommand[i] + " ";
            System.out.println("output " + output);
        }
        return output.trim();
    }

    public String[] removeWhiteSpaces(String input) {
        userCommand = input.split("\\s+");
        return userCommand;
    }

    public void determineLengthOfInput() {
        lengthOfInput = userCommand.length;
    }

    public static LocalDate stringToLocalDate(String date) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return LocalDate.parse(date, formatter);
    }

    /**
     * Public boolean checkIfFloatingTask() throws Exception { DateTimeFormatter
     * formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); try {
     * LocalDate.parse(userCommand[lengthOfInput - 1], formatter); } catch
     * (Exception e) { return false; } return true; }
     **/

    public LocalDate getStartDate(String userInput) {
        setAttributes(userInput);
        return startDate;
    }

    public LocalDate getEndDate(String userInput) {
        setAttributes(userInput);
        // System.out.println(endDate);
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

    /** Methods for updates *. */

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

    public String setTaskNameForUpdates() {
        String output = "";
        int taskNameIndex = lengthOfInput - 2;

        for (int i = 2; i < taskNameIndex; i++) {
            output += userCommand[i] + " ";
        }
        return output.trim();
    }

}