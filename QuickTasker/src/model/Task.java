package model;

import java.time.LocalDate;

import sun.management.VMOptionCompositeData;

public class Task {
    private String _taskName;
    private static LocalDate _deadline;
    private LocalDate _startDate;
    private static int _index = 0;
    
    public void setStartDate(LocalDate startDate) {
        _startDate = LocalDate.now();
    }
    
    public Task(int index) {        
        _index = index;
        _taskName = null;
        _deadline = null;
        _startDate = null;
    }
    
    public Task(String taskName, LocalDate deadline, int index) {
        _taskName = taskName;
        _deadline = deadline;
        _index = index;
    }
    
    public Task(String taskName, LocalDate deadline, LocalDate startDate, int index) {
        _taskName = taskName;
        _deadline = deadline;
        _startDate = startDate;
        _index = index;
    }
    
    public int getIndex() {
        return _index;
    }
    
    public void setIndex(int newIndex) {
        _index = newIndex;
    }

    public String getName() {
        return _taskName;
    }

    public LocalDate getDate() {
        return _deadline;
    }
    
    
    public LocalDate getStartDate() {
        return _startDate;
    }
    
    public void setName(String newName) {
        _taskName = newName;
    }

    public static void setDate(int year, int month, int date) {
        LocalDate newDate = LocalDate.of(date, month, year);
        _deadline = newDate;
    }
}
