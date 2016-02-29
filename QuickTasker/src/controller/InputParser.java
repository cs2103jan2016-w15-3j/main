package controller;

import java.util.Date;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 * author A0121558H
 * 
 * This class takes in an input and processes it.
 * 
 * Outputs: 1) Date 2) Task name
 **/

public class InputParser {

    private static LocalDate currentDate = strToDate(getNowDate());

    /** Messages **/
    private static final String MESSAGE_INVALID = "Format invalid. Please input in "
            + "this format\"add <taskname> <date> <time>\"";
    private static final String MESSAGE_INVALID_DATE = "Date format invalid. Please try again.";

    /**
     * convert a date in String to Date format
     * 
     * String format yyyy-MM-dd
     * 
     * @param str
     * @return Date
     */
    public static LocalDate strToDate(String str) {
        if (str == null)
            return null;

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        ParsePosition pos = new ParsePosition(0);
        Date strToDate = formatter.parse(str, pos);
        LocalDate local= strToDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        return local;
    }

    /**
     * Gets current date
     **/
    public static String getNowDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new java.text.SimpleDateFormat("dd-MM-yyyy");
        String nowDate = formatter.format(currentTime);
        return nowDate;
    }

    /**
     * convert Date to string format
     * 
     * @param date
     * @return String yyyy-MM-dd
     */
    public static String dateToStr(Date date) {
        if (date == null)
            return null;

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String dateString = formatter.format(date);
        return dateString;
    }

   

    /** This method splits the input **/
    private static String[] stringSplitter(String input) {
        String[] output = input.split(" ");

        return output;
    }
    
    private static Commands outputCommand(String input) throws Exception{
        String[] splitted= stringSplitter(input);
        Commands command= DetermineCommandType.getCommand(splitted[0]);
        return command;
    }

    private static String outputDate(String input) throws Exception {
        String[] splitted = stringSplitter(input);
        return parseDate(splitted[2]);
    }

    private static String outputTaskName(String input) {
        String[] splitted = stringSplitter(input);
        return parseTaskName(splitted);
    }

 /**   private static String outputTime(String input) {
        String[] splitted = stringSplitter(input);
        return parseTime(splitted[3]);
    }
**/
    private static String parseTaskName(String[] input) {
        String output = "";

        for (int i = 1; i < input.length - 1; i++) {
            output += input[i];
        }
        return output;
    }
    
    /**
     * Check input date. Format: DD-MM-YYYY
     * 
     * @param input
     * @return
     */
    private static String parseDate(String input) throws Exception {
//need to return LocalDate type
        try {
            String[] splittedInput = input.split("-");
            if (checkSplittedInput(splittedInput)) {
                return input;
            } else {
                return MESSAGE_INVALID_DATE;
            }
        } catch (Exception e) {
            return MESSAGE_INVALID_DATE;
        }
    }

    private static boolean checkSplittedInput(String[] input) {
        return Integer.parseInt(input[0]) > 0 && Integer.parseInt(input[0]) < 32 && Integer.parseInt(input[1]) > 0
                && Integer.parseInt(input[1]) < 13 && input[2].length() < 5;
    }
}
