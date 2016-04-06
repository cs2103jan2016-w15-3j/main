package ui.controller;

import model.Task;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;

public class SearchHelper {
    private final String FLOATING_TASK = "floating";
    private final double FUZZY_STRING_COMPARE_THRESHOLD = 0.65;

    public boolean isItDisplayedInTodayView(Task task) {
        String taskType = task.getTaskType();
        if (isFloatingTask(task)) return true;
        else if (isDueToday(task)) return true;
        return false;
    }

    public boolean isItDisplayedInTomorrowView(Task task) {
        return isDueTomorrow(task);
    }

    private boolean isDueTomorrow(Task task) {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        LocalDate today = LocalDate.now();
        LocalDate theDayAfterTomorrow = tomorrow.plusDays(1);
        if (task.getDueDate().isAfter(today) && task.getDueDate().isBefore(theDayAfterTomorrow)) return true;
        return false;
    }

    public boolean containsKeyWord(Task task, String keywords) {
        String[] keywWordsArr = keywords.split("\\s+");
        processKeyWords(keywWordsArr);
        String taskName = task.getName();
        return containsFuzzy(keywWordsArr, taskName);
    }

    private void processKeyWords(String[] keywords) {
        for (String s : keywords) {
            s = s.trim();
            s = s.toLowerCase();
        }
    }

    private boolean containsFuzzy(String[] keywords, String taskName) {
        for (String s : keywords) {
            if (taskName.contains(s)) return true;
            if (compareStrings(taskName, s) > FUZZY_STRING_COMPARE_THRESHOLD) return true;
        }
        return false;
    }

    private double compareStrings(String source, String toBeCompared) {
        return StringUtils.getJaroWinklerDistance(source, toBeCompared);
    }

    public boolean isFloatingTask(Task task) {
        return task.getTaskType().equals(FLOATING_TASK);
    }

    private boolean isDueToday(Task task) {
        LocalDate today = LocalDate.now();
        LocalDate dueDate = task.getDueDate();
        return dueDate.isEqual(today);
    }
}
