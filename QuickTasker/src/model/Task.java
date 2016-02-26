package model;

import java.time.LocalDate;

public class Task {
    private String _taskName;
    private static LocalDate _deadline;
    private LocalDate _startDate;
    
    public void setStartDate(LocalDate startDate) {
        _startDate = LocalDate.now();
    }
    public Task() {        

        _taskName = null;
        _deadline = null;
        _startDate = null;
    }
    
    public Task(String taskName, LocalDate deadline) {
        _taskName = taskName;
        _deadline = deadline;
    }
    
    public Task(String taskName, LocalDate deadline, LocalDate startDate) {
        _taskName = taskName;
        _deadline = deadline;
        _startDate = startDate;
    }

    public String getName() {
        return _taskName;
    }

    private LocalDate getDate() {
        return _deadline;
    }
    
    
    private LocalDate getStartDate() {
        return _startDate;
    }
    
    private void setName(String newName) {
        _taskName = newName;
    }

    private static void setDate(int year, int month, int date) {
        LocalDate newDate = LocalDate.of(date, month, year);
        _deadline = newDate;
    }
}
