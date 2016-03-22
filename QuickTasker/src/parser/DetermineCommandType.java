package parser;

/**
 * @author A0121558H
 *         Dawson
 */

public class DetermineCommandType {

    public static Commands getCommand(String input) {

        switch (input) {
            case "add":
                return Commands.CREATE_TASK;
            case "del":
                return Commands.DELETE_TASK;
            case "remove":
                return Commands.DELETE_TASK;
            case "rm":
                return Commands.DELETE_TASK;
            case "delete":
                return Commands.DELETE_TASK;
            case "update":
                return Commands.UPDATE_TASK;
            case "change":
                return Commands.UPDATE_TASK;
            case "sort":
                return Commands.SORT_TASK;
            case "search":
                return Commands.SEARCH_TASK;
            case "find":
                return Commands.SEARCH_TASK;
            case "read":
                return Commands.DISPLAY_TASK;
            case "undo":
                return Commands.UNDO_TASK;
            case "exit":
                return Commands.EXIT;
            case "c":
                return Commands.COMPLETE_TASK;
            case "redo":
                return Commands.REDO;
            default:
                return Commands.ERROR;
        }
    }

}
