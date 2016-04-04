package parser;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

/*
 * author A0121558H
 * 
 * normal task to recurring task
 * addition of recurring task
 */
public class RecurringParser extends UserInputParser{

    private String[] userCommand;
    private String taskName;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private int lengthOfInput;
    private int numToUse;
    private int numToRecur;
    private String recurDuration;

    private void setAttributesRecurring(String input) {
        DateTimeParser dateTimeParser = new DateTimeParser();
        removeWhiteSpaces(input);
        determineLengthOfInput();
        numToRecur = setNumToRecur();
        recurDuration = setRecurDuration();
        userCommand = removeNumAndDuration();
        determineLengthOfInput();
        setTime(userCommand);
        userCommand = dateTimeParser.removeTime(userCommand);
        determineLengthOfInput();
        isEnglishDate();
        setDate(numToUse, lengthOfInput);
        userCommand = dateTimeParser.removeDate(userCommand);
        determineLengthOfInput();
        setTaskName();
    }
    private String[] removeNumAndDuration() {
        ArrayList<String> tempUserCommand = new ArrayList<String>(Arrays.asList(userCommand));
        tempUserCommand.remove(lengthOfInput - 1);
        tempUserCommand.remove(lengthOfInput - 2);
        return tempUserCommand.toArray(new String[tempUserCommand.size()]);
    }

    public int setNumToRecur() {
        return Integer.parseInt(userCommand[lengthOfInput - 2]);
    }

    public String setRecurDuration() { 
        return userCommand[lengthOfInput - 1];
    }

    public static LocalDate stringToLocalDate(String date) {
        DateTimeParser parser = new DateTimeParser();
        return parser.parseDate(date);
    }

    public int getNumToRecur(String userInput) { // for controller
        setAttributesRecurring(userInput);
        return numToRecur;

    }

    public String getRecurDuration(String userInput) {// for controller
        setAttributesRecurring(userInput);
        return recurDuration;

    }

    public String getTaskName(String userInput) { // for controller
        setAttributesRecurring(userInput);
        return taskName;
    }

    public LocalDate getTaskStartDate(String userInput) {
        setAttributesRecurring(userInput);
        return startDate;
    }

    public LocalDate getTaskEndDate(String userInput) {
        setAttributesRecurring(userInput);
        return endDate;
    }

    public LocalTime getTaskStartTime(String userInput) {
        setAttributesRecurring(userInput);
        return startTime;
    }

    public LocalTime getTaskEndTime(String userInput) {
        setAttributesRecurring(userInput);
        return endTime;
    }
}