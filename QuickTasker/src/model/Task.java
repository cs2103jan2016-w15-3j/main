package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.BlockingDeque;

/**
 * @author A0121558H/A0130949
 */
public class Task implements Comparable {
    private static int IdGenerator;
    private String taskName;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean isDone = false;
    private int id;
    private boolean isRecurring = false;
    /*
    private String type; // type attrib
    private int numToRecur;*/

    public void setStartDateAsNow() {
        startDate = LocalDate.now();
    }

    /**
     * Default constructor *.
     */
    public Task() {
        this.taskName = "";
        this.endDate = LocalDate.MIN;
        this.setStartDateAsNow();
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
    
    // @author: A0133333U
    public Task(String taskName, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        this.taskName = taskName;
        this.endDate = endDate;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endTime = endTime;
        generateId();
    }


/*    public Task(String taskName, LocalDate startDate, LocalDate endDate, String type,
            int numToRecur) {
        this.taskName = taskName;
        this.endDate = endDate;
        this.startDate = startDate;
        generateId();
        this.type = type;
        this.numToRecur = numToRecur;
        setRecurring();
    }*/

    public String getName() {
        return taskName;
    }

    public LocalDate getDueDate() {
        return endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }
    
    public LocalTime getStartTime() {
    	return startTime;
    }
    
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
    
    public void setStartTime(LocalTime time) {
    	this.startTime = time;
    }
    
    public void setEndTime(LocalTime time) {
    	this.endTime = time;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

/*    public void setRecurring() {
        this.isRecurring = true;
    }

    public boolean getRecurring() {
        return isRecurring;
    }*/

    private void generateId() {
        this.id = ++IdGenerator;
    }

 /*   public int getNumberToRecur() {
        return this.numToRecur;
    }*/

/*    public void adjustDate() {
        this.checkYearsPast();
        this.checkMonthsPast();
        this.checkDaysPast();
    }

    private int calculateOffsetForWeeks(int differenceInTimes, int iteration) {
        int amount = iteration * 7;
        int numberOfWeeks = differenceInTimes;
        System.out.println("differnece = " + differenceInTimes);
        if (differenceInTimes % amount != 0) {
            for (int i = differenceInTimes; i % amount != 0; i++) {
                System.out.print("number of weeks = " + numberOfWeeks);
                numberOfWeeks = i;
            }
        }
        return (numberOfWeeks + 1) / 7;
    }

    private int calculateOffsetForDays(int differenceInTimes, int iteration) {
        int amount = iteration;
        int numberOfDays = differenceInTimes;
        System.out.println("differnece = " + differenceInTimes);
        if (differenceInTimes % amount != 0) {
            for (int i = differenceInTimes; i % amount != 0; i++) {
                System.out.print("number of weeks = " + numberOfDays);
                numberOfDays = i;
            }
            numberOfDays++;
        }
        return (numberOfDays);
    }

    private void checkYearsPast() {
        if (LocalDate.now().getYear() > this.getDueDate().getYear()) {
            long amount = this.getDueDate().until(LocalDate.now(), ChronoUnit.DAYS);
            if (this.getType().equals("week")) {
                int offset = calculateOffsetForWeeks((int) amount, this.getNumberToRecur());
                setNextDates(this.getStartDate().plusWeeks(offset),
                        this.getDueDate().plusWeeks(offset));
            } else {
                int offset = calculateOffsetForDays((int) amount, this.getNumberToRecur());
                setNextDates(this.getStartDate().plusDays(offset),
                        this.getDueDate().plusDays(offset));
            }
        }
    }

    public String getType() {
        return this.type;
    }

    private void checkMonthsPast() {
        if (LocalDate.now().getMonthValue() > this.getDueDate().getMonthValue()) {
            if (LocalDate.now().getYear() == this.getDueDate().getYear()) {
                long amount = this.getStartDate().until(LocalDate.now(), ChronoUnit.DAYS);
                if (this.getType().equals("week")) {
                    int offset = calculateOffsetForWeeks((int) amount, this.getNumberToRecur());
                    setNextDates(this.getStartDate().plusWeeks(offset),
                            this.getDueDate().plusWeeks(offset));
                } else {
                    int offset = calculateOffsetForDays((int) amount, this.getNumberToRecur());
                    setNextDates(this.getStartDate().plusDays(offset),
                            this.getDueDate().plusDays(offset));
                }
            }
        }
    }

    private void checkDaysPast() {
        if (LocalDate.now().getDayOfMonth() > this.getDueDate().getDayOfMonth()) {
            if (LocalDate.now().getMonthValue() == this.getDueDate().getMonthValue()) {
                if (LocalDate.now().getYear() == this.getDueDate().getYear()) {
                    long amount = ChronoUnit.DAYS.between(this.getDueDate(), LocalDate.now());
                    if (this.getType().equals("week")) {
                        int offset = calculateOffsetForWeeks((int) amount, this.getNumberToRecur());
                        setNextDates(this.getStartDate().plusWeeks(offset),
                                this.getDueDate().plusWeeks(offset));
                    } else {
                        int offset = calculateOffsetForDays((int) amount, this.getNumberToRecur());
                        setNextDates(this.getStartDate().plusDays(offset),
                                this.getDueDate().plusDays(offset));
                    }
                }
            }
        }
    }

    private void setNextDates(LocalDate startDate, LocalDate endDate) {
        setStartDate(startDate);
        setEndDate(endDate);
    }*/

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

    @Override public int hashCode() {
        int result = taskName.hashCode();
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + id;
        return result;
    }

    @Override public int compareTo(Object task) {
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
