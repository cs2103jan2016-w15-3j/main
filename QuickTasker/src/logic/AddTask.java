package logic;

import java.util.List;
/*
*
* author A0130949
* Soh Yonghao 
* 
**/

import model.Task;

public class AddTask<E> implements Command {
    
    @Override
    public void execute(List list, Object task) {
        executeAdd(list, (Task) task); 
    }
    
    private void executeAdd(List<Task> list, Task task) {
        if (task != null) {
            list.add(task);

        }
    }
}
