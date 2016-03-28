package logic;

import data.SettingManager;
import model.RecurringTask;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class AddRecurTaskTest {
    Logic logic;
    SettingManager settings;

<<<<<<< HEAD:QuickTasker/src/logic/AddRecurTaskTest.java
    @Before
    public void setUp() throws Exception {
=======
    @Before public void setUp() throws Exception {
>>>>>>> jialin3:QuickTasker/tests/logic/AddRecurTaskTest.java
        settings = new SettingManager();
        settings.setPathOfSaveFile("test.json");
        logic = new Logic();
        logic.addTask(
                new RecurringTask("name", LocalDate.of(2016, 03, 23), LocalDate.of(2016, 03, 24),
                        "week", 1));
        logic.addTask(
                new RecurringTask("name", LocalDate.of(2016, 03, 23), LocalDate.of(2016, 03, 24),
<<<<<<< HEAD:QuickTasker/src/logic/AddRecurTaskTest.java
                        "day", 1));
    }

    @After
    public void tearDown() {
=======
                        "day"));
        settings = new SettingManager();

    }

    @After public void tearDown() {
>>>>>>> jialin3:QuickTasker/tests/logic/AddRecurTaskTest.java
        logic.clear();
        settings.resetDefaultSettings();
    }

    @Test public void testNameOfRecurringTask() {
        assertEquals(logic.getTasks().get(0).getName(), "name");
    }

    @Test public void testStartDateOfRecurringTask() {
        assertEquals(logic.getTasks().get(0).getStartDate(), LocalDate.of(2016, 03, 23));
    }

    @Test public void testEndDateOfRecurringTask() {
        assertEquals(logic.getTasks().get(0).getDueDate(), LocalDate.of(2016, 03, 24));
    }

    @Test public void testDueDateOfRecurringTaskAfterAdjustingWeek() {
        RecurringTask recurring = null;
        assertEquals(logic.getTasks().get(0).getDueDate(), LocalDate.of(2016, 03, 24));
        if (logic.getTasks().get(0) instanceof RecurringTask) {
            recurring = (RecurringTask) logic.getTasks().get(0);
        }
        recurring.adjustDate();
        assertEquals(logic.getTasks().get(0).getDueDate(), LocalDate.of(2016, 03, 31));
    }

    @Test public void testStartDateOfRecurringTaskAfterAdjustingWeek() {
        RecurringTask recurring = null;
        assertEquals(logic.getTasks().get(0).getStartDate(), LocalDate.of(2016, 03, 23));
        if (logic.getTasks().get(0) instanceof RecurringTask) {
            recurring = (RecurringTask) logic.getTasks().get(0);
        }
        recurring.adjustDate();
        assertEquals(logic.getTasks().get(0).getStartDate(), LocalDate.of(2016, 03, 30));
    }

    @Test public void testDueDateOfRecurringTaskAfterAdjustingDay() {
        RecurringTask recurring = null;
        assertEquals(logic.getTasks().get(1).getDueDate(), LocalDate.of(2016, 03, 24));
        if (logic.getTasks().get(1) instanceof RecurringTask) {
            recurring = (RecurringTask) logic.getTasks().get(1);
        }
        recurring.adjustDate();
        assertEquals(logic.getTasks().get(1).getDueDate(), LocalDate.of(2016, 03, 26));
    }

    @Test public void testStartDateOfRecurringTaskAfterAdjustingDay() {
        RecurringTask recurring = null;
        assertEquals(logic.getTasks().get(1).getStartDate(), LocalDate.of(2016, 03, 23));
        if (logic.getTasks().get(1) instanceof RecurringTask) {
            recurring = (RecurringTask) logic.getTasks().get(1);
        }
        recurring.adjustDate();
        assertEquals(logic.getTasks().get(1).getStartDate(), LocalDate.of(2016, 03, 25));
    }

}
