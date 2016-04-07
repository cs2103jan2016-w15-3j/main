package data;

import org.apache.commons.configuration2.Configuration;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SettingManagerTest {
    private SettingManager settingManager;
    private static final File file = new File("settings.properties");

    @Before public void setUp() throws Exception {
        settingManager = new SettingManager();
    }

    @Test public void settingsFileShouldAlwaysExist() {
        assertTrue(file.exists());
    }

    @Test public void ifSettingsFileIsEmptyShouldSetDefault() {
        Configuration settings = null;
        removeEverythingFromSettingsFile();
        settingManager = new SettingManager();
        settings = settingManager.getConfigs();
        assertFalse(settings.isEmpty());
    }

    /**
     * If critical properties does not exist (accidentally removed) , should be
     * set default eg.saveFilePath = save.json
     * <p>
     * more thorough approach : look for customSettings.properties first, if
     * critical ones does not exist, look for the default.properties
     */

    private void removeEverythingFromSettingsFile() {
        Configuration settings = settingManager.getConfigs();
        settings.clear();
    }

}
