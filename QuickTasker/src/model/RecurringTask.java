package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class RecurringTask extends Task {
    private LocalDate nextStartDate;
    private LocalDate nextEndDate;
    private String type;
    
    public RecurringTask(String taskName, LocalDate startDate, LocalDate endDate, String type) {
            super(taskName, startDate, endDate);
            this.type = type;
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
                setNextDates(this.getStartDate().plusWeeks(amount + 1), this.getDueDate().plusWeeks(amount + 1));
            } else {
                long amount = this.getStartDate().until(LocalDate.now(), ChronoUnit.DAYS);
                setNextDates(this.getStartDate().plusDays(amount), this.getDueDate().plusDays(amount));
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
                    setNextDates(this.getStartDate().plusWeeks(amount + 1), this.getDueDate().plusWeeks(amount + 1));
                } else {
                    long amount = this.getStartDate().until(LocalDate.now(), ChronoUnit.DAYS);
                    setNextDates(this.getStartDate().plusDays(amount), this.getDueDate().plusDays(amount));
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
                        setNextDates(this.getStartDate().plusWeeks(amount + 1), this.getDueDate().plusWeeks(amount + 1));
                    } else {
                        long amount = ChronoUnit.DAYS.between(this.getStartDate(), LocalDate.now());
                        System.out.println("" + amount);
                        setNextDates(this.getStartDate().plusDays(amount), this.getDueDate().plusDays(amount));
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
    
    public Task stopRecurring(RecurringTask task) {
        Task newTask = new Task(task.getName(), task.nextStartDate, task.getDueDate());
        return newTask;
    }
    
    private void setNextStartDate(LocalDate date) {
        this.nextStartDate = date;
    }
    
    private void setNextEndDate(LocalDate date) {
        this.nextEndDate = date;
    }
    
    protected LocalDate getNextStartDate() {
        return nextStartDate;
    }
    
    protected LocalDate getNextEndDate() {
        return nextEndDate;
    }
}
