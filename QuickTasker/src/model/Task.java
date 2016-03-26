package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.LocalTime;

/**
 * .
 *
 * @author A0121558H/A0130949
 */
public class Task implements Comparable {
    private static int IdGenerator;
    private String taskName;
    private LocalDate startDate;
    private boolean isDone = false;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;

/*    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (id != task.id) return false;
        if (!taskName.equals(task.taskName)) return false;
        if (endDate != null ? !endDate.equals(task.endDate) : task.endDate != null) return false;
        return startDate != null ? startDate.equals(task.startDate) : task.startDate == null;

    }*/

    private int id;
    private boolean isRecurring = false;
    private String type;

    public void setStartDateAsNow() {
        startDate = LocalDate.now();
    }

    /**
     * Default constructor *.
     */
    public Task() {
        taskName = "";
        startDate= LocalDate.MIN;
        endDate = LocalDate.MIN;
        startTime=LocalTime.MIN;
        endTime=LocalTime.MIN;
        setStartDateAsNow();
        generateId();
    }

    /**
     * Constructor for tasks with only task name *.
     */
    public Task(String taskName) {
        this.taskName = taskName;
        setStartDateAsNow();
        this.endDate = LocalDate.MIN;
        generateId();
    }

    /**
     * Constructor for floating tasks *.
     */
    public Task(String taskName, LocalDate startDate) {
        this.taskName = taskName;
        this.endDate = LocalDate.MIN;
        this.startDate = startDate;
        generateId();
    }

    /**
     * Constructor with all fields filled *.
     */
    public Task(String taskName, LocalDate startDate, LocalDate endDate) {
        this.taskName = taskName;
        this.endDate = endDate;
        this.startDate = startDate;
        generateId();
    }
    
    public Task(String taskName, LocalDate startDate, LocalDate endDate, String type) {
        this.taskName = taskName;
        this.endDate = endDate;
        this.startDate = startDate;
        generateId();
        this.type = type;
        setRecurring();
    }

