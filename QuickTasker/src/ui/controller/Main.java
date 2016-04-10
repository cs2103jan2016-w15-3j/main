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

// @@author A0133333U
public class Main extends Application {

    private static final String APP_TITLE = "Welcome to QuickTasker!";
    private static final String IMAGE_ICON = "img/quickTasker.png";
    // @author A0126077E
    private JFXDecorator decorator;
    private Stage primaryStage;
    private Scene scene;
    private static final int STAGE_MINIMUM_HEIGHT = 150;
    private static final int STAGE_MINIMUM_WIDTH = 560;
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    // @author A0126077E
    @Override
    public void start(Stage primaryStage) {
        // required to generate settings when program starts the first time
        SettingManager settings = new SettingManagerImpl();
        // ======================================================
        this.primaryStage = primaryStage;
        initMainWindow();
    }

    // @author A0126077E
    private void initMainWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/fxml/MainWindowView.fxml"));
            BorderPane mainContainer = loader.load();
            MainWindowController mainWindowController = loader.getController();

            setReferencesToController(mainWindowController);
            configureScene(mainContainer);
            setCssStyles();
            initializePrimaryStage();
        } catch (Exception e) {
            logger.severe("Main window failed to load during init Stage.");
            e.printStackTrace();
        }
    }

    // @author A0126077E
    private void setReferencesToController(MainWindowController mainWindowController) {
        mainWindowController.setMain(this);
        mainWindowController.setStage(primaryStage);
    }

    // @author A0126077E
    private void configureScene(BorderPane mainContainer) {
        decorator = new JFXDecorator(primaryStage, mainContainer);
        scene = new Scene(decorator, 560, 400);
    }

    // @author A0126077E
    private void setCssStyles() {
        // PRECAUSION: fonts.css need to be loaded before other css files to avoid compilation error
        scene.getStylesheets().add(Main.class.getResource("/css/fonts.css").toExternalForm());
        scene.getStylesheets().add(Main.class.getResource("/css/application.css").toExternalForm());
    }
    // @author A0126077E

    private void initializePrimaryStage() {
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.getIcons().add(new Image(IMAGE_ICON));
        primaryStage.setTitle(APP_TITLE);
        primaryStage.setMinWidth(STAGE_MINIMUM_WIDTH);
        primaryStage.setMinHeight(STAGE_MINIMUM_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // @author A0126077E
    public Scene getScene() {
        return this.scene;
    }

    // @author A0126077E
    public JFXDecorator getDecorator() {
        return decorator;
    }

    // @author A0126077E
    public static void main(String[] args) {
        launch(args);
    }
}
