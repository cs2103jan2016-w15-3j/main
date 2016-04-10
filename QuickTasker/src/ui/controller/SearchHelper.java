package ui.controller;

import model.Task;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;

// @ author A0126077E
class SearchHelper {
    private final String FLOATING_TASK = "floating";
    private final double FUZZY_STRING_COMPARE_THRESHOLD = 0.65;

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
}
