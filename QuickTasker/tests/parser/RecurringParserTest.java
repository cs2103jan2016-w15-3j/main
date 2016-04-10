package parser;
//@@author A0121558H

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;

public class RecurringParserTest {
    RecurringParser parser;
    LocalDate startDate;
    LocalDate endDate;
    LocalTime startTime;
    LocalTime endTime;
    int numToRecur;
    String recurDuration;
    String userCommandTomorrow = "recur buy dog tomorrow 13:00 15:00 3 days";
    String userCommandDayAfter = "recur buy dog day after 13:00 15:00 3 days";
    String userCommandNormal = "recur buy dog 121212 131212 13:00 15:00 3 days";
    String userCommandDatesOnly = "recur buy dog next day 3 days";

    @Test
    public void testDatesOnly() {
        parser = new RecurringParser();
        assertEquals(3, parser.getNumToRecur(userCommandDatesOnly));
        assertEquals("days", parser.getRecurDuration(userCommandDatesOnly));
        assertEquals("buy dog", parser.getTaskName(userCommandDatesOnly));
        assertEquals(LocalDate.now().plusDays(1), parser.getTaskStartDate(userCommandDatesOnly));
        assertEquals(LocalDate.now().plusDays(1), parser.getTaskEndDate(userCommandDatesOnly));
        assertEquals(LocalTime.MIN, parser.getTaskStartTime(userCommandDatesOnly));
        assertEquals(LocalTime.MAX, parser.getTaskEndTime(userCommandDatesOnly));

    }

    @Test
    public void testTomorrow() {
        parser = new RecurringParser();
        assertEquals(3, parser.getNumToRecur(userCommandTomorrow));
        assertEquals("days", parser.getRecurDuration(userCommandTomorrow));
        assertEquals("buy dog", parser.getTaskName(userCommandTomorrow));
        assertEquals(LocalDate.now().plusDays(1), parser.getTaskStartDate(userCommandTomorrow));
        assertEquals(LocalDate.now().plusDays(1), parser.getTaskEndDate(userCommandTomorrow));
        assertEquals(LocalTime.of(13, 00), parser.getTaskStartTime(userCommandTomorrow));
        assertEquals(LocalTime.of(15, 00), parser.getTaskEndTime(userCommandTomorrow));

    }

    @Test
    public void testDayAfter() {
        parser = new RecurringParser();
        assertEquals(3, parser.getNumToRecur(userCommandDayAfter));
        assertEquals("days", parser.getRecurDuration(userCommandDayAfter));
        assertEquals("buy dog", parser.getTaskName(userCommandDayAfter));
        assertEquals(LocalDate.now().plusDays(2), parser.getTaskStartDate(userCommandDayAfter));
        assertEquals(LocalDate.now().plusDays(2), parser.getTaskEndDate(userCommandDayAfter));
        assertEquals(LocalTime.of(13, 00), parser.getTaskStartTime(userCommandDayAfter));
        assertEquals(LocalTime.of(15, 00), parser.getTaskEndTime(userCommandDayAfter));

    }

    @Test
    public void testGetNumToRecur() {
        parser = new RecurringParser();
        assertEquals(3, parser.getNumToRecur(userCommandNormal));
    }

    @Test
    public void testGetRecurDuration() {
        parser = new RecurringParser();
        assertEquals("days", parser.getRecurDuration(userCommandNormal));
    }

    @Test
    public void testGetTaskName() {
        parser = new RecurringParser();
        assertEquals("buy dog", parser.getTaskName(userCommandNormal));
    }

    @Test
    public void testGetTaskStartDate() {
        parser = new RecurringParser();
        assertEquals(LocalDate.of(2012, 12, 12), parser.getTaskStartDate(userCommandNormal));
    }

    @Test
    public void testGetTaskEndDate() {
        parser = new RecurringParser();
        assertEquals(LocalDate.of(2012, 12, 13), parser.getTaskEndDate(userCommandNormal));
    }

    @Test
    public void testGetTaskStartTime() {
        parser = new RecurringParser();
        assertEquals(LocalTime.of(13, 00), parser.getTaskStartTime(userCommandNormal));
    }

    @Test
    public void testGetTaskEndTime() {
        parser = new RecurringParser();
        assertEquals(LocalTime.of(15, 00), parser.getTaskEndTime(userCommandNormal));
    }
}
