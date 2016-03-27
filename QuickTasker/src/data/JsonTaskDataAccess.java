package data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.RecurringTask;
import model.Task;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JsonTaskDataAccess implements TaskDataAccessObject {
    private static final String DEFAULT_FILENAME = "tasks.json";
    private static final Path DEFAULT_SAVE_PATH = Paths.get(DEFAULT_FILENAME);

    private SettingManager settings = new SettingManager();
    private Logger logger;
    private Path pathOfSaveFile;

    public JsonTaskDataAccess() {
        initialize();
    }

    private void initialize() {
        String p = settings.getPathOfSaveFile();
        if (p != null) {
            pathOfSaveFile = Paths.get(p);
        } else {
            pathOfSaveFile = DEFAULT_SAVE_PATH;
        }
        if (Files.notExists(pathOfSaveFile)) {
            createNewSaveFile();
        }
    }

    private void createNewSaveFile() throws CreateSaveFileException {
        try {
            Files.createFile(pathOfSaveFile);
        } catch (IOException e) {
            throw new CreateSaveFileException();
        }
    }

    @Override
    public List<Task> getTasks() throws LoadTasksException {
        Gson gson = getGson();
        List<Task> tasks;
        try {
            BufferedReader reader = Files.newBufferedReader(pathOfSaveFile);
            tasks = gson.fromJson(reader, new TypeToken<List<Task>>() {
            }.getType());
            reader.close();
            return tasks == null ? new ArrayList<>() : tasks;
        } catch (IOException e) {
            throw new LoadTasksException();
        }
    }

    private Gson getGson() {
        RuntimeTypeAdapterFactory<Task> adapter = RuntimeTypeAdapterFactory.of(Task.class)
                .registerSubtype(Task.class).registerSubtype(RecurringTask.class);
        return new GsonBuilder().setPrettyPrinting().registerTypeAdapterFactory(adapter).create();
    }

    @Override
    public void save(Task task) throws SaveTasksException {
        Gson gson = getGson();
        String json = gson.toJson(task);
        try {
            BufferedWriter writer = Files.newBufferedWriter(pathOfSaveFile);
            writer.write(json);
            writer.close();

        } catch (IOException e) {
            throw new SaveTasksException();
        }
    }

    @Override
    public void save(List<Task> tasks) throws SaveTasksException {

        Gson gson = getGson();
        String json = gson.toJson(tasks);
        try {
            BufferedWriter writer = Files.newBufferedWriter(pathOfSaveFile);
            writer.write(json);
            writer.close();

        } catch (IOException e) {
            throw new SaveTasksException();
        }
    }

    @Override
    public void reset() {
        Path p = Paths.get(settings.getPathOfSaveFile());
        try {
            Files.deleteIfExists(p);
        } catch (DirectoryNotEmptyException e) {
            logger.log(Level.WARNING, "Try to delete non-empty directory " + p.getFileName() +
                    " at reset() method in < " + this.getClass().getName() + "> class");
        } catch (SecurityException se) {
            logger.log(Level.WARNING, "Permission error while deleting " + p.getFileName() +
                    " at reset() method in < " + this.getClass().getName() + "> class");
        } catch (IOException ioe) {
            logger.log(Level.WARNING,
                    "Unknow IOException occurs while deleting " + p.getFileName() +
                            "at reset() method in <" + this.getClass().getName() + "> class");
        }
        initialize();
    }

    protected Path getFilePath() {
        return this.pathOfSaveFile;
    }

    private static class CreateSaveFileException extends RuntimeException {
    }

    private static class LoadTasksException extends RuntimeException {
    }

    private static class SaveTasksException extends RuntimeException {
    }
}
