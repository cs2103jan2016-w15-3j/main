package ui.model;

public enum ApplicationColor {
    RED("#f44336"),
    HAPPY("#F44336"),
    PINK("#E91E63"),
    PURPLE("#9C27B0"),
    DEEP_PURPLE("#673AB7"),
    INDIGO("#3F51B5"),
    BLUE("#2196F3"),
    LIGHT_BLUE("#03A9F4"),
    CYAN("#00BCD4"),
    TEAL("#009688"),
    GREEN("#4CAF50"),
    APPLE("#4CAF50"),
    LIGHT_GREEN("#8BC34A"),
    LIME("#CDDC39"),
    YELLOW("#FFEB3B"),
    AMBER("#FFC107"),
    ORANGE("#FF9800"),
    DEEP_ORANGE("#FF5722"),
    BROWN("#795548"),
    GREY("#9E9E9E"),
    BLUE_GREY("#607D8B"),
    BLACK("#000000"),
    DARK("#000000");

    private final String hexCode;

    ApplicationColor(String hexCode) {
        this.hexCode = hexCode;
    }

    public String getHexCode() {
        return this.hexCode;
    }

    public static boolean hasColor(String color) {
        for (ApplicationColor c : values()) {
            if (c.name().equalsIgnoreCase(color)) {
                return true;
            }
        }
        return false;
    }

    public String getColorValue() {
        return this.hexCode;
    }

    // returns default color red if color given is unknown
    public String findColor(String color) {
        for (ApplicationColor c : values()) {
            if (c.name().equalsIgnoreCase(color)) {
                return c.getColorValue();
            }
        }
        return RED.getHexCode();
    }
}
