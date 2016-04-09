package parser;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

//@@author A0121558H

public class RecurringParser extends UserInputParser{

    private int numToRecur;
    private String recurDuration;
	private static Logger loggerRecur = Logger.getLogger("setNumToRecur in RecurringParser");

    private void setAttributesRecurring(String input) {
        System.out.println("---");
        DateTimeParser dateTimeParser = new DateTimeParser();
        System.out.println("===");
        removeWhiteSpaces(input);
        System.out.println("(()");
        determineLengthOfInput();
        System.out.println("&&& " + lengthOfInput);
        numToRecur = setNumToRecur();
        System.out.println("***");
        recurDuration = setRecurDuration();
        System.out.println("!!!");
        userCommand = removeNumAndDuration();
        System.out.println("???");
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
		loggerRecur.log(Level.INFO, "Start of setNumToRecur");

    	try {
            System.out.println("sze = " + userCommand.length);
            System.out.println("lenth = " + lengthOfInput);
    		Integer.parseInt(userCommand[lengthOfInput - 2]);
    	}catch(NumberFormatException e) {
			loggerRecur.log(Level.WARNING, "Error in processing number to recur", e);
    	} catch (Exception e) {
            //System.out.println("sze = " + userCommand.length);
            System.out.println("lenth = " + lengthOfInput);
            System.out.println(e);
        }
		loggerRecur.log(Level.INFO, "End of setNumToRecur");
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