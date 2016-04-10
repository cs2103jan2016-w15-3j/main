package ui.controller;

import com.jfoenix.controls.JFXDecorator;
import data.SettingManager;
import data.SettingManagerImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.logging.Logger;

public class Main extends Application {

    private static final String APP_TITLE = "Welcome to QuickTasker!";
    private static final String IMAGE_ICON = "img/quickTasker.png";
    private JFXDecorator decorator;
    private Stage primaryStage;
    private Scene scene;
    private static final int STAGE_MINIMUM_HEIGHT = 150;
    private static final int STAGE_MINIMUM_WIDTH = 560;
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    /**
     * @@author A0126077E.
     */

    @Override
    public void start(Stage primaryStage) {
        SettingManager settings = new SettingManagerImpl();
        this.primaryStage = primaryStage;
        mainWindow();
    }

    public static void main(String[] args) {
        launch(args);
    }

    // @@author A0133333U
    private void mainWindow() {

        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/fxml/MainWindowView.fxml"));
            BorderPane mainContainer = loader.load();
            MainWindowController mainWindowController = loader.getController();
            mainWindowController.setMain(this);
            mainWindowController.setStage(primaryStage);
            decorator = new JFXDecorator(primaryStage, mainContainer);
            scene = new Scene(decorator, 560, 400);
            scene.getStylesheets().add(Main.class.getResource("/css/fonts.css").toExternalForm());
            scene.getStylesheets().add(Main.class.getResource("/css/application.css").toExternalForm());

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

    public JFXDecorator getDecorator() {
        return decorator;
    }

    public Scene getScene() {
        return this.scene;
    }

}
