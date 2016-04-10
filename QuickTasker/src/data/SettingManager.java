package data;

import common.ResetSettingsException;
import org.apache.commons.configuration2.Configuration;

public interface SettingManager {
    void resetDefaultSettings() throws ResetSettingsException;

    String getPathOfSaveFile();

    void setPathOfSaveFile(String absolutePathOfSaveFile);

    Configuration getConfigs();
}
