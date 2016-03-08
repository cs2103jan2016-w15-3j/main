package parser;

public class DetermineCommandType {

	public static Commands getCommand(String input) {
		switch (input) {
		case "add":
			return Commands.CREATE_TASK;
		case "delete":
			return Commands.DELETE_TASK;
		case "update":
			return Commands.UPDATE_TASK;
		case "read":
			return Commands.DISPLAY_TASK;
		default:
			return Commands.ERROR;
		}
	}

}
