package logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import model.Task;

public class Sort<E> implements Command {

    @Override
    public void execute(List list, Object op) {
        System.out.println("really sorting now");
        Collections.sort(list);
        System.out.println("finish sorting");
/*        for(int i = 0; i < list.size(); i++) {   
            System.out.println(list.get(i) + "\n");
        } */
    }

    @Override
    public void undo(ArrayList list) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void redo(ArrayList list) {
        // TODO Auto-generated method stub
        
    }
    

}
