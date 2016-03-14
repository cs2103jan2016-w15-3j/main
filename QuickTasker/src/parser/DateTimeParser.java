package parser;

import java.time.LocalDate;
//import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Author dawson A0121558H
 * 
 *
 */
public class DateTimeParser {

    public LocalDate parseDate(String input) {

        DateTimeFormatter formatterForDashes = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter formatterForSlashes = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatterEmpty = DateTimeFormatter.ofPattern("ddMMyyyy");
        DateTimeFormatter formatterDashesShortened = DateTimeFormatter.ofPattern("dd-MM-yy");
        DateTimeFormatter formatterSlashesShortened = DateTimeFormatter.ofPattern("dd/MM/yy");
        DateTimeFormatter formatterEmptyShortened = DateTimeFormatter.ofPattern("ddMMyy");

        LocalDate output;

        if (!checkIfEnglish(input)) {
            try {
                output = LocalDate.parse(input, formatterForDashes);
                return output;
            } catch (Exception e) {
                // Check next one
            }
            try {
                output = LocalDate.parse(input, formatterForSlashes);
                return output;
            } catch (Exception e) {
                // output = null;
                // try next
            }
            try {
                output = LocalDate.parse(input, formatterDashesShortened);
                return output;
            } catch (Exception e) {
                // try next
            }
            try {
                output = LocalDate.parse(input, formatterSlashesShortened);
                return output;
            } catch (Exception e) {
                // try next
            }
            try {
                output = LocalDate.parse(input, formatterEmptyShortened);
                return output;
            } catch (Exception e) {
                // try next
            }
            try {
                output = LocalDate.parse(input, formatterEmpty);
                return output;
            } catch (Exception e) {
                output = null;
            }
        } else {
            // english
            if (input.equals("today")) {
                output = LocalDate.now();
            } else if (input.equals("tomorrow")) {
                output = LocalDate.now().plusDays(1);
            } else if (input.equals("next day")) {
                output = LocalDate.now().plusDays(1);
            } else {
                output = LocalDate.now().plusDays(2);
            }
        }
        return output;
    }
    /*
     * public LocalTime parseTime(String input) {
     * 
     * }
     */

    private boolean checkIfEnglish(String input) {
        return (input.equals("today") || input.equals("tomorrow") || input.equals("next day")
                || input.equals("day after"));
    }

}
