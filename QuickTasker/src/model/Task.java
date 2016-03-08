package model;

import java.time.LocalDate;

/**
 *. 
 * @author A0121558H/A0130949
 *
 */
public class Task {
	private String taskName;
	private LocalDate endDate;
	private LocalDate startDate;

	public void setStartDateAsNow() {
		startDate = LocalDate.now();
	}

	/** Default constructor *.*/
	public Task() {
		taskName = "";
		endDate = LocalDate.MIN;
		setStartDateAsNow();
	}

	/** Constructor for tasks with only task name *.*/
	public Task(String taskName) {
		this.taskName = taskName;
		setStartDateAsNow();
		this.endDate = LocalDate.MAX;
	}

	/** Constructor for floating tasks *.*/
	public Task(String taskName, LocalDate startDate) {
		this.taskName = taskName;
		this.endDate = LocalDate.MAX;
		this.startDate = startDate;
	}

	/** Constructor with all fields filled *.*/
	public Task(String taskName, LocalDate startDate, LocalDate endDate) {
		this.taskName = taskName;
		this.endDate = endDate;
		this.startDate = startDate;
	}

	public String getName() {
		return taskName;
	}

	public LocalDate getDueDate() {
		return endDate;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setName(String newName) {
		taskName = newName;
	}

	private static void setNewEndDate(int date, int month, int year) {
		LocalDate newDate = LocalDate.of(date, month, year);
	}
}
