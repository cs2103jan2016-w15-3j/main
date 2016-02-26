package controller;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.scene.layout.AnchorPane;
import javafx.collections.ObservableList;

public class Main extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;
        mainWindow();
    }

    // Responsible for displaying main window
    public void mainWindow() {

        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/MainWindowView.fxml"));
            AnchorPane pane = loader.load();

            MainWindowController mainWindowController = new MainWindowController();
            mainWindowController.setMain(this);

            Scene scene = new Scene(pane);

            primaryStage.setScene(scene);
            primaryStage.setTitle("Welcome to QuickTasker!");
            primaryStage.setMinWidth(500);
            primaryStage.setMinHeight(200);
            primaryStage.show();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
