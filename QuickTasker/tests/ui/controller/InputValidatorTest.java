package ui.controller;
//@@author A0121558H

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Before;
import org.junit.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import logic.Logic;
import model.Task;

public class InputValidatorTest {
	private final Logic operations = new Logic();
	InputValidator validator;
	ObservableList<Task> list;
	Task toTest;

	@Before
	public void setUp() throws Exception {
		validator = new InputValidator();
	}

	@Test
	public void testCheckIfClashSameTimeOverlapDates() {
		list = FXCollections.observableArrayList(operations.addTask(new Task("TEST 1", LocalDate.of(2016, 4, 6),
				LocalDate.of(2016, 4, 25), LocalTime.of(9, 00), LocalTime.of(13, 00))));
		toTest = new Task("TEST 2", LocalDate.of(2016, 4, 4), LocalDate.of(2016, 4, 22), LocalTime.of(9, 00),
				LocalTime.of(13, 00));
		assertEquals(validator.checkIfClash(list, toTest), true);

	}

	@Test
	public void testCheckIfClashOverlapTimeSameDates() {
		list = FXCollections.observableArrayList(operations.addTask(new Task("TEST 1", LocalDate.of(2016, 4, 6),
				LocalDate.of(2016, 4, 6), LocalTime.of(9, 00), LocalTime.of(13, 00))));
		toTest = new Task("TEST 2", LocalDate.of(2016, 4, 6), LocalDate.of(2016, 4, 6), LocalTime.of(8, 00),
				LocalTime.of(12, 00));
		assertEquals(validator.checkIfClash(list, toTest), true);
	}
}
