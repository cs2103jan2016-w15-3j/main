package controller;
// specific methods required parsing input

import java.time.LocalDate;

public interface ParserInterface {
    // in later stage return type code change to 'Commands' to implement undo feature
    // retrieve command from a command string
    public static Commands parseCommand(String str) throws Exception {
        return InputParser.outputCommand(str);
    }

    // retrieve a date object from a flexible formatted string
    // in later stage could change to JodaDate/PrettyDate for datetime compare
    public static LocalDate parseDate(String str) throws Exception {
        String date= InputParser.outputDate(str);
        return InputParser.strToDate(date);
    }

    // retrieve taskName from string
    public static String parseTaskName(String str){
    
        return InputParser.outputTaskName(str);
    }
}