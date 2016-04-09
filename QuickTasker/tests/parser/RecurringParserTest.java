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
    String userCommand = "recur buy dog tomorrow 13:00 15:00 3 days";

    @Test
    public void testGetNumToRecur() {
        parser = new RecurringParser();
        assertEquals(3, parser.getNumToRecur(userCommand));
    }

    @Test
    public void testGetRecurDuration() {
        parser = new RecurringParser();
        assertEquals("days", parser.getRecurDuration(userCommand));
    }

    @Test
    public void testGetTaskName() {
        parser = new RecurringParser();
        assertEquals("buy dog", parser.getTaskName(userCommand));
    }

    @Test
    public void testGetTaskStartDate() {
        parser = new RecurringParser();
        assertEquals(LocalDate.now().plusDays(1), parser.getTaskStartDate(userCommand));
    }

    @Test
    public void testGetTaskEndDate() {
        parser = new RecurringParser();
        assertEquals(LocalDate.now().plusDays(1), parser.getTaskEndDate(userCommand));
    }

    @Test
    public void testGetTaskStartTime() {
        parser = new RecurringParser();
        assertEquals(LocalTime.of(13, 00), parser.getTaskStartTime(userCommand));
    }

    @Test
    public void testGetTaskEndTime() {
        parser = new RecurringParser();
        assertEquals(LocalTime.of(15, 00), parser.getTaskEndTime(userCommand));
    }
}
