package parser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.ocpsoft.prettytime.nlp.PrettyTimeParser;
import org.ocpsoft.prettytime.shade.org.antlr.runtime.ParserRuleReturnScope;

import jdk.management.resource.internal.inst.SocketDispatcherRMHooks;

import java.util.Date;
import java.util.List;

import javax.print.attribute.ResolutionSyntax;

//import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

/**
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
        System.out.println("parser" + taskName);
        startDate = setStartDate();
        System.out.println("parser startdate " + startDate);
        endDate = setEndDate();
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

    public String setTaskName() {
        String output = "";
        int taskNameIndex = lengthOfInput - 2;

        System.out.println("index " + taskNameIndex);
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

        // DateTimeFormatter formatter =
        // DateTimeFormatter.ofPattern("dd-MM-yyyy");
        // return LocalDate.parse(date, formatter);

        DateTimeParser parser = new DateTimeParser();
        return parser.parseDate(date);
    }

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
        setDate(checkIfEnglishDate());
    }

    private void setDate(int numToSetDate) {
        if (numToSetDate == 0) {
            startDate = stringToLocalDate(userCommand[lengthOfInput - 2]);
            endDate = stringToLocalDate(userCommand[lengthOfInput - 1]);
        } else if (numToSetDate == 1) {
            startDate = stringToLocalDate("today");
            endDate = stringToLocalDate("tomorrow");
        } else {
            startDate = stringToLocalDate("today");
            endDate = stringToLocalDate(userCommand[lengthOfInput - 2] + " " + userCommand[lengthOfInput - 1]);
        }
    }

    private int checkIfEnglishDate() {
        // 1 is tmr
        // 2 is either next day or day after
        // 0 is not english
        String toCheck = userCommand[lengthOfInput - 1] + " " + userCommand[lengthOfInput - 2];
        int numToUse = 0;

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