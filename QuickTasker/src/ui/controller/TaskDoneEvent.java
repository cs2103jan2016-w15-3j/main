package ui.controller;
//@@author A0126077E
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import model.Task;

public class TaskDoneEvent extends Event {

    public static final EventType<TaskDoneEvent> TASK_COMPLETE = new EventType(ANY,
            "TASK_COMPLETE");

    private Task task;

    public TaskDoneEvent(Task task) {
        this(TASK_COMPLETE);
        this.task = task;
    }

    public Task getTask() {

        return task;
    }

    public TaskDoneEvent(EventType<? extends Event> arg0) {
        super(arg0);
    }

    public TaskDoneEvent(Object arg0, EventTarget arg1, EventType<? extends Event> arg2) {
        super(arg0, arg1, arg2);
    }
}
