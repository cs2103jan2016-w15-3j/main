package data;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.builder.fluent.PropertiesBuilderParameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class SettingManager {
    private static final Path settingLocation = Paths.get("settings.properties");
    private Configuration settings;

    public SettingManager() {

        initiate();
    }

    private void initiate() {
        if (!settingsFileExists()) {
            createDefaultSettings();
        } else if (settingFileIsEmpty()) {
            resetDefaultSettings();
        } else loadSettings();
    }

    public void resetDefaultSettings() throws ResetSettingsException {
        try {
            Files.deleteIfExists(settingLocation);
            createDefaultSettings();
            loadSettings();
        } catch (IOException e) {
            throw new ResetSettingsException();
        }
    }

    public String getPathOfSaveFile() {
        try {
            return settings.getString("saveFileLocation");
        } catch (NullPointerException e) {
            return null;
        }
    }

    public void setPathOfSaveFile(String absolutePathOfSaveFile) {
        settings.setProperty("saveFileLocation", absolutePathOfSaveFile);
    }

    public Configuration getConfigs() {
        return this.settings;
    }

    private boolean settingFileIsEmpty() {
        Configurations cons = new Configurations();
        try {
            Configuration con = cons.properties(new File(settingLocation.getFileName().toString()));
            return con.isEmpty();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean settingsFileExists() {
        return Files.exists(settingLocation);
    }

    private void createDefaultSettings() {

        try (OutputStream outputStream = Files.newOutputStream(settingLocation)) {
            Properties properties = new Properties();
            properties
                    .store(outputStream, "Application Settings");
            loadSettings();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void loadSettings() throws LoadSettingsException {
        Parameters parameters = new Parameters();
        PropertiesBuilderParameters propertiesParams = parameters.properties()
                .setFileName("settings.properties").setEncoding("UTF-8");
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(
                PropertiesConfiguration.class).configure(propertiesParams);
        builder.setAutoSave(true);
        try {
            settings = builder.getConfiguration();
        } catch (ConfigurationException cex) {
            throw new LoadSettingsException();
        }

    }

    public static class LoadSettingsException extends RuntimeException {
    }

    public static class ResetSettingsException extends RuntimeException {
    }
}
