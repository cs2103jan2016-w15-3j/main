package model;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class RecurringTaskTest {

    @Before public void setUp() throws Exception {
        ArrayList<Task> list = new ArrayList<Task>();
        RecurringTask task = new RecurringTask("name", LocalDate.now(), LocalDate.now(), "week",
                LocalTime.now(), LocalTime.now(), 1);
        RecurringTask taskk = new RecurringTask("name", LocalDate.now(), LocalDate.now(), "week",
                LocalTime.now(), LocalTime.now(), 1);
        list.add(task);
        list.add(taskk);
    }

    @Test public void test() {
        ArrayList<Task> list = new ArrayList<Task>();
        RecurringTask task = new RecurringTask("name", LocalDate.now(), LocalDate.now(), "week",
                LocalTime.now(), LocalTime.now(), 1);
        RecurringTask taskk = new RecurringTask("name", LocalDate.now(), LocalDate.now(), "week",
                LocalTime.now(), LocalTime.now(), 1);
        list.add(task);
        list.add(taskk);
        assertEquals("name", list.get(0).getName());
        assertEquals(LocalDate.now(), list.get(1).getStartDate());
        assertEquals(LocalDate.now(), list.get(1).getDueDate());
        RecurringTask taskkk = new RecurringTask("name2", LocalDate.of(2016, 03, 13),
                LocalDate.of(2016, 03, 15), "day", LocalTime.now(), LocalTime.now(), 1);
        taskkk.adjustDate();
        assertEquals(25, taskkk.getStartDate().getDayOfMonth());
        assertEquals(27, taskkk.getDueDate().getDayOfMonth());
        RecurringTask taskkkk = new RecurringTask("name2", LocalDate.of(2016, 03, 13),
                LocalDate.of(2016, 03, 15), "week", LocalTime.now(), LocalTime.now(), 1);
        taskkkk.adjustDate();
        assertEquals(27, taskkkk.getStartDate().getDayOfMonth());
        assertEquals(29, taskkkk.getDueDate().getDayOfMonth());
        RecurringTask taskkkkk = new RecurringTask("name2", LocalDate.of(2016, 03, 25),
                LocalDate.of(2016, 03, 25), "day", LocalTime.now(), LocalTime.now(), 1);
        taskkkkk.adjustDate();
        assertEquals(25, taskkkkk.getStartDate().getDayOfMonth());
        assertEquals(25, taskkkkk.getDueDate().getDayOfMonth());
        RecurringTask taskkkkkk = new RecurringTask("name2", LocalDate.of(2016, 03, 23),
                LocalDate.of(2016, 03, 24), "day", LocalTime.now(), LocalTime.now(), 1);
        taskkkkkk.adjustDate();
        assertEquals(25, taskkkkkk.getStartDate().getDayOfMonth());
        assertEquals(26, taskkkkkk.getDueDate().getDayOfMonth());
        RecurringTask taskkkkkkk = new RecurringTask("name2", LocalDate.of(2016, 03, 23),
                LocalDate.of(2016, 03, 24), "week", LocalTime.now(), LocalTime.now(), 1);
        taskkkkkkk.adjustDate();
        assertEquals(30, taskkkkkkk.getStartDate().getDayOfMonth());
        assertEquals(31, taskkkkkkk.getDueDate().getDayOfMonth());
        RecurringTask taskkkkkkkk = new RecurringTask("name2", LocalDate.of(2016, 03, 25),
                LocalDate.of(2016, 03, 25), "week", LocalTime.now(), LocalTime.now(), 1);
        taskkkkkkkk.adjustDate();
        assertEquals(25, taskkkkkkkk.getStartDate().getDayOfMonth());
        assertEquals(25, taskkkkkkkk.getDueDate().getDayOfMonth());
    }

}
