package logic;

import data.SettingManager;
import model.Task;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import parser.Commands;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Author A0130949 Soh Yonghao
 * <p>
 * .
 */

public class DeleteTaskTest {
    Logic logic;
    SettingManager settings;

<<<<<<< HEAD:QuickTasker/src/logic/DeleteTaskTest.java
    @Before
    public void setUp() throws Exception {
=======
    @Before public void setUp() throws Exception {
>>>>>>> jialin3:QuickTasker/tests/logic/DeleteTaskTest.java
        settings = new SettingManager();
        settings.setPathOfSaveFile("test.json");
        init();
    }

<<<<<<< HEAD:QuickTasker/src/logic/DeleteTaskTest.java
    @After
    public void tearDown() {
=======
    @After public void tearDown() {
>>>>>>> jialin3:QuickTasker/tests/logic/DeleteTaskTest.java
        settings.resetDefaultSettings();
        logic.clear();
    }

    private void init() {
        logic = new Logic();
        for (int i = 0; i < 10; i++) {
            Task task = new Task();
            logic.list.add(task);
        }
    }

    @Test public void test() throws Exception {
        assertEquals(logic.getSize(), 10);
        logic.deleteTask(0);
        assertEquals(logic.getSize(), 9);
        logic.deleteTask(8);
        assertEquals(logic.getSize(), 8);
        logic.commandMap.get(Commands.DELETE_TASK).undo((ArrayList<Task>) logic.list);
        assertEquals(logic.getSize(), 9);
    }
}
