package logic;

import model.RecurringTask;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class SkipRecurTaskTest {
    Logic logic;

    @Before
    public void setUp() throws Exception {
        logic = new Logic();
        logic.clear();
        createRecurringTasks(3);
    }

    private void createRecurringTasks(int n) {
        for (int i = 0; i < n; i++) {
            logic.addTask(new RecurringTask("Recurring " + i, LocalDate.now().plusDays(i),
                    LocalDate.now().plusDays(i), "weeks", null, null, 2));
        }
    }

    @Test
    public void testSizeOfListAfterAdd() throws Exception {
        assertEquals(logic.getSize(), 3);
    }

    @Test
    public void testSkip() {
        logic.skip(0);
        assertEquals("Recurring 1", logic.getTasks().get(0).getName());
        assertEquals(LocalDate.now().plusWeeks(2), logic.getTasks().get(2).getStartDate());
    }

    @Test
    public void testSkipNegativeNumber() {
        boolean testNegative = false;
        try {
            logic.skip(-1);
        } catch (IndexOutOfBoundsException e) {
            testNegative = true;
        }
        assert (testNegative);
    }

    @Test
    public void testSkipOverBound() {
        boolean testOutOfBound = false;
        try {
            logic.skip(5);
        } catch (IndexOutOfBoundsException e) {
            testOutOfBound = true;
        }
        assert (testOutOfBound);
    }

    @Test
    public void testSkipNonNumberIndex() {
        boolean testNonNumberIndex = false;
        try {
            logic.skip(Integer.parseInt("hi"));
        } catch (NumberFormatException e) {
            testNonNumberIndex = true;
        }
        assert (testNonNumberIndex);
    }
}
