package controller;
// specific methods required parsing input

import java.time.LocalDate;

public interface ParserInterface {
    /**
     * In later stage return type code change to 'Commands' to implement undo feature
     * retrieve command from a command string.
     */
    static Commands parseCommand(String str) throws Exception {
        return InputParser.outputCommand(str);
    }

    /**
     * Retrieve a date object from a flexible formatted string
     * in later stage could change to JodaDate/PrettyDate for datetime compare.
     */
    static LocalDate parseDate(String str) throws Exception {
        String date= InputParser.outputDate(str);
        return InputParser.strToDate(date);
    }

    /** Retrieve taskName from string. */
    static String parseTaskName(String str){
    
        return InputParser.outputTaskName(str);
    }
}