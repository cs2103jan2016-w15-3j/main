package ui.controller;

import com.jfoenix.controls.JFXDecorator;
import data.SettingManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

// @author:  A0133333U

public class Main extends Application {

    private static final String APP_TITLE = "Welcome to QuickTasker!";
    private static final String IMAGE_ICON = "img/home.png";
    private JFXDecorator decorator;
    private Stage primaryStage;
    public static Scene scene;
    // private RootLayoutController rootLayoutController;
    private static final int STAGE_MINIMUM_HEIGHT = 150;
    private static final int STAGE_MINIMUM_WIDTH = 560;

    @Override
    public void start(Stage primaryStage) {
        SettingManager settings = new SettingManager();
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

            }
        };
    }


    private void mainWindow() {

        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/fxml/MainWindowView.fxml"));
            BorderPane mainContainer = loader.load();
            MainWindowController mainWindowController = new MainWindowController();

            mainWindowController.setMain(this);
            mainWindowController.setStage(primaryStage);
            decorator = new JFXDecorator(primaryStage,mainContainer);
            scene = new Scene(decorator, 560, 400);
            scene.getStylesheets().add(Main.class.getResource("/css/fonts.css").toExternalForm());
            scene.getStylesheets()
                    .add(Main.class.getResource("/css/application.css").toExternalForm());

            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.getIcons().add(new Image(IMAGE_ICON));
            primaryStage.setTitle(APP_TITLE);
            primaryStage.setMinWidth(STAGE_MINIMUM_WIDTH);
            primaryStage.setMinHeight(STAGE_MINIMUM_HEIGHT);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void quickShow() {

    }

    public JFXDecorator getDecorator(){
        return decorator;
    }

    public Scene getScene(){
        return this.scene;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
