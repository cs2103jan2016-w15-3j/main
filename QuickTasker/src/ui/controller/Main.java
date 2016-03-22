package ui.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

// @author:  A0133333U

public class Main extends Application {

    private static final String APP_TITLE = "Welcome to QuickTasker!";
    private static final String IMAGE_ICON = "img/home.png";

    private Stage primaryStage;
    // private RootLayoutController rootLayoutController;
    private static final int STAGE_MINIMUM_HEIGHT = 200;
    private static final int STAGE_MINIMUM_WIDTH = 550;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        mainWindow();
    }

    /**
     * Responsible for displaying main window.
     */
    private void mainWindow() {

        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/fxml/MainWindowView.fxml"));
            AnchorPane pane = loader.load();

            MainWindowController mainWindowController = new MainWindowController();
            mainWindowController.setMain(this);
            primaryStage.getIcons().add(new Image(IMAGE_ICON));
            Scene scene = new Scene(pane);
            primaryStage.setScene(scene);
            primaryStage.setTitle(APP_TITLE);
            primaryStage.setMinWidth(STAGE_MINIMUM_WIDTH);
            primaryStage.setMinHeight(STAGE_MINIMUM_HEIGHT);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);

    }
}