    public Task(String taskName, LocalDate startDate, LocalDate endDate, 
            LocalTime startTime) {
        this.taskName = taskName;
        this.endDate = endDate;
        this.startDate = startDate;
        this.endDate=endDate;
        this.startTime=startTime;
        generateId();     
    }
    public Task(String taskName, LocalDate startDate, LocalDate endDate, 
            LocalTime startTime, LocalTime endTime) {
        this.taskName = taskName;
        this.endDate = endDate;
        this.startDate = startDate;
        this.endDate=endDate;
        this.startTime=startTime;
        this.endTime=endTime;
        generateId();     
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
    
    // @author: A0133333U
    public LocalTime getStartTime() {
    	return startTime;
    	
    }
    
    // @author: A0133333U
    public LocalTime getEndTime() {
    	return endTime;
    }

    public void setName(String newName) {
        taskName = newName;
    }

    public int getId() {
        return id;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setStartDate(LocalDate date) {
        this.startDate = date;
    }

    public void setEndDate(LocalDate date) {
        this.endDate = date;
    }

    public void setDone(boolean done) {
        isDone = done;   
    }
    
    public void setRecurring() {
        this.isRecurring = true;
    }
    
    public boolean getRecurring() {
        return isRecurring;
    }

    private void generateId() {
        this.id = ++IdGenerator;
    }
    
    public void adjustDate() {
        this.checkYearsPast();
        this.checkMonthsPast();
        this.checkDaysPast();
    }

    private void checkYearsPast() {
        if (LocalDate.now().getYear() > this.getDueDate().getYear()) {
            if (this.getType().equals("week")) {
                long amount = this.getDueDate().until(LocalDate.now(), ChronoUnit.WEEKS);
                setNextDates(this.getStartDate().plusWeeks(amount + 1),
                        this.getDueDate().plusWeeks(amount + 1));
            } else {
                long amount = this.getStartDate().until(LocalDate.now(), ChronoUnit.DAYS);
                setNextDates(this.getStartDate().plusDays(amount),
                        this.getDueDate().plusDays(amount));
            }
        }
    }

    public String getType() {
        return this.type;
    }

    private void checkMonthsPast() {
        if (LocalDate.now().getMonthValue() > this.getDueDate().getMonthValue()) {
            if (LocalDate.now().getYear() == this.getDueDate().getYear()) {
                if (this.getType().equals("week")) {
                    long amount = this.getDueDate().until(LocalDate.now(), ChronoUnit.WEEKS);
                    setNextDates(this.getStartDate().plusWeeks(amount + 1),
                            this.getDueDate().plusWeeks(amount + 1));
                } else {
                    long amount = this.getStartDate().until(LocalDate.now(), ChronoUnit.DAYS);
                    setNextDates(this.getStartDate().plusDays(amount),
                            this.getDueDate().plusDays(amount));
                }
            }
        }
    }

    private void checkDaysPast() {
        if (LocalDate.now().getDayOfMonth() > this.getDueDate().getDayOfMonth()) {
            if (LocalDate.now().getMonthValue() == this.getDueDate().getMonthValue()) {
                if (LocalDate.now().getYear() == this.getDueDate().getYear()) {
                    if (this.getType().equals("week")) {
                        long amount = ChronoUnit.WEEKS.between(this.getDueDate(), LocalDate.now());
                        setNextDates(this.getStartDate().plusWeeks(amount + 1),
                                this.getDueDate().plusWeeks(amount + 1));
                    } else {
                        long amount = ChronoUnit.DAYS.between(this.getStartDate(), LocalDate.now());
                        System.out.println("" + amount);
                        setNextDates(this.getStartDate().plusDays(amount),
                                this.getDueDate().plusDays(amount));
                        //System.out.println(this.getStartDate());
                    }
                }
            }
        }
    }

    private void setNextDates(LocalDate startDate, LocalDate endDate) {
        setStartDate(startDate);
        setEndDate(endDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;

        Task task = (Task) o;

        if (id != task.id) return false;
        if (!taskName.equals(task.taskName)) return false;
        if (endDate != null ? !endDate.equals(task.endDate) : task.endDate != null) return false;
        return startDate != null ? startDate.equals(task.startDate) : task.startDate == null;

    }
    
    @Override
    public int hashCode() {
        int result = taskName.hashCode();
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + id;
        return result;
    }

    @Override
    public int compareTo(Object task) {
        Task comparedTask = (Task) task;
        if (this.getDueDate() == LocalDate.MIN) {
            return -1;
        }
        if (this.getDueDate().getDayOfMonth() > comparedTask.getDueDate().getDayOfMonth()) {
            if (this.getDueDate().getMonthValue() >= comparedTask.getDueDate().getMonthValue()) {
                if (this.getDueDate().getYear() >= comparedTask.getDueDate().getYear()) {
                    return 1;
                }
            }
        }
        if (this.getDueDate().getMonthValue() > comparedTask.getDueDate().getMonthValue()) {
            if (this.getDueDate().getYear() > comparedTask.getDueDate().getYear()) {
                return 1;
            }
        }
        if (this.getDueDate().getYear() > comparedTask.getDueDate().getYear()) {
            return 1;
        }

        if (this.getStartDate().getDayOfMonth() > comparedTask.getStartDate().getDayOfMonth()) {
            if (this.getStartDate().getMonthValue() >= comparedTask.getStartDate()
                    .getMonthValue()) {
                if (this.getStartDate().getYear() >= comparedTask.getStartDate().getYear()) {
                    return 1;
                }
            }
        }
        if (this.getStartDate().getMonthValue() > comparedTask.getStartDate().getMonthValue()) {
            if (this.getStartDate().getYear() > comparedTask.getStartDate().getYear()) {
                return 1;
            }
        }
        if (this.getStartDate().getYear() > comparedTask.getStartDate().getYear()) {
            return 1;
        }
        return -1;
    }
}
