package data;
//@@author A0126077E

import org.apache.commons.configuration2.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import static org.junit.Assert.*;


public class SettingManagerTest {
    private SettingManager settingManager;
    private static final File file = new File("settings.properties");

    @Before
    public void setUp() throws Exception {
        settingManager = new SettingManager();
    }
    @After
    public void tearDown(){
        settingManager.resetDefaultSettings();
    }

    @Test
    public void settingsFileShouldAlwaysExist() {
        assertTrue(file.exists());
    }

    @Test
    public void ifSettingsFileIsEmptyShouldSetDefault() {
        Configuration settings = null;
        removeEverythingFromSettingsFile();
        settingManager = new SettingManager();
        settings = settingManager.getConfigs();
        assertFalse(settings.isEmpty());
    }

    @Test
    public void canChangeDirectorySettings(){
        String expectedPath = "..desktop";
        settingManager.setPathOfSaveFile(expectedPath);
        assertEquals(expectedPath,settingManager.getPathOfSaveFile());
    }


    private void removeEverythingFromSettingsFile() {
        Configuration settings = settingManager.getConfigs();
        settings.clear();
    }

}
