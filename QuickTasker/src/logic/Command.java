package logic;

import java.util.List;

import model.Task;

/**
 *
 * Author A0130949 Soh Yonghao
 * 
 * .
 */

public interface Command<E> {
	void execute(List<Task> list, E task);
}
