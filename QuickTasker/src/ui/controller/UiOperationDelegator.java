package ui.controller;

//@@author A0121558H

import common.InvalidStringException;
import common.UIOperationException;
import parser.Commands;

public class UiOperationDelegator {
    private final MainWindowController mainWindowController;

    public UiOperationDelegator(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

    public void performOperations(String userInput) throws UIOperationException, InvalidStringException {
        InputValidator inputValidator = new InputValidator();
        if (!inputValidator.checkAllValid(userInput)) {
            MainWindowController.logger.severe(inputValidator.toString());
            System.out.println("throw??");
            throw new InvalidStringException();//TODO
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
                else if (mainWindowController.getParser().getCommand(userInput) == Commands.DISPLAY_TASK)
                    mainWindowController.showTasks(userInput);
                else if (mainWindowController.getParser().getCommand(userInput) == Commands.THEME)
                    mainWindowController.changeTheme(userInput);
                else if (mainWindowController.getParser().getCommand(userInput) == Commands.HELP)
                    mainWindowController.showHelp();
            } catch (Exception e) {
                throw new UIOperationException();
            }
        }
    }
}