package logic;

import model.Task;

import java.util.List;

/**
 * Author A0130949 Soh Yonghao
 * <p>
 * .
 */

public interface Command<E> {
    void execute(List<Task> list, E task);
}
