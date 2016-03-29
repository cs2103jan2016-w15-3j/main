package ui.controller;

import data.SettingManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

// @author:  A0133333U

public class Main extends Application {

    private static final String APP_TITLE = "Welcome to QuickTasker!";
    private static final String IMAGE_ICON = "img/home.png";

    private Stage primaryStage;
    // private RootLayoutController rootLayoutController;
    private static final int STAGE_MINIMUM_HEIGHT = 150;
    private static final int STAGE_MINIMUM_WIDTH = 560;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        // Do not remove these 2 lines of comments:
        /*Thread.setDefaultUncaughtExceptionHandler((t, e) -> Platform.runLater(() -> showErrorDialog(t, e)));
        Thread.currentThread().setUncaughtExceptionHandler(this::showErrorDialog);*/
        mainWindow();
    }

    // usage : Platform.runLater(getFxWrapper(yourRunnable));
    public static Runnable getFxWrapper(final Runnable r) {
        return () -> {
            try {
                r.run();
            } catch (Exception e) {
                // call logger.log here to handle thread exception
                System.out.println("Found an exception");
            }
        };
    }

    private void mainWindow() {

        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/fxml/MainWindowView.fxml"));
            AnchorPane pane = loader.load();
            MainWindowController mainWindowController = new MainWindowController();
            SettingManager settings = new SettingManager();
            mainWindowController.setMain(this);
            // mainWindowController.setFeedback(MESSAGE_WELCOME);
            primaryStage.getIcons().add(new Image(IMAGE_ICON));
            Scene scene = new Scene(pane);
            primaryStage.setScene(scene);
            primaryStage.setTitle(APP_TITLE);
            primaryStage.setMinWidth(STAGE_MINIMUM_WIDTH);
            primaryStage.setMinHeight(STAGE_MINIMUM_HEIGHT);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
