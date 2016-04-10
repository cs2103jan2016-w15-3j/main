package ui.controller;

import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXToolbar;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import ui.model.ApplicationColor;

// @author A0126077E
public class ThemeChanger {
    private final Enum DefaultColor = ApplicationColor.RED;

    public void change(String userinput, Scene s) {
        String colorName = userinput.substring(userinput.indexOf(" ") + 1);
        if (!ApplicationColor.hasColor(colorName)) return;
        ApplicationColor selectedColor = ApplicationColor.valueOf(colorName.toUpperCase());
        JFXDecorator decorator = (JFXDecorator) s.lookup(".jfx-decorator");
        JFXToolbar toolbar = (JFXToolbar) s.lookup(".jfx-tool-bar");
        Label label = (Label) s.lookup(".icons-badge Label");
        label.setStyle("-fx-text-fill:" + selectedColor.getColorValue() + ";");
        decorator.setStyle("-fx-decorator-color: derive(" + selectedColor.getColorValue() + ",-20%);");
        toolbar.setStyle("-fx-background-color: " + selectedColor.getColorValue() + ";");
    }
}
