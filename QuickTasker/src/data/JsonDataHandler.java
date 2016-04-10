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

public class JsonDataHandler implements DataHandler {
    private static final Logger logger = Logger.getLogger(JsonDataHandler.class.getName());

    private static final String DEFAULT_FILENAME = "tasks.json";
    private static final Path DEFAULT_SAVE_PATH = Paths.get(DEFAULT_FILENAME);
    private SettingManager settings = new SettingManagerImpl();
    private Path pathOfSaveFile;

    public JsonDataHandler() {

        initialize();
    }

    private void initialize() {
        loadSavePathFromSettingManager();
        if (Files.notExists(pathOfSaveFile)) createSaveFileIfNotExist();
    }

    private void loadSavePathFromSettingManager() {
        String p = settings.getPathOfSaveFile();
        pathOfSaveFile = p != null ? Paths.get(p) : DEFAULT_SAVE_PATH;
    }

    private void createSaveFileIfNotExist() throws CreateSaveFileException {
        try  {
            if (pathOfSaveFile.getParent()!=null &&!Files.isRegularFile(pathOfSaveFile))
                Files.createDirectories(pathOfSaveFile.getParent());
           if (!Files.exists(pathOfSaveFile)) Files.createFile(pathOfSaveFile);
        } catch (IOException e) {
            logger.warning(e.toString());
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
                        RuntimeTypeAdapterFactory.of(Task.class).registerSubtype(Task.class)
                                .registerSubtype(RecurringTask.class))).create();
    }

    @Override
    public void save(Task t) throws SaveTasksException {
        loadSavePathFromSettingManager();
        createSaveFileIfNotExist();
        Gson gson = getGson();
        String json = gson.toJson(t);
        try (BufferedWriter writer = Files.newBufferedWriter(pathOfSaveFile)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
            throw new SaveTasksException();
        }
    }

    @Override
    public void save(List<Task> ts) throws SaveTasksException {
       loadSavePathFromSettingManager();
       createSaveFileIfNotExist();
        Gson gson = getGson();
        String json = gson.toJson(ts);
        try (BufferedWriter writer = Files.newBufferedWriter(pathOfSaveFile)) {
            writer.write(json);

        } catch (IOException e) {
            e.printStackTrace();
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

    @Override
    public void setSavePath(String path) {
        this.pathOfSaveFile = Paths.get(path);
        settings.setPathOfSaveFile(path);

    }
}