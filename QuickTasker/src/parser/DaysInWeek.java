package parser;
/*
*  author A0130949Y
*
* */

import java.time.LocalDate;

public class DaysInWeek {
    private static LocalDate monday;
    private static LocalDate tuesday;
    private static LocalDate wednesday;
    private static LocalDate thursday;
    private static LocalDate friday;
    private static LocalDate saturday;
    private static LocalDate sunday;

    public DaysInWeek() {
        switch (LocalDate.now().getDayOfWeek()) {
            case MONDAY:
                setMonday();
                break;
            case TUESDAY:
                setTuesday();
                break;
            case WEDNESDAY:
                setWednesday();
                break;
            case THURSDAY:
                setThursday();
                break;
            case FRIDAY:
                setFriday();
                break;
            case SATURDAY:
                setSaturday();
                break;
            default:
                setSunday();
                break;
        }
    }

    private void setMonday() {
        monday = LocalDate.now();
        tuesday = LocalDate.now().plusDays(1);
        wednesday = LocalDate.now().plusDays(2);
        thursday = LocalDate.now().plusDays(3);
        friday = LocalDate.now().plusDays(4);
        saturday = LocalDate.now().plusDays(5);
        sunday = LocalDate.now().plusDays(6);
    }

    private void setTuesday() {
        tuesday = LocalDate.now();
        wednesday = LocalDate.now().plusDays(1);
        thursday = LocalDate.now().plusDays(2);
        friday = LocalDate.now().plusDays(3);
        saturday = LocalDate.now().plusDays(4);
        sunday = LocalDate.now().plusDays(5);
        monday = LocalDate.now().plusDays(6);
    }

    private void setWednesday() {
        wednesday = LocalDate.now();
        thursday = LocalDate.now().plusDays(1);
        friday = LocalDate.now().plusDays(2);
        saturday = LocalDate.now().plusDays(3);
        sunday = LocalDate.now().plusDays(4);
        monday = LocalDate.now().plusDays(5);
        tuesday = LocalDate.now().plusDays(6);
    }

    private void setThursday() {
        thursday = LocalDate.now();
        friday = LocalDate.now().plusDays(1);
        saturday = LocalDate.now().plusDays(2);
        sunday = LocalDate.now().plusDays(3);
        monday = LocalDate.now().plusDays(4);
        tuesday = LocalDate.now().plusDays(5);
        wednesday = LocalDate.now().plusDays(6);
    }

    private void setFriday() {
        friday = LocalDate.now();
        saturday = LocalDate.now().plusDays(1);
        sunday = LocalDate.now().plusDays(2);
        monday = LocalDate.now().plusDays(3);
        tuesday = LocalDate.now().plusDays(4);
        wednesday = LocalDate.now().plusDays(5);
        thursday = LocalDate.now().plusDays(6);
    }

    private void setSaturday() {
        saturday = LocalDate.now();
        sunday = LocalDate.now().plusDays(1);
        monday = LocalDate.now().plusDays(2);
        tuesday = LocalDate.now().plusDays(3);
        wednesday = LocalDate.now().plusDays(4);
        thursday = LocalDate.now().plusDays(5);
        friday = LocalDate.now().plusDays(6);
    }

    private void setSunday() {
        sunday = LocalDate.now();
        monday = LocalDate.now().plusDays(1);
        tuesday = LocalDate.now().plusDays(2);
        wednesday = LocalDate.now().plusDays(3);
        thursday = LocalDate.now().plusDays(4);
        friday = LocalDate.now().plusDays(5);
        saturday = LocalDate.now().plusDays(6);
    }

    public LocalDate getMonday() {
        return this.monday;
    }

    public LocalDate getTuesday() {
        return this.tuesday;
    }

    public LocalDate getWednesday() {
        return this.wednesday;
    }

    public LocalDate getThursday() {
        return this.thursday;
    }

    public LocalDate getFriday() {
        return this.friday;
    }

    public LocalDate getSaturday() {
        return this.saturday;
    }

    public LocalDate getSunday() {
        return this.sunday;
    }
}
