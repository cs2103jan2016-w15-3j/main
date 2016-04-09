package data;
//@@author A0126077E

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.RecurringTask;
import model.Task;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class JsonTaskDataAccessTest {
    private JsonTaskDataAccess dataHandler;
    private List<Task> plannerNotebook;
    public static final String WORKING_DIRECTORY = System.getProperty("user.dir");
    public static final String PATH_SEPARATOR =  System.getProperty("file.separator");
    public static final String FILE_NAME = "test.json";

    @Before
    public void setUp() throws Exception {
        plannerNotebook = new ArrayList<>();
        dataHandler = new JsonTaskDataAccess();
        dataHandler.setSavePath(FILE_NAME);
    }

    @After
    public void tearDown() {
        dataHandler.reset();
    }

    @Test
    public void whenNewHandlerCreatedPathShouldNotBeNull() {
        assertNotNull(dataHandler.getFilePath());
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
            Gson gson = getGson();
            List<Task> testObj = gson.fromJson(reader, new TypeToken<List<Task>>() {}.getType());
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
        assertEquals(testTask, readOneTask());
    }

    @Test
    public void canReadsavedTasksFromJsonFile() {
        plannerNotebook = create30TasksWithDifferentAttributes();
        dataHandler.save(plannerNotebook);
        assertEquals(plannerNotebook, dataHandler.getTasks());
    }

    @Test
    public void canSaveOneRecurrTask() {

        RecurringTask task = new RecurringTask("RecurringTask11", LocalDate.MIN, LocalDate.MAX, "week",
                LocalTime.NOON, LocalTime.now(), 1);
        List<Task> tasks = new ArrayList<>();
        tasks.add(task);
        dataHandler.save(tasks);
        assertEquals(dataHandler.getTasks().get(0).getName(), task.getName());
    }

    @Test
    public void ifTaskSaveFileHasNoTaskGetTasksShouldNotReturnNull() throws IOException {
        dataHandler.reset();
        assertNotNull(dataHandler.getTasks());
    }

    @Test
    public void deserializedRecurringTasksShouldHaveCorrectType() {
        Task t = new RecurringTask("task1", LocalDate.now(), LocalDate.now(), "week", LocalTime.NOON,
                LocalTime.MIDNIGHT, 1);
        List<Task> tasks = new ArrayList<>();
        tasks.add(t);
        dataHandler.save(tasks);
        dataHandler.getTasks().get(0);
        assertEquals(RecurringTask.class, dataHandler.getTasks().get(0).getClass());
    }

    @Test
    public void allDeserializedTasksShouldBeEqualToOriginal() {
        List<Task> tasks = create30TasksWithDifferentAttributes();
        Object[] taskArr = tasks.toArray();
        dataHandler.save(tasks);

        assertArrayEquals(taskArr, dataHandler.getTasks().toArray());

    }

    @Test
    public void deserilizedTasksShouldHaveCorrectType() {
        List<Task> expected = create30TasksWithDifferentAttributes(); // last 10 are recurr tasks
        dataHandler.save(expected);
        List<Task> result = dataHandler.getTasks();
        for (int i = 0; i < expected.size(); ++i)
            assertEquals(expected.get(i).getClass(), result.get(i).getClass());
    }

    @Test
    public void deserilizedRecurTaskShouldContainTypeAttribute() {
        List<Task> expected = create30TasksWithDifferentAttributes();
        dataHandler.save(expected);
        List<Task> result = dataHandler.getTasks();

        for (int i = 20; i < expected.size(); ++i)
            assertEquals(((RecurringTask) result.get(i)).getClass(),
                    ((RecurringTask) expected.get(i)).getClass());

    }

    @Test
    public void saveSaveFileToExistingDirectory() {
        List<Task> tasks = create30TasksWithDifferentAttributes();
        dataHandler.setSavePath(WORKING_DIRECTORY + PATH_SEPARATOR + FILE_NAME);
        System.out.println(dataHandler.getFilePath());
        dataHandler.save(tasks);
    }

    private Task readOneTask() {
        try {
            BufferedReader reader = Files.newBufferedReader(dataHandler.getFilePath());
            Gson gson = getGson();
            Task $ = gson.fromJson(reader, Task.class);
            reader.close();
            return $;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<Task> create30TasksWithDifferentAttributes() {
        List<Task> $ = new ArrayList<Task>(createTasksWithStartAndEnd(10));
        $.addAll(createTasksWithOnlyTaskNameAttribute(10));
        $.addAll(createRecurringTasks(10));
        return $;
    }

    private List<Task> createRecurringTasks(int numberOfTasks) {
        List<Task> $ = new ArrayList<>();
        for (int i = 0; i < numberOfTasks; ++i)
            $.add((new RecurringTask(("Recurring Task " + i), LocalDate.now(), LocalDate.now().plusMonths(1),
                    "week", LocalTime.NOON, LocalTime.MIDNIGHT, 1)));
        return $;
    }

    private List<Task> createTasksWithOnlyTaskNameAttribute(int numberOfTasks) {
        List<Task> $ = new ArrayList<>();
        for (int i = 0; i < numberOfTasks; ++i)
            $.add((new Task(("Task " + i))));
        return $;
    }

    private List<Task> createTasksWithStartAndEnd(int numberOfTasks) {
        List<Task> $ = new ArrayList<>();
        for (int i = 0; i < numberOfTasks; ++i)
            $.add((new Task(("Task " + i), LocalDate.now(), LocalDate.now())));
        return $;
    }

    private boolean hasSaveFile() {
        return Files.exists(dataHandler.getFilePath());
    }

    private Gson getGson() {
        return new GsonBuilder().setPrettyPrinting()
                .registerTypeAdapterFactory(
                        RuntimeTypeAdapterFactory.of(Task.class).registerSubtype(Task.class, "Task")
                                .registerSubtype(RecurringTask.class, "RecurringTask"))
                .create();
    }

}
