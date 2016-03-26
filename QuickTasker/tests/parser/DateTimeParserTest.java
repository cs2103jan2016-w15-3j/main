package parser;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class DateTimeParserTest {
    DateTimeParser parser;
    LocalDate date;
    LocalTime time;
    String[] timeList = { "12:12" };
    ArrayList<Integer> indices = new ArrayList<Integer>() {
        {
            add(0);
        }
    };

    @Before
    public void setUp() throws Exception {
        parser = new DateTimeParser();
    }

    @Test
    public void testParseDate() {
        date = LocalDate.of(2012, 12, 12);
        String input = "121212";
        assertEquals(date, parser.parseDate(input));
    }

    @Test
    public void testParseTime() {
        time = LocalTime.of(12, 12);
        ArrayList<LocalTime> toCheck = parser.parseTime(timeList, indices);

        assertEquals(time, toCheck.get(0));

    }

}
