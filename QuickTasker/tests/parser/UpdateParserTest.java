package parser;
//@@author A0121558H

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;

public class UpdateParserTest {
    UpdateParser parser;
    LocalDate date;
    LocalTime time;
    String stringToTest = "update 1 buy groceries 121212 121213 09:00 10:00";

    @Before
    public void setUp() throws Exception {
        parser = new UpdateParser();
    }

    @Test
    public void testGetStartDate() {

        assertEquals(LocalDate.of(2012, 12, 12), parser.getStartDate(stringToTest));
    }

    @Test
    public void testGetEndDate() {
        assertEquals(LocalDate.of(2013, 12, 12), parser.getEndDate(stringToTest));
    }

    @Test
    public void testGetStartTime() {
        assertEquals(LocalTime.of(9, 00), parser.getStartTime(stringToTest));
    }

    @Test
    public void testGetEndTime() {
        assertEquals(LocalTime.of(10, 00), parser.getEndTime(stringToTest));
    }

    @Test
    public void testGetTaskName() {
        assertEquals("buy groceries", parser.getTaskName(stringToTest));
    }

    @Test
    public void testGetIndex() {
        parser.setAttributesForUpdates(stringToTest);
        assertEquals(1, parser.getIndexForUpdates(stringToTest));
    }

}
