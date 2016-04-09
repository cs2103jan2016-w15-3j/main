package parser;
//@@author A0121558H

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;

public class UserInputParserTest {
    UserInputParser parser;
    LocalDate startDate;
    LocalDate endDate;
    LocalTime startTime;
    LocalTime endTime;
    String userCommand = "add biology class tomorrow 13:00 15:00";

    @Before
    public void setUp() throws Exception {
        parser = new UserInputParser();
    }

    @Test
    public void testSetDate() {
        parser.setDate(1, 4);
        assertEquals(LocalDate.now().plusDays(1), parser.getStartDate(userCommand));
    }

    @Test
    public void testSetTime() {
        assertEquals(LocalTime.of(13, 00), parser.getStartTime(userCommand));
        assertEquals(LocalTime.of(15, 00), parser.getEndTime(userCommand));
    }

}
