package ui.controller;

//@@author A0121558H
import common.UIOperationException;
import common.InvalidStringException;
import parser.Commands;

public class UiOperationDelegator {
	private final MainWindowController mainWindowController;

	public UiOperationDelegator(MainWindowController mainWindowController) {
		this.mainWindowController = mainWindowController;
	}

	public void performOperations(String userInput) throws UIOperationException ,InvalidStringException {
		InputValidator inputValidator = new InputValidator();
		if (!inputValidator.checkAllValid(userInput)) {
			MainWindowController.logger.severe("Invalid input");
			throw new InvalidStringException();
		} else {
			try {
				if (mainWindowController.getParser().getCommand(userInput) == Commands.CREATE_TASK)
					mainWindowController.addTask(userInput);
				else if (mainWindowController.getParser().getCommand(userInput) == Commands.DELETE_TASK)
					mainWindowController.deleteTask(userInput);
				else if (mainWindowController.getParser().getCommand(userInput) == Commands.UPDATE_TASK)
					mainWindowController.updateTask(userInput);
				else if (mainWindowController.getParser().getCommand(userInput) == Commands.UNDO_TASK)
					mainWindowController.undoTask();
				else if (mainWindowController.getParser().getCommand(userInput) == Commands.REDO_TASK)
					mainWindowController.redoTask();
				else if (mainWindowController.getParser().getCommand(userInput) == Commands.EXIT)
					mainWindowController.getOperations().exit();
				else if (mainWindowController.getParser().getCommand(userInput) == Commands.MARK_TASK)
					mainWindowController.markTaskCompleted(userInput);
				else if (mainWindowController.getParser().getCommand(userInput) == Commands.RECUR_TASK)
					mainWindowController.addRecurringTask(userInput);
				else if (mainWindowController.getParser().getCommand(userInput) == Commands.SKIP_TASK)
					mainWindowController.skipRecurringTask(userInput);
				else if (mainWindowController.getParser().getCommand(userInput) == Commands.CLEAR_TASK)
					mainWindowController.clearTasks();
				else if (mainWindowController.getParser().getCommand(userInput) == Commands.STOP_TASK)
					mainWindowController.stopRecurringTask(userInput);
				else if (mainWindowController.getParser().getCommand(userInput) == Commands.SEARCH_TASK)
					mainWindowController.searchTask(userInput);
				else if (mainWindowController.getParser().getCommand(userInput) == Commands.CHANGE_DIRECTORY)
					mainWindowController.changeDirectory(userInput);
				else if (mainWindowController.getParser().getCommand(userInput) == Commands.BACK)
					mainWindowController.showAll();
				else if ("show today".equals(userInput) || "view today".equals(userInput))
					mainWindowController.showToday();
				else if ("show tomorrow".equals(userInput) || "view tomorrow".equals(userInput))
					mainWindowController.showTomorrow();
				else if ("show all".equals(userInput) || "view all".equals(userInput))
					mainWindowController.showAll();
				else if ("view archived".equals(userInput))
					mainWindowController.viewArchived();
				else if ("view floating".equals(userInput) || "show floating".equals(userInput))
					mainWindowController.showFloating();
				else if ("view monday".equals(userInput) || "show monday".equals(userInput))
					mainWindowController.showMonday();
				else if ("view tuesday".equals(userInput) || "show tuesday".equals(userInput))
					mainWindowController.showTuesday();
				else if ("view wednesday".equals(userInput) || "show wednesday".equals(userInput))
					mainWindowController.showWednesday();
				else if ("view thursday".equals(userInput) || "show thursday".equals(userInput))
					mainWindowController.showThursday();
				else if ("view friday".equals(userInput) || "show friday".equals(userInput))
					mainWindowController.showFriday();
				else if ("view saturday".equals(userInput) || "show saturday".equals(userInput))
					mainWindowController.showSaturday();
				else if ("view sunday".equals(userInput) || "show sunday".equals(userInput))
					mainWindowController.showSunday();
				else if (userInput.contains("theme"))
					mainWindowController.changeTheme(userInput);
				else if (userInput.equalsIgnoreCase("show overdue"))
					mainWindowController.showOverdue();
				else if ("help".equals(userInput))
					mainWindowController.showHelp();
			} catch (Exception e) {
				throw new UIOperationException();
			}
		}
	}
}
