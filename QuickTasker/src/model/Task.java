package model;

import java.util.Date;

public class Task {
    private String _taskName;
    private Date _deadline;
    
    public Task() {
        
    }
    
    public Task(String taskName, Date deadline) {
        _taskName = taskName;
        _deadline = deadline;
    }
    
    private String getName() {
        return _taskName;
    }
    
    private Date getDate() {
        return _deadline;
    }
    
    private void setName(String newName) {
        _taskName = newName;
    }
    
    private void setDate(Date newDate) {
        _deadline = newDate;
    }
}
