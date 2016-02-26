package controller;

import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class MainWindowController {
    // Views
    @FXML
    Label label;
    @FXML
    JFXTextField commandBox;

    private Main main;

    public void setMain(Main main) {
        this.main = main;
    }

    public void handleEnterKeyPressed(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {

        }
    }
}
