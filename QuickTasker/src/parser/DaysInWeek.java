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
    private static final int OFFSET_ONE = 1;
    private static final int OFFSET_TWO = 2;
    private static final int OFFSET_THREE = 3;
    private static final int OFFSET_FOUR = 4;
    private static final int OFFSET_FIVE = 5;
    private static final int OFFSET_SIX = 6;
    private static final int OFFSET_SEVEN = 7;

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
        tuesday = LocalDate.now().plusDays(OFFSET_ONE);
        wednesday = LocalDate.now().plusDays(OFFSET_TWO);
        thursday = LocalDate.now().plusDays(OFFSET_THREE);
        friday = LocalDate.now().plusDays(OFFSET_FOUR);
        saturday = LocalDate.now().plusDays(OFFSET_FIVE);
        sunday = LocalDate.now().plusDays(OFFSET_SIX);
    }

    private void setTuesday() {
        tuesday = LocalDate.now();
        wednesday = LocalDate.now().plusDays(OFFSET_ONE);
        thursday = LocalDate.now().plusDays(OFFSET_TWO);
        friday = LocalDate.now().plusDays(OFFSET_THREE);
        saturday = LocalDate.now().plusDays(OFFSET_FOUR);
        sunday = LocalDate.now().plusDays(OFFSET_FIVE);
        monday = LocalDate.now().plusDays(OFFSET_SIX);
    }

    private void setWednesday() {
        wednesday = LocalDate.now();
        thursday = LocalDate.now().plusDays(OFFSET_ONE);
        friday = LocalDate.now().plusDays(OFFSET_TWO);
        saturday = LocalDate.now().plusDays(OFFSET_THREE);
        sunday = LocalDate.now().plusDays(OFFSET_FOUR);
        monday = LocalDate.now().plusDays(OFFSET_FIVE);
        tuesday = LocalDate.now().plusDays(OFFSET_SIX);
    }

    private void setThursday() {
        thursday = LocalDate.now();
        friday = LocalDate.now().plusDays(OFFSET_ONE);
        saturday = LocalDate.now().plusDays(OFFSET_TWO);
        sunday = LocalDate.now().plusDays(OFFSET_THREE);
        monday = LocalDate.now().plusDays(OFFSET_FOUR);
        tuesday = LocalDate.now().plusDays(OFFSET_FIVE);
        wednesday = LocalDate.now().plusDays(OFFSET_SIX);
    }

    private void setFriday() {
        friday = LocalDate.now();
        saturday = LocalDate.now().plusDays(OFFSET_ONE);
        sunday = LocalDate.now().plusDays(OFFSET_TWO);
        monday = LocalDate.now().plusDays(OFFSET_THREE);
        tuesday = LocalDate.now().plusDays(OFFSET_FOUR);
        wednesday = LocalDate.now().plusDays(OFFSET_FIVE);
        thursday = LocalDate.now().plusDays(OFFSET_SIX);
    }

    private void setSaturday() {
        saturday = LocalDate.now();
        sunday = LocalDate.now().plusDays(OFFSET_ONE);
        monday = LocalDate.now().plusDays(OFFSET_TWO);
        tuesday = LocalDate.now().plusDays(OFFSET_THREE);
        wednesday = LocalDate.now().plusDays(OFFSET_FOUR);
        thursday = LocalDate.now().plusDays(OFFSET_FIVE);
        friday = LocalDate.now().plusDays(OFFSET_SIX);
    }

    private void setSunday() {
        sunday = LocalDate.now();
        monday = LocalDate.now().plusDays(OFFSET_ONE);
        tuesday = LocalDate.now().plusDays(OFFSET_TWO);
        wednesday = LocalDate.now().plusDays(OFFSET_THREE);
        thursday = LocalDate.now().plusDays(OFFSET_FOUR);
        friday = LocalDate.now().plusDays(OFFSET_FIVE);
        saturday = LocalDate.now().plusDays(OFFSET_SIX);
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
