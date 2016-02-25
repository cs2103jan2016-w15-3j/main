package controller;
// specifies methods required for communication between UI , parser and logic

import java.time.LocalDate;

public interface InputInterface {

    public String getTaskName();

    public LocalDate getTaskDate();

    public String getTaskCommand();

}
