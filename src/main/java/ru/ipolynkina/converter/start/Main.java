package ru.ipolynkina.converter.start;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/main.fxml"));

        Parent fxmlMain = fxmlLoader.load();
        primaryStage.setTitle("Converter");
        primaryStage.setMinWidth(450);
        primaryStage.setMinHeight(300);
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(fxmlMain, 450, 300));
        primaryStage.show();
    }
}
