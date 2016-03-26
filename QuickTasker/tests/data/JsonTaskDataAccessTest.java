package data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.RecurringTask;
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
    private JsonTaskDataAccess dataHandler;

    @Before
    public void setUp() throws Exception {
        List<Task> plannerNotebook = new ArrayList<>();
        dataHandler = new JsonTaskDataAccess();
    }

    @Test
    public void whenNewHandlerCreatedPathShouldNotBeNull() {
        assertNotNull(dataHandler.getFilePath());
    }

    @Test
    public void ifPathOfSaveFileIsNullThenUseDefaultPath() {

    }

    @Test
    public void ifThereIsNoSaveFileCreateDefaultBasedOnSettingsFileName() throws IOException {
        Files.deleteIfExists(dataHandler.getFilePath());
        dataHandler = new JsonTaskDataAccess();
        assertTrue(hasSaveFile());
    }

    @Test
    public void canSaveListOfTasksToJsonFile() {
        List<Task> tasks = createTasksWithStartAndEnd(20);
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

    @Test
    public void canSaveOneTaskIntoJsonFile() {
        String taskName = "Task 1";
        Task testTask = new Task(taskName, LocalDate.now(), LocalDate.now());
        dataHandler.save(testTask);
        Task resultTask = readOneTask();
        assertEquals(testTask, resultTask);
    }

    @Test
    public void canReadsavedTasksFromJsonFile() {
        plannerNotebook = create30TasksWithDifferentAttributes();
        dataHandler.save(plannerNotebook);
        List<Task> tasksRead = dataHandler.getTasks();
        assertEquals(plannerNotebook, tasksRead);
    }

    @Test
    public void ifTaskSaveFileHasNoTaskGetTasksShouldNotReturnNull() throws IOException {
        dataHandler.reset();
        assertNotNull(dataHandler.getTasks());
    }

    @Test
    public void deserializedRecurringTasksShouldHaveCorrectType() {
        Task t = new RecurringTask("task1", LocalDate.now(), LocalDate.now(), "week");
        List<Task> tasks = new ArrayList<>();
        tasks.add(t);
        dataHandler.save(tasks);
        dataHandler.getTasks().get(0);
        assertEquals(RecurringTask.class, dataHandler.getTasks().get(0).getClass());
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

    private List<Task> create30TasksWithDifferentAttributes() {
        List<Task> tasks = new ArrayList<>();
        tasks.addAll(createTasksWithStartAndEnd(10));
        tasks.addAll(createTasksWithOnlyTaskNameAttribute(10));
        tasks.addAll(createRecurringTasks(10));
        return tasks;
    }

    private List<Task> createRecurringTasks(int numberOfTasks) {
        List<Task> tasks = new ArrayList<>();
        for (int i = 0; i < numberOfTasks; i++) {
            String taskName = "Recurring Task " + i;
            Task recurringTask = new RecurringTask(taskName, LocalDate.now(),
                    LocalDate.now().plusMonths(1), "week");
            tasks.add(recurringTask);
        }
        return tasks;
    }

    private List<Task> createTasksWithOnlyTaskNameAttribute(int numberOfTasks) {
        List<Task> tasks = new ArrayList<>();
        for (int i = 0; i < numberOfTasks; i++) {
            String taskName = "Task " + i;
            Task task = new Task(taskName);
            tasks.add(task);
        }
        return tasks;
    }

    private List<Task> createTasksWithStartAndEnd(int numberOfTasks) {
        List<Task> tasks = new ArrayList<>();
        for (int i = 0; i < numberOfTasks; i++) {
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
