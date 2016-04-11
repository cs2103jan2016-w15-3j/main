package ui.controller;

import model.Task;
import org.apache.commons.lang3.StringUtils;
import parser.DaysInWeek;
import java.time.LocalDate;

// @ author A0126077E
class SearchHelper {
    private final String FLOATING_TASK = "floating";
    private final double FUZZY_STRING_COMPARE_THRESHOLD = 0.65;
    private final DaysInWeek getDays = new DaysInWeek();

    public boolean isItDisplayedInTodayView(Task t) {
        return isFloatingTask(t) || isDueToday(t);
    }

    public boolean isItDisplayedInTomorrowView(Task t) {
        return isDueTomorrow(t);
    }

    // @@author A0133333U
    private boolean isOverdue(Task t) {
        return t.getDueDate().isBefore(LocalDate.now());
    }

    public boolean isTaskOverdue(Task t) {
        return isOverdue(t);
    }

    private boolean isDueTomorrow(Task t) {
        return t.getDueDate().isAfter(LocalDate.now()) && t.getDueDate()
                .isBefore(LocalDate.now().plusDays(1).plusDays(1));
    }

    // @@author A0126077E
    public boolean containsKeyWord(Task t, String keywords) {
        String[] keywWordsArr = keywords.split("\\s+");
        processKeyWords(keywWordsArr);
        return containsFuzzy(keywWordsArr, t.getName());
    }
    // @@author A0126077E

    private void processKeyWords(String[] keywords) {
        for (String s : keywords) {
            s = s.trim();
            s = s.toLowerCase();
        }
    }

    // @@author A0126077E
    private boolean containsFuzzy(String[] keywords, String taskName) {
        for (String s : keywords)
            if (taskName.contains(s) || compareStrings(taskName, s) > FUZZY_STRING_COMPARE_THRESHOLD)
                return true;
        return false;
    }

    // @@author A0126077E
    private double compareStrings(String source, String toBeCompared) {
        return StringUtils.getJaroWinklerDistance(source, toBeCompared);
    }

    // @@author A0126077E
    public boolean isFloatingTask(Task t) {
        return FLOATING_TASK.equals(t.getTaskType());
    }

    // @@author A0126077E
    private boolean isDueToday(Task t) {
        return t.getDueDate().isEqual(LocalDate.now());
    }

    // @@author A0126077E
    boolean isKeywordSearch() {
        return true;
    }

    // @@author A0130949Y
    public boolean isTaskDueOnMonday(Task task) {
        return isDueMonday(task);
    }

    // @@author A0130949Y
    public boolean isTaskDueOnTuesday(Task task) {
        return isDueTuesday(task);
    }

    // @@author A0130949Y
    public boolean isTaskDueOnWednesday(Task task) {
        return isDueWednesday(task);
    }

    // @@author A0130949Y
    public boolean isTaskDueOnThursday(Task task) {
        return isDueThursday(task);
    }

    // @@author A0130949Y
    public boolean isTaskDueOnFriday(Task task) {
        return isDueFriday(task);
    }

    // @@author A0130949Y
    public boolean isTaskDueOnSaturday(Task task) {
        return isDueSaturday(task);
    }

    // @@author A0130949Y
    public boolean isTaskDueOnSunday(Task task) {
        return isDueSunday(task);
    }

    // @@author A0130949Y
    private boolean isDueMonday(Task task) {
        return task.getDueDate().isEqual(getDays.getMonday());
    }

    // @@author A0130949Y
    private boolean isDueTuesday(Task task) {
        return task.getDueDate().isEqual(getDays.getTuesday());
    }

    // @@author A0130949Y
    private boolean isDueWednesday(Task task) {
        return task.getDueDate().isEqual(getDays.getWednesday());
    }

    // @@author A0130949Y
    private boolean isDueThursday(Task task) {
        return task.getDueDate().isEqual(getDays.getThursday());
    }

    // @@author A0130949Y
    private boolean isDueFriday(Task task) {
        return task.getDueDate().isEqual(getDays.getFriday());
    }

    // @@author A0130949Y
    private boolean isDueSaturday(Task task) {
        return task.getDueDate().isEqual(getDays.getSaturday());
    }

    // @@author A0130949Y
    private boolean isDueSunday(Task task) {
        return task.getDueDate().isEqual(getDays.getSunday());
    }
}
