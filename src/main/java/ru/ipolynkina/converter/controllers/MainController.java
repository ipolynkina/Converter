package ru.ipolynkina.converter.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private ObservableList<String> languages = FXCollections.observableArrayList();
    private ResourceBundle resourceBundle;

    @FXML
    private ComboBox<String> boxLanguage;

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

        languages.addAll("en", "ru");
        boxLanguage.getItems().setAll(languages);
        boxLanguage.setValue(languages.get(0));

        boxLanguage.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if(!oldValue.equals(newValue)) {
                loadLang(new Locale(languages.get(newValue.intValue())));
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
