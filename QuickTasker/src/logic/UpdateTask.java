package logic;

import model.Task;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class UpdateTask<E> implements Command<Object> {
    private static Stack<Task> undoStackTask = new Stack<Task>();
    private static Stack<Integer> undoStackInt = new Stack<Integer>();
    private static Stack<Task> redoStackTask = new Stack<Task>();
    private static Stack<Integer> redoStackInt = new Stack<Integer>();

    @Override
    public void execute(List<Task> list, Object index) {
        int taskIndex = (int) index;
        undoStackTask.push(list.get(taskIndex));
        undoStackInt.push(taskIndex);
        executeUpdate(taskIndex, list);
    }

    public void executeUpdate(int taskIndex, List<Task> list) {
        Task newTask = list.remove(list.size() - 1);
        checkAttributes(newTask, taskIndex, list);
        list.set(taskIndex, newTask);
    }

    private void checkAttributes(Task updatedTask, int taskIndex, List<Task> list) {
        if (updatedTask.getStartDate() == LocalDate.MAX
                && list.get(taskIndex).getStartDate() != LocalDate.MAX) {
            updatedTask.setStartDate(list.get(taskIndex).getStartDate());
        }

        if (updatedTask.getDueDate() == LocalDate.MAX
                && list.get(taskIndex).getDueDate() != LocalDate.MAX) {
            updatedTask.setEndDate(list.get(taskIndex).getDueDate());
        }

        if (updatedTask.getName().isEmpty() && !list.get(taskIndex).getName().isEmpty()) {
            updatedTask.setName(list.get(taskIndex).getName());
        }

        if (updatedTask.getStartTime() == LocalTime.MAX && !list.get(taskIndex).getStartTime()
                .equals(LocalTime.MAX)) {
            System.out.println("AA");
            updatedTask.setStartTime(list.get(taskIndex).getStartTime());
        }

        if (updatedTask.getEndTime() == LocalTime.MAX && !list.get(taskIndex).getEndTime()
                .equals(LocalTime.MAX)) {
            System.out.println("BB");
            updatedTask.setEndTime(list.get(taskIndex).getEndTime());
        }
    }

    @Override
    public void undo(ArrayList<Task> list) {
        int undoIndex = undoStackInt.pop();
        Task undoTask = undoStackTask.pop();
        redoStackInt.push(undoIndex);
        redoStackTask.push(list.get(undoIndex));
        list.set(undoIndex, undoTask);
    }

    @Override
    public void redo(ArrayList<Task> list) {
        int redoIndex = redoStackInt.pop();
        Task redoTask = redoStackTask.pop();
        undoStackInt.push(redoIndex);
        undoStackTask.push(list.get(redoIndex));
        list.set(redoIndex, redoTask);
    }
}
