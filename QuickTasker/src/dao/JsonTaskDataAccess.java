package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import model.Task;

public class JsonTaskDataAccess implements TaskDataAccessObject {
    private Path pathOfSaveFile;
    private SettingManager settings = new SettingManager();

    public JsonTaskDataAccess() {
        initialize();
    }

    private void initialize() {
        this.pathOfSaveFile = Paths.get(settings.getPathOfSaveFile());
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
    public List<Task> getTasks() throws GetTasksException {
        try {
            BufferedReader reader = Files.newBufferedReader(pathOfSaveFile);
            Gson gson = new Gson();
            List<Task> tasks = gson.fromJson(reader, new TypeToken<List<Task>>() {}.getType());
            reader.close();
            return tasks;
        } catch (IOException e) {
            throw new GetTasksException();
        }
    }

    @Override
    public void save(Task task) throws SaveTasksException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
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
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
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
        // TODO Auto-generated method stub

    }

    public Path getFilePath() {
        // TODO Auto-generated method stub
        return this.pathOfSaveFile;
    }

    public static class CreateSaveFileException extends RuntimeException {};

    public static class GetTasksException extends RuntimeException {};

    public static class SaveTasksException extends RuntimeException {};
}
