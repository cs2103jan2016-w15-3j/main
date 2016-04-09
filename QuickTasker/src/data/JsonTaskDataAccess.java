package data;
//@@author A0126077E

import com.fatboyindustrial.gsonjavatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import common.CreateSaveFileException;
import common.LoadTasksException;
import common.SaveTasksException;
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
        pathOfSaveFile = p != null ? Paths.get(p) : DEFAULT_SAVE_PATH;
        if (Files.notExists(pathOfSaveFile)) createNewSaveFile();
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
        try (BufferedReader reader = Files.newBufferedReader(pathOfSaveFile)) {
            tasks = gson.fromJson(reader, new TypeToken<List<Task>>() {}.getType());
            reader.close();
            return tasks != null ? tasks : new ArrayList<>();
        } catch (IOException e) {
            throw new LoadTasksException();
        }
    }

    private Gson getGson() {
        return Converters.registerLocalDateTime(new GsonBuilder().setPrettyPrinting()
                                                                 .registerTypeAdapterFactory(
                                                                         RuntimeTypeAdapterFactory
                                                                                 .of(Task.class)
                                                                                 .registerSubtype(Task.class)
                                                                                 .registerSubtype(
                                                                                         RecurringTask.class)))
                         .create();
    }

    @Override
    public void save(Task t) throws SaveTasksException {
        Gson gson = getGson();
        String json = gson.toJson(t);
        try (BufferedWriter writer = Files.newBufferedWriter(pathOfSaveFile)) {
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            throw new SaveTasksException();
        }
    }

    @Override
    public void save(List<Task> ts) throws SaveTasksException {

        Gson gson = getGson();
        String json = gson.toJson(ts);
        try (BufferedWriter writer = Files.newBufferedWriter(pathOfSaveFile)) {
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
                    " at reset() method in < " + getClass().getName() + "> class");
        } catch (SecurityException se) {
            logger.log(Level.WARNING, "Permission error while deleting " + p.getFileName() +
                    " at reset() method in < " + getClass().getName() + "> class");
        } catch (IOException ioe) {
            logger.log(Level.WARNING, "Unknow IOException occurs while deleting " + p.getFileName() +
                    "at reset() method in <" + getClass().getName() + "> class");
        }
        initialize();
    }

    Path getFilePath() {
        return this.pathOfSaveFile;
    }

    // internal method for unit test
    protected void setSavePath(String path) {
        this.pathOfSaveFile = Paths.get(path);
    }

}