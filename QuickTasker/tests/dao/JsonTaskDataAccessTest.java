package dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Task;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class JsonTaskDataAccessTest {
    private List<Task> plannerNotebook;
    private JsonTaskDataAccess dataHandler;

    @Before public void setUp() throws Exception {
        plannerNotebook = new ArrayList<>();
        dataHandler = new JsonTaskDataAccess();
    }

    @Test public void whenNewHandlerCreatedPathShouldNotBeNull() {
        assertNotNull(dataHandler.getFilePath());
    }

    @Test public void ifPathOfSaveFileIsNullThenUseDefaultPath() {

    }

    @Test public void ifThereIsNoSaveFileCreateDefaultBasedOnSettingsFileName() throws IOException {
        Files.deleteIfExists(dataHandler.getFilePath());

        dataHandler = new JsonTaskDataAccess();
        assertTrue(hasSaveFile());
    }

    @Test public void canSaveListOfTasksToJsonFile() {
        List<Task> tasks = create10Tasks();
        dataHandler.save(tasks);
        try {
            BufferedReader reader = Files.newBufferedReader(dataHandler.getFilePath());
            Gson gson = new Gson();
            List<Task> testObj = gson.fromJson(reader, new TypeToken<List<Task>>() {
            }.getType());
            assertEquals(testObj, tasks);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test public void canSaveOneTaskIntoJsonFile() {
        String taskName = "Task 1";
        Task testTask = new Task(taskName, LocalDate.now(), LocalDate.now());
        dataHandler.save(testTask);
        Task resultTask = readOneTask();
        assertEquals(testTask, resultTask);
    }

    private Task readOneTask() {
        try {
            BufferedReader reader = Files.newBufferedReader(dataHandler.getFilePath());
            Gson gson = new Gson();
            Task testObj = gson.fromJson(reader, Task.class);
            reader.close();
            return testObj;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<Task> create10Tasks() {
        List<Task> tasks = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String taskName = "Task " + i;
            Task task = new Task(taskName, LocalDate.now(), LocalDate.now());
            tasks.add(task);
        }
        return tasks;

    }

    private boolean hasSaveFile() {
        return Files.exists(dataHandler.getFilePath());
    }

}
