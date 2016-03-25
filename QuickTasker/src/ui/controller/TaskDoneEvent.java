package ui.controller;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

public class TaskDoneEvent extends Event {
 public static final EventType<TaskDoneEvent> TASK_COMPLETE = new EventType(ANY,
 "TASK_COMPLETE");

 public TaskDoneEvent() {
 this(TASK_COMPLETE);
 }

 public TaskDoneEvent(EventType<? extends Event> arg0) {
 super(arg0);
 }

 public TaskDoneEvent(Object arg0, EventTarget arg1, EventType<? extends Event> arg2) {
 super(arg0, arg1, arg2);
 }
}