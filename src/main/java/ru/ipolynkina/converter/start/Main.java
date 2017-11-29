package ru.ipolynkina.converter.start;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.ipolynkina.converter.beans.Language;
import ru.ipolynkina.converter.dialogs.Dialog;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    private static ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext("spring/spring.xml");

    public static void main(String[] args) {
        launch();
        ctx.close();
    }

    public static ConfigurableApplicationContext getContext() {
        return ctx;
    }

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

        ContextMenu contextMenu = new ContextMenu();
        MenuItem menuItem = new MenuItem("Info");
        menuItem.setOnAction(event -> {
            new Dialog().showOkDialog("Info", "version: beta",
                    "release: 29.11.2017\n" +
                            "author: Irina Polynkina\n" +
                            "email: irina.polynkina.dex@yandex.ru");
        });
        contextMenu.getItems().add(menuItem);
        fxmlMain.setOnContextMenuRequested(event -> {
            contextMenu.show(primaryStage, event.getScreenX(), event.getScreenY());
        });

        primaryStage.show();
    }
}
