package parser;

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
            case "read":
                return Commands.DISPLAY_TASK;
            case "undo":
                return Commands.UNDO;
            default:
                return Commands.ERROR;
        }
    }

}
