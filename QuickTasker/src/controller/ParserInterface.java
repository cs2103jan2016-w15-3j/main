package controller;
// specific methods required parsing input

import java.time.LocalDate;

public interface ParserInterface {
    // in later stage return type code change to 'Command' to implement undo feature
    // retrieve command from a command string
    public String parseCommand(String str);

    // retrieve a date object from a flexible formatted string
    // in later stage could change to JodaDate/PrettyDate for datetime compare
    public LocalDate parseDate(String str);

    // retrieve taskName from string
    public String parseTaskName(String str);

}
