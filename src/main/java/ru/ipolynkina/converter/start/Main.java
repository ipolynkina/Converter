package ru.ipolynkina.converter.start;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.ipolynkina.converter.beans.Language;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    public static void main(String[] args) {
        launch();
    }

    private static ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext("spring/spring.xml");

    @Override
    public void start(Stage primaryStage) throws Exception {

        Language languages = ctx.getBean(Language.class);

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/main.fxml"));
        fxmlLoader.setResources(ResourceBundle.getBundle("bundles.Locale", new Locale(languages.getLanguages().get(0))));
        Parent fxmlMain = fxmlLoader.load();

        primaryStage.setTitle("Converter");
        primaryStage.setMinWidth(450);
        primaryStage.setMinHeight(300);
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(fxmlMain, 450, 300));
        primaryStage.show();
    }

    public static ConfigurableApplicationContext getContext() {
        return ctx;
    }
}
