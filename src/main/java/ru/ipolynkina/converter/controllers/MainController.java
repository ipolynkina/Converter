package ru.ipolynkina.converter.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ru.ipolynkina.converter.beans.FileFormat;
import ru.ipolynkina.converter.beans.Language;
import ru.ipolynkina.converter.start.Main;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private ResourceBundle resourceBundle;
    private FileFormat allFormats;
    private Path pathIn;
    private Path pathOut;

    @FXML
    private ComboBox<String> boxLanguage;

    @FXML
    private ComboBox<String> boxInputFormat;

    @FXML
    private ComboBox<String> boxOutputFormat;

    @FXML
    private Label txtLanguage;

    @FXML
    private Label txtInputFormat;

    @FXML
    private Label txtOutputFormat;

    @FXML
    private Label txtDirectoryIn;

    @FXML
    private Label txtDirectoryOut;

    @FXML
    private Button btnSelectIn;

    @FXML
    private Button btnSelectOut;

    @FXML
    private Button btnConvert;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resourceBundle = resources;

        Language languages = Main.getContext().getBean(Language.class);
        boxLanguage.getItems().setAll(languages.getLanguages());
        boxLanguage.setValue(languages.getLanguages().get(0));
        boxLanguage.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if(!oldValue.equals(newValue)) {
                loadLang(new Locale(languages.getLanguageByIndex(newValue.intValue())));
            }
        });

        allFormats = Main.getContext().getBean(FileFormat.class);
        FileFormat inputFormat = new FileFormat(allFormats.getFormats());
        boxInputFormat.getItems().setAll(inputFormat.getFormats());
        boxInputFormat.setValue(inputFormat.getFormatByIndex(0));

        FileFormat outputFormat = new FileFormat(allFormats.excludeFormat(boxInputFormat.getValue()));
        boxOutputFormat.getItems().setAll(outputFormat.getFormats());
        boxOutputFormat.setValue(outputFormat.getFormatByIndex(0));

        boxInputFormat.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if(!oldValue.equals(newValue)) {
                outputFormat.clear();
                outputFormat.addAllFormats(allFormats.excludeFormat(boxInputFormat.getValue()));
                boxOutputFormat.getItems().setAll(outputFormat.getFormats());
                boxOutputFormat.setValue(outputFormat.getFormatByIndex(0));
                txtDirectoryIn.setText(resourceBundle.getString("txt_directory_in"));
            }
        });

        btnSelectIn.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle(resourceBundle.getString("title_open_document"));
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter(boxInputFormat.getValue(), "*." + boxInputFormat.getValue())
            );

            File file = fileChooser.showOpenDialog(new Stage());
            if(file != null) {
                pathIn = Paths.get(file.toString());
                txtDirectoryIn.setText(pathIn.getFileName().toString());
            }
        });

        btnSelectOut.setOnAction(event -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle(resourceBundle.getString("title_open_directory"));
            File file = directoryChooser.showDialog(new Stage());
            if(file != null) {
                pathOut = Paths.get(file.toString());
                txtDirectoryOut.setText(pathOut.getFileName().toString());
            }
        });
    }

    private void loadLang(Locale locale) {
        resourceBundle = ResourceBundle.getBundle("bundles.Locale", locale);

        txtLanguage.setText(resourceBundle.getString("txt_language"));
        txtInputFormat.setText(resourceBundle.getString("txt_input_format"));
        txtOutputFormat.setText(resourceBundle.getString("txt_output_format"));

        if(pathIn == null) {
            txtDirectoryIn.setText(resourceBundle.getString("txt_directory_in"));
        } else {
            txtDirectoryIn.setText(pathIn.getFileName().toString());
        }

        if(pathOut == null) {
            txtDirectoryOut.setText(resourceBundle.getString("txt_directory_out"));
        } else {
            txtDirectoryOut.setText(pathOut.getFileName().toString());
        }


        btnSelectIn.setText(resourceBundle.getString("btn_select_in"));
        btnSelectOut.setText(resourceBundle.getString("btn_select_out"));
        btnConvert.setText(resourceBundle.getString("btn_convert"));
    }
}
