package parser;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

import java.util.Collections;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

//import java.time.LocalTime;

/**
 * @author A0121558H dawson
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

        if (!isEnglish(input)) {
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

    private boolean isEnglish(String input) {
        return (input.equals("today") || input.equals("tomorrow") || input.equals("next day")
                || input.equals("day after"));
    }

    public boolean isLocalDate(String input) {
        DateTimeFormatter formatterForDashes = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter formatterForSlashes = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatterEmpty = DateTimeFormatter.ofPattern("ddMMyyyy");
        DateTimeFormatter formatterDashesShortened = DateTimeFormatter.ofPattern("dd-MM-yy");
        DateTimeFormatter formatterSlashesShortened = DateTimeFormatter.ofPattern("dd/MM/yy");
        DateTimeFormatter formatterEmptyShortened = DateTimeFormatter.ofPattern("ddMMyy");

        try {
            LocalDate.parse(input, formatterForDashes);
            return true;
        } catch (Exception e) {
        }
        try {
            LocalDate.parse(input, formatterForSlashes);
            return true;
        } catch (Exception e) {
        }
        try {
            LocalDate.parse(input, formatterEmpty);
            return true;
        } catch (Exception e) {
        }
        try {
            LocalDate.parse(input, formatterDashesShortened);
            return true;
        } catch (Exception e) {
        }
        try {
            LocalDate.parse(input, formatterSlashesShortened);
            return true;
        } catch (Exception e) {
        }
        try {
            LocalDate.parse(input, formatterEmptyShortened);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isTime(String input) {
        return (input.indexOf(':') >= 0) || (input.indexOf("pm") >= 0) || input.indexOf("am") >= 0;
    }

    public ArrayList<Integer> indicesToDetermineTime(String[] input) {

        ArrayList<Integer> indices = new ArrayList<Integer>();

        for (int i = 0; i < input.length; i++) {
            if (isTime(input[i])) {
                indices.add(i);
            }
        }
        return indices;
    }

    public ArrayList<LocalTime> parseTime(String[] input, ArrayList<Integer> indices) {
        ArrayList<LocalTime> output = new ArrayList<LocalTime>();

        for (int i = 0; i < indices.size(); i++) {
            output.add(toLocalTime(input[indices.get(i)]));
        }
        return output;
    }

    private LocalTime toLocalTime(String input) {
        DateTimeFormatter timeColons = DateTimeFormatter.ofPattern("HH:MM");
        // how to set am pm??? TODO
        return LocalTime.parse(input, timeColons);
    }

    public String[] removeTime(String[] userCommand) {
        // test
      /*  System.out.println("BEFORE REMOVE");
        for (String s : userCommand) {
            System.out.print(s);
        }*/

        ArrayList<Integer> indices = new ArrayList<Integer>();
        indices = indicesToDetermineTime(userCommand);
        int numIndices = indices.size();
        ArrayList<String> tempUserCommand = new ArrayList<String>(Arrays.asList((userCommand)));

        if (numIndices == 0) {
            return userCommand;
        } else if (numIndices == 1) {
            int first= indices.get(0);
           // System.out.println("check indices: " + indices.get(0));
         //   System.out.println("to remove: " + tempUserCommand.get(indices.get(0)));
            tempUserCommand.remove(first);
        } else {
            int first = indices.get(0);
            int second = indices.get(1);

          //  System.out.println("1st: " + first);
         //   System.out.println("2nd: " + second);

            if (first > second) {
                tempUserCommand.remove(first);
                tempUserCommand.remove(second);
            } else {
                tempUserCommand.remove(second);
                tempUserCommand.remove(first);
            }
        }

        // String[] checkcheck= tempUserCommand.toArray(new
        // String[tempUserCommand.size()]);
        // test
      /*  System.out.println("AFTER REMOVE");
        for (String t : tempUserCommand)
            System.out.print(t);*/

        return tempUserCommand.toArray(new String[tempUserCommand.size()]);
    }
}
