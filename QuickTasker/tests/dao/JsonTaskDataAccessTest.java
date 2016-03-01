package dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.Task;;

public class JsonTaskDataAccessTest {
    List<Task> plannerBook;
    TaskDataAccessObject dataHandler;

    @Before
    public void setUp() throws Exception {
        plannerBook = new ArrayList<Task>();
        dataHandler = new JsonTaskDataAccess();
    }

    @Test
    public void testConvertOneTaskObjToJson() {

    }

}
