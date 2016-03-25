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
 * recur x days/weeks/years must be together anywhere in string
 */
public class RecurringParser {
    
    private String taskName;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String[] userCommand;
    private int lengthOfInput;
    private int numToUse;
    private int numToRecur;
    private String recurDuration;
   
    
    
    /*Constructor*/
    public RecurringParser(String[] input) {
        DateTimeParser dateTimeParser = new DateTimeParser();

        userCommand=input;
        determineLengthOfInput();
        numToRecur= setNumToRecur();
        recurDuration= setRecurDuration();
        userCommand=removeNumAndDuration();
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
    private int indexOfRecur() {
        int num=0;
        
        for( int i=lengthOfInput; i>1; i--) {
            if(userCommand[i-1].equals("recur")) {
                num=i-1;
                break;
            }
        }
        return num;       
    }
    private String[] removeNumAndDuration() {
        int index= indexOfRecur();
        ArrayList<String> tempUserCommand = new ArrayList<String>(Arrays.asList(userCommand));
        tempUserCommand.remove(index);
        tempUserCommand.remove(index);
        tempUserCommand.remove(index);
        return tempUserCommand.toArray(new String[tempUserCommand.size()]);
    }
    public int setNumToRecur() { // here might have error if format wrong
        return Integer.parseInt(userCommand[indexOfRecur()+1]);
        
    }
    public String setRecurDuration() { //similar to above
        return userCommand[indexOfRecur()+2];
        
    }
    public int getNumToRecur() { //for controller
        return numToRecur;
        
    }
    public String getRecurDuration() {//for controller
        return recurDuration;
        
    }
    public String getTaskName() { //for controller
        return taskName;
    } 
    
    public boolean isRecurring(String[] userCommand) {
        boolean check=false;
        
        for (String s: userCommand) {
            if(s.equals("recur")) {
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
        }
    }
    public void setTime(String[] input) {
        DateTimeParser parser = new DateTimeParser();
        ArrayList<Integer> indicesTime = parser.indicesToDetermineTime(input);

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
    public static LocalDate stringToLocalDate(String date) {

        DateTimeParser parser = new DateTimeParser();
        return parser.parseDate(date);
    }
    private boolean isFloating() {

        DateTimeParser parser = new DateTimeParser();

        return (!parser.isDate(userCommand[lengthOfInput - 1]) && !parser.isDate(userCommand[lengthOfInput - 2]));
    }
    //for testing
   /* public static void main(String[] args) {
        String input="add buy dog tomorrow 13:00 15:00 recur 3 days";
        String[] input1=input.split(" ");
        RecurringParser parser= new RecurringParser(input1);
        
        System.out.println("task name:" + parser.taskName);
        System.out.println("parser startdate " + parser.startDate);
        System.out.println("parser enddate " + parser.endDate);
        System.out.println("parser starttime " + parser.startTime);
        System.out.println("parser endtime " + parser.endTime);
        System.out.println("parser recur x= " + parser.numToRecur);
        System.out.println("parser recur duration " + parser.recurDuration);     
    }*/
}
