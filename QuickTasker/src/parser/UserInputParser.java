public String getTaskName(String userInput) {
        setAttributes(userInput);
        return taskName;
    }

    public int getTaskIndex(String input) {
        String[] splitted = input.split("\\s+");
        return Integer.parseInt(splitted[1]) - 1;
    }

    public Commands getCommand(String userInput) {
        setAttributesForGetCommands(userInput);
        return DetermineCommandType.getCommand(command);
    }

    /** Methods for updates **/

    public int getIndexForUpdate(String userInput) {
        removeWhiteSpaces(userInput);
        return Integer.parseInt(userCommand[1]) - 1;
    }

    public String getTaskNameForUpdate(String userInput) {
        setAttributesForUpdates(userInput);
        return taskName;
    }

    public LocalDate getStartDateForUpdate(String userInput) {
        setAttributesForUpdates(userInput);
        return startDate;
    }

    public LocalDate getEndDateForUpdate(String userInput) {
        setAttributesForUpdates(userInput);
        return endDate;
    }

    private void setAttributesForUpdates(String input) {
        removeWhiteSpaces(input);
        command = userCommand[0];
        determineLengthOfInput();
        taskName = setTaskNameForUpdates();
        startDate = stringToLocalDate(userCommand[lengthOfInput - 2]);
        endDate = stringToLocalDate(userCommand[lengthOfInput - 1]);
    }

    private void setDate(int numToSetDate) {
        if (numToSetDate == 0) {
            startDate = stringToLocalDate(userCommand[lengthOfInput - 2]);
            endDate = stringToLocalDate(userCommand[lengthOfInput - 1]);
        } else if (numToSetDate == 1) {
            startDate = endDate = stringToLocalDate("today");

        } else {
            startDate = endDate = stringToLocalDate(
                    userCommand[lengthOfInput - 2] + " " + userCommand[lengthOfInput - 1]);
        }
    }

    private int isEnglishDate() {
        // 1 is tmr
        // 2 is either next day or day after
        // 0 is not english
        String toCheck = userCommand[lengthOfInput - 2] + " " + userCommand[lengthOfInput - 1];
        numToUse = 0;

        if (userCommand[lengthOfInput - 1].equals("tomorrow")) {
            numToUse = 1;
        } else if (toCheck.equals("next day") || toCheck.equals("day after")) {
            numToUse = 2;
        }
        return numToUse;
    }

    public String setTaskNameForUpdates() {
        String output = "";
        int taskNameIndex = lengthOfInput - 2;

        for (int i = 2; i < taskNameIndex; i++) {
            output += userCommand[i] + " ";
        }
        return output.trim();
    }
}