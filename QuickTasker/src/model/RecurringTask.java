package model;
//@@author A0130949
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class RecurringTask extends Task {
    private LocalDate nextStartDate;
    private LocalDate nextEndDate;
    private LocalTime nextStartTime;
    private LocalTime nextEndTime;
    private String recurType;
    int numberToRecur;

    public RecurringTask(String taskName, LocalDate startDate, LocalDate endDate, String recurType,
            LocalTime startTime, LocalTime endTime, int numberToRecur) {
        super(taskName, startDate, endDate, startTime, endTime);
        this.recurType = recurType;
        this.numberToRecur = numberToRecur;
    }

    public void adjustDate() {
        if (LocalDate.now().isAfter(this.getDueDate())) {
            addOffset();
        }
    }

    public String getRecurType() {
        return this.recurType;
    }

    public int getNumberToRecur() {
        return this.numberToRecur;
    }

    private void checkYearsPast() {
        if (LocalDate.now().getYear() > this.getDueDate().getYear()) {
            addOffset();
        }
    }

    private void checkMonthsPast() {
        if (LocalDate.now().getMonthValue() > this.getDueDate().getMonthValue()) {
            if (LocalDate.now().getYear() == this.getDueDate().getYear()) {
                addOffset();
            }
        }
    }

    private void checkDaysPast() {
        if (LocalDate.now().getDayOfMonth() > this.getDueDate().getDayOfMonth()) {
            if (LocalDate.now().getMonthValue() == this.getDueDate().getMonthValue()) {
                if (LocalDate.now().getYear() == this.getDueDate().getYear()) {
                    //long amount = ChronoUnit.DAYS.between(this.getDueDate(), LocalDate.now());
                    //System.out.println("A");
                    addOffset();
                }
            }
        }
    }

    private void addOffset() {
        long amount = this.getDueDate().until(LocalDate.now(), ChronoUnit.DAYS);
        if (this.getRecurType().toLowerCase().equals("week") || this.getRecurType().toLowerCase().equals("weeks")) {
            int offset = calculateOffsetForWeeks((int) amount, this.getNumberToRecur());
            setNextDates(this.getStartDate().plusWeeks(offset),
                    this.getDueDate().plusWeeks(offset));
        } else if (this.getRecurType().toLowerCase().equals("day") || this.getRecurType().toLowerCase().equals("days")){
            int offset = calculateOffsetForDays((int) amount, this.getNumberToRecur());
            setNextDates(this.getStartDate().plusDays(offset), this.getDueDate().plusDays(offset));
        } else if (this.getRecurType().toLowerCase().equals("month") || this.getRecurType().toLowerCase().equals("months")){
            long amountOfMonths = this.getDueDate().until(LocalDate.now(), ChronoUnit.MONTHS);
            setNextDates(this.getStartDate().plusMonths(amountOfMonths + 1), this.getDueDate().plusMonths(amountOfMonths + 1));
        } else {
            long amountOfYears = this.getDueDate().until(LocalDate.now(), ChronoUnit.YEARS);
            setNextDates(this.getStartDate().plusYears(amountOfYears + 1), this.getDueDate().plusYears(amountOfYears + 1));
        }
    }

    private int calculateOffsetForWeeks(int differenceInTimes, int iteration) {
        int amount = iteration * 7;
        int numberOfWeeks = differenceInTimes;
        // System.out.println("differnece = " + differenceInTimes);
        if (differenceInTimes % amount != 0) {
            for (int i = differenceInTimes; i % amount != 0; i++) {
                //System.out.print("number of weeks = " + numberOfWeeks);
                numberOfWeeks = i;
            }
        }
        return (numberOfWeeks + 1) / 7;
    }

    private int calculateOffsetForDays(int differenceInTimes, int iteration) {
        int amount = iteration;
        int numberOfDays = differenceInTimes;
        //System.out.println("differnece = " + differenceInTimes);
        if (differenceInTimes % amount != 0) {
            for (int i = differenceInTimes; i % amount != 0; i++) {
                //System.out.print("number of weeks = " + numberOfDays);
                numberOfDays = i;
            }
            numberOfDays++;
        }
        return (numberOfDays);
    }

    private void setNextDates(LocalDate startDate, LocalDate endDate) {
        setStartDate(startDate);
        setEndDate(endDate);
    }

    public Task stopRecurring() {
        Task newTask = new Task(this.getName(), this.getStartDate(), this.getDueDate(), this.getStartTime(), this.getEndTime());
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