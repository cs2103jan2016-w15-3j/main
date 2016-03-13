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
        LocalDate output;
        
        System.out.println("IN DATETIMEPARSER");
        System.out.println("INPUT IN DATEIMTPARSER: " +input);

        

        if (!checkIfEnglish(input)) {
            try {
                output = LocalDate.parse(input, formatterForDashes);
                return output;
            } catch (Exception e) {
                // Check next one
            }
            try {
                output = LocalDate.parse(input, formatterForSlashes);
            } catch (Exception e) {
               output = null;
               //other part need to handle null LocalDate
            }
        } else {
            // english
            System.out.println("INPUT IN DATEIMTPARSER ELSE: " +input);
            if (input.equals("today")) {
                output = LocalDate.now();
            } else if (input.equals("tomorrow")) {
                output = LocalDate.now().plusDays(1);
            } else if (input.equals("next day")) {
                System.out.println("IN DATETIMEPARSER CHECK NEXTDAY");
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
