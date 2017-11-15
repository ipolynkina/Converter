package ru.ipolynkina.converter.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import ru.ipolynkina.converter.beans.FileFormat;
import ru.ipolynkina.converter.beans.Language;
import ru.ipolynkina.converter.start.Main;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private ResourceBundle resourceBundle;
    private FileFormat allFormats;

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
            }
        });
    }

    private void loadLang(Locale locale) {
        resourceBundle = ResourceBundle.getBundle("bundles.Locale", locale);

        txtLanguage.setText(resourceBundle.getString("txt_language"));
        txtInputFormat.setText(resourceBundle.getString("txt_input_format"));
        txtOutputFormat.setText(resourceBundle.getString("txt_output_format"));
        txtDirectoryIn.setText(resourceBundle.getString("txt_directory_in"));
        txtDirectoryOut.setText(resourceBundle.getString("txt_directory_out"));

        btnSelectIn.setText(resourceBundle.getString("btn_select_in"));
        btnSelectOut.setText(resourceBundle.getString("btn_select_out"));
        btnConvert.setText(resourceBundle.getString("btn_convert"));
    }
}
