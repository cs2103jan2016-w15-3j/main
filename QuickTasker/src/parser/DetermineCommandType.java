package parser;

//@@author A0121558H

public class DetermineCommandType {

    public static Commands getCommand(String input) {
        switch (input.toLowerCase()) {
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
            case "sort":
                return Commands.SORT_TASK;
            case "search":
                return Commands.SEARCH_TASK;
            case "find":
                return Commands.SEARCH_TASK;
            case "show":
                return Commands.DISPLAY_TASK;
            case "view":
                return Commands.DISPLAY_TASK;
            case "back":
                return Commands.DISPLAY_TASK;
            case "mark":
                return Commands.MARK_TASK;
            case "undo":
                return Commands.UNDO_TASK;
            case "recur":
                return Commands.RECUR_TASK;
            case "exit":
                return Commands.EXIT;
            case "redo":
                return Commands.REDO_TASK;
            case "change":
                return Commands.CHANGE_DIRECTORY;
            case "switch":
                return Commands.CHANGE_DIRECTORY;
            case "help":
                return Commands.HELP;
            case "theme":
                return Commands.THEME;
            case "skip":
                return Commands.SKIP_TASK;
            case "stop":
                return Commands.STOP_TASK;
            case "clear":
                return Commands.CLEAR_TASK;
            default:
                return Commands.ERROR;
        }
    }

}
