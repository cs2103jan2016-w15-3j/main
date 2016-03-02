package parser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 
 * @author A0121558H
 *
 */
public class UserInputParser {

    private static String taskName;
    private static LocalDate startDate;
    private static LocalDate endDate;
    private static String command;
    private static String[] userCommand;
    private static int lengthOfInput;

    /** Constructor for Parser object 
     * @throws Exception **/
    public UserInputParser(String userInput) throws Exception {
        removeWhiteSpaces(userInput);
        determineLengthOfInput();
        command = userCommand[0];
        taskName = setTaskName();
        startDate = setStartDate();
        endDate = setEndDate();
    }
/**
 * Checks if it is a floating task. If yes, returns null.
 * @return
 * @throws Exception
 */
    public static LocalDate setEndDate() throws Exception {
        if (!checkIfFloatingTask()) {
            return stringToLocalDate(userCommand[lengthOfInput - 1]);
        } else {
            return null;
        }
    }

    public static LocalDate setStartDate() throws Exception {
        if (checkIfFloatingTask()) {
            return stringToLocalDate(userCommand[lengthOfInput - 1]);
        } else {
            return stringToLocalDate(userCommand[lengthOfInput - 2]);
        }
    }

    public static String setTaskName() throws Exception {
        String output = "";
        int taskNameIndex = lengthOfInput - 3;

        if (checkIfFloatingTask()) {
            taskNameIndex--;
        }

        for (int i = 1; i < taskNameIndex; i++) {
            output += userCommand[i];
        }
        return output;
    }

    public static String[] removeWhiteSpaces(String input) {
        userCommand = input.split(" ");
        return userCommand;
    }

    public static void determineLengthOfInput() {
        lengthOfInput = userCommand.length;
    }

    public static LocalDate stringToLocalDate(String date) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate output = LocalDate.parse(date, formatter);
        return output;
    }

    public static boolean checkIfFloatingTask() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        try {
            LocalDate.parse(userCommand[lengthOfInput - 1], formatter);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /** To retrieve attributes **/

    public  LocalDate getStartDate() {
        return startDate;
    }

    public  LocalDate getEndDate() {
        return endDate;
    }

    public  String getCommand() {
        return command;
    }

    public  String getTaskName() {
        return taskName;
    }

}
