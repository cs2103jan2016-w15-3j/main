package data;
//@@author A0126077E

import Common.LoadSettingsException;
import Common.ResetSettingsException;
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
import java.util.logging.Logger;

public class SettingManagerImpl implements SettingManager {
    private static final String DEFAULT_FILENAME_OF_SETTING = "settings.properties";
    private static final Path DEFAULT_PATH_OF_SETTING = Paths.get(DEFAULT_FILENAME_OF_SETTING);
    private static final String SETTING_HEADER_TEXT = "Application Settings";
    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final Logger logger = Logger.getLogger(SettingManagerImpl.class.getName());
    private Configuration settings;

    public SettingManagerImpl() {

        initiate();
    }

    private void initiate() {
        if (settingFileDoesNotExist()) createDefaultSettings();
        else if (!settingFileExistButEmpty()) loadSettings();
        else resetDefaultSettings();
    }

    @Override
    public void resetDefaultSettings() throws ResetSettingsException {
        try {
            Files.deleteIfExists(DEFAULT_PATH_OF_SETTING);
            createDefaultSettings();
        } catch (IOException e) {
            throw new ResetSettingsException();
        }
    }

    @Override
    public String getPathOfSaveFile() {
        try {
            return settings.getString(AvailableSettings.SAVE_LOCATION.toString());
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public void setPathOfSaveFile(String absolutePathOfSaveFile) {
        settings.setProperty(AvailableSettings.SAVE_LOCATION.toString(), absolutePathOfSaveFile);
        assert (absolutePathOfSaveFile.equals(settings.getString(AvailableSettings.SAVE_LOCATION.name())));
    }

    @Override
    public Configuration getConfigs() {
        return this.settings;
    }

    private boolean settingFileExistButEmpty() {
        assert Files.exists(DEFAULT_PATH_OF_SETTING);
        Configurations cons = new Configurations();
        try {
            return cons.properties(new File(DEFAULT_FILENAME_OF_SETTING)).isEmpty();
        } catch (ConfigurationException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean settingFileDoesNotExist() {
        return Files.notExists(DEFAULT_PATH_OF_SETTING);
    }

    private void createDefaultSettings() {

        try (OutputStream outputStream = Files.newOutputStream(DEFAULT_PATH_OF_SETTING)) {
            Properties properties = new DefaultSettings().getDefaults();
            properties.store(outputStream, SETTING_HEADER_TEXT);
            loadSettings();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadSettings() throws LoadSettingsException {
        Parameters parameters = new Parameters();
        PropertiesBuilderParameters propertiesParams = parameters.properties()
                .setFileName(DEFAULT_FILENAME_OF_SETTING).setEncoding(DEFAULT_ENCODING);
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(
                PropertiesConfiguration.class).configure(propertiesParams);
        builder.setAutoSave(true);
        try {
            settings = builder.getConfiguration();
        } catch (ConfigurationException cex) {
            throw new LoadSettingsException();
        }
    }
}
