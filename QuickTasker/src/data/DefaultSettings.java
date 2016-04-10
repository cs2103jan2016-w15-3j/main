package data;

import ui.model.ApplicationColor;

import java.util.Properties;

public class DefaultSettings {
    private Properties properties = new Properties();

    public DefaultSettings() {
        setDefaults();
    }

    public Properties getDefaults() {
        return properties;
    }

    public Properties setDefaults() {

        properties.setProperty(AvailableSettings.APP_THEME.name(), ApplicationColor.RED.name());
        properties.setProperty(AvailableSettings.SAVE_LOCATION.name(), "tasks.json");
        properties.setProperty(AvailableSettings.APP_HEIGHT.name(), "560");
        properties.setProperty(AvailableSettings.APP_WIDTH.name(), "160");

        return properties;
    }

}
