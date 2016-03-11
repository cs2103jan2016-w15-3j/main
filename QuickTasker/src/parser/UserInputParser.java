package parser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * .
 * 
 * @author A0121558H
 *
 */
public class UserInputParser implements ParserInterface {

    private String taskName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String command;
    private String[] userCommand;
    private int lengthOfInput;

    public UserInputParser() {
        this.taskName = "";
        this.startDate = LocalDate.MIN;
        this.endDate = LocalDate.MAX;
        this.command = "";
    }

    /**
     * Constructor for Parser object.
     * 
     * @throws Exception
     **/
    /**
     * Public UserInputParser(String userInput) throws Exception {
     * 
     * } .
     */
    public void setAttributes(String userInput) throws Exception {
        removeWhiteSpaces(userInput);
        determineLengthOfInput();
        command = userCommand[0];
        taskName = setTaskName();
        startDate = setStartDate();
        endDate = setEndDate();
    }

    public void setAttributesForGetCommands(String userInput) throws setAttributeException {
        removeWhiteSpaces(userInput);
        determineLengthOfInput();
        command = userCommand[0];
    }

    public static class setAttributeException extends RuntimeException {
        // the purpose of setting cusom exception is such that you can tell exactly which class,
        // and which method causes the exception, rather than seeing a bunch of red.
        // The purpose of using RuntimeException is because it is the only 'unchcked' exception.
        // A quick google will provide you some insights on how chcked expcetions are failed
        // experiments in various programming languages including Java
    }

    /**
     * Checks if it is a floating task. If yes, returns null.
     * 
     * @return
     * @throws Exception
     */
    public LocalDate setEndDate() throws Exception {
        /*
         * if (!checkIfFloatingTask()) { return stringToLocalDate(userCommand[lengthOfInput - 1]); }
         * else { System.out.println("sould return null"); return null; }
         **/
        return stringToLocalDate(userCommand[lengthOfInput - 1]);
    }

    public LocalDate setStartDate() throws Exception {
        return stringToLocalDate(userCommand[lengthOfInput - 2]);
    }

    public String setTaskName() throws Exception {
        String output = "";
        int taskNameIndex = lengthOfInput - 2;

        /*
         * if (checkIfFloatingTask()) { taskNameIndex--; }
         **/
        System.out.println("index " + taskNameIndex);
        for (int i = 1; i < taskNameIndex; i++) {
            output += userCommand[i] + " ";
            System.out.println("output " + output);
        }
        // output += userCommand[taskNameIndex];
        return output;
    }

    public String[] removeWhiteSpaces(String input) {
        userCommand = input.split("\\s+");
        return userCommand;
    }

    public void determineLengthOfInput() {
        lengthOfInput = userCommand.length;
    }

    public static LocalDate stringToLocalDate(String date) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return LocalDate.parse(date, formatter);
    }

    /**
     * Public boolean checkIfFloatingTask() throws Exception { DateTimeFormatter formatter =
     * DateTimeFormatter.ofPattern("dd-MM-yyyy"); try { LocalDate.parse(userCommand[lengthOfInput -
     * 1], formatter); } catch (Exception e) { return false; } return true; }
     **/

    /**
     * (non-Javadoc)
     * 
     * @see parser.ParserInterface#getStartDate()
     */

    public LocalDate getStartDate(String userInput) throws Exception {
        setAttributes(userInput);
        return startDate;
    }

    /**
     * (non-Javadoc)
     * 
     * @see parser.ParserInterface#getEndDate()
     */
    public LocalDate getEndDate(String userInput) throws Exception {
        setAttributes(userInput);
       // System.out.println(endDate);
        return endDate;
    }

    /**
     * (non-Javadoc)
     * 
     * @see parser.ParserInterface#getCommand()
     */
    public Commands getCommand(String userInput) throws Exception {
        setAttributesForGetCommands(userInput);
        return DetermineCommandType.getCommand(command);
    }

    /**
     * (non-Javadoc)
     * 
     * @see parser.ParserInterface#getTaskName()
     */
    public String getTaskName(String userInput) throws Exception {
        setAttributes(userInput);
        return taskName;
    }
    public int getTaskIndex(String input) {
        String[] splitted = input.split("\\s+");
        return Integer.parseInt(splitted[1]) - 1;
    }
}