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
public class RecurringParser {

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

    public String[] removeWhiteSpaces(String input) {

        try {
            userCommand = input.split("\\s+");
        } catch (Exception e) {// change into logger
            System.out.println("Error in splitting");
            System.out.println("Please enter again");
        }
        return userCommand;
    }

    private String[] removeNumAndDuration() {
        ArrayList<String> tempUserCommand = new ArrayList<String>(Arrays.asList(userCommand));
        tempUserCommand.remove(lengthOfInput - 1);
        tempUserCommand.remove(lengthOfInput - 2);
        return tempUserCommand.toArray(new String[tempUserCommand.size()]);
    }

    public int setNumToRecur() { // here might have error if format wrong
        return Integer.parseInt(userCommand[lengthOfInput - 2]);
    }

    public String setRecurDuration() { // similar to above
        return userCommand[lengthOfInput - 1];
    }

    public boolean isRecurring(String[] userCommand) {
        boolean check = false;

        for (String s : userCommand) {
            if (s.equals("recur")) {
                check = true;
            }
        }
        return check;
    }

    public void determineLengthOfInput() {
        lengthOfInput = userCommand.length;
    }

    public void setTaskName() {
        // UPDATED AS OF 23/3/2016
        String output = "";

        for (int i = 1; i < lengthOfInput; i++) {
            output += userCommand[i] + " ";
        }
        output = output.trim();
        taskName = output;
    }

    public void setDate(int numToSetDate, int length) {
        if (numToSetDate == 0) {
            startDate = stringToLocalDate(userCommand[length - 2]);
            endDate = stringToLocalDate(userCommand[length - 1]);
        } else if (numToSetDate == 1) {
            startDate = endDate = stringToLocalDate("tomorrow");
        } else if (numToSetDate == 2) {
            startDate = endDate = stringToLocalDate(userCommand[length - 2] + " " + userCommand[length - 1]);
        } else if (numToSetDate == 3) {// floating task
            startDate = LocalDate.MIN;
            endDate = LocalDate.MIN;// placeholder for null
        } else if (numToSetDate == 4) {
            startDate = endDate = stringToLocalDate("today");
        } else if (numToSetDate == 5) {
            startDate = stringToLocalDate(userCommand[length - 1]);
            endDate = LocalDate.MIN;
        }
    }

    public void setTime(String[] input) {
        DateTimeParser parser = new DateTimeParser();
        ArrayList<Integer> indicesTime = parser.indicesToDetermineTime(input);

        if (indicesTime.size() == 0) {
            startTime = null;
            endTime = null;
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
            numToUse = 1;
        } else if (toCheck.equals("next day") || toCheck.equals("day after")) {
            numToUse = 2;
        } else if (isFloating()) {
            numToUse = 3;
        } else if (userCommand[lengthOfInput - 1].equals("today")) {
            numToUse = 4;
        } else if (parser.isDate(userCommand[lengthOfInput - 1]) && !parser
                .isDate(userCommand[lengthOfInput - 2])) {
            numToUse = 5;
        }
    }

    public static LocalDate stringToLocalDate(String date) {
        DateTimeParser parser = new DateTimeParser();
        return parser.parseDate(date);
    }

    private boolean isFloating() {
        DateTimeParser parser = new DateTimeParser();
        return (!parser.isDate(userCommand[lengthOfInput - 1]) && !parser
                .isDate(userCommand[lengthOfInput - 2]));
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
    // for testing

    public static void main(String[] args) {
        String input = "add buy dog tomorrow 13:00 15:00 recur 3 days";
        RecurringParser parser = new RecurringParser();

        System.out.println("task name:" + parser.getTaskName(input));
        System.out.println("parser startdate " + parser.getTaskStartDate(input));
        System.out.println("parser enddate " + parser.getTaskEndDate(input));
        System.out.println("parser starttime " + parser.getTaskStartTime(input));
        System.out.println("parser endtime " + parser.getTaskEndTime(input));
        System.out.println("parser recur x= " + parser.getNumToRecur(input));
        System.out.println("parser recur duration " + parser.getRecurDuration(input));
    }
}