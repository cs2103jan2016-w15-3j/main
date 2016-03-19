package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        mainWindow();
    }

    /**
     * Responsible for displaying main window.
     */
    public void mainWindow() {

        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/MainWindowView.fxml"));
            AnchorPane pane = loader.load();

            MainWindowController mainWindowController = new MainWindowController();
            mainWindowController.setMain(this);

            Scene scene = new Scene(pane);

            primaryStage.setScene(scene);
            primaryStage.setTitle("Welcome to QuickTasker");
            primaryStage.getIcons().add(new Image("file:resources/images/home.png"));
            primaryStage.setMinWidth(500);
            primaryStage.setMinHeight(200);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);

    }
}
