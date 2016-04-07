package ui.controller;

import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXToolbar;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import ui.model.ApplicationColor;

public class ThemeChanger {
    private final Enum DefaultColor = ApplicationColor.RED;


    public void change(String userinput, Scene scene) {
        String colorName = userinput.substring(userinput.indexOf(" ") + 1);
        if (ApplicationColor.hasColor(colorName)) {
            ApplicationColor selectedColor = ApplicationColor.valueOf(colorName.toUpperCase());
            JFXDecorator decorator = (JFXDecorator) scene.lookup(".jfx-decorator");
            JFXToolbar toolbar = (JFXToolbar) scene.lookup(".jfx-tool-bar");
            Label label = (Label) scene.lookup(".icons-badge Label");

            label.setStyle("-fx-text-fill:" + selectedColor.getColorHex() + ";");
            decorator.setStyle("-fx-decorator-color: derive(" + selectedColor.getColorHex() + ",-20%);");
            toolbar.setStyle("-fx-background-color: " + selectedColor.getColorHex() + ";");
        }
    }
}
