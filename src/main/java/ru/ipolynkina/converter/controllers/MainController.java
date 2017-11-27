package ru.ipolynkina.converter.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ru.ipolynkina.converter.beans.FileFormat;
import ru.ipolynkina.converter.beans.Language;
import ru.ipolynkina.converter.beans.Property;
import ru.ipolynkina.converter.converters.Converter;
import ru.ipolynkina.converter.converters.ConverterStrategy;
import ru.ipolynkina.converter.start.Main;

import java.io.File;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private ResourceBundle resourceBundle;
    private FileFormat allFormats;
    private File fileIn;
    private File fileOut;

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
                fileIn = null;
                txtDirectoryIn.setText(resourceBundle.getString("txt_directory_in"));
            }
        });

        btnSelectIn.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File(Property.getDirIn()));
            fileChooser.setTitle(resourceBundle.getString("title_open_document"));
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter(boxInputFormat.getValue(), "*." + boxInputFormat.getValue())
            );

            fileIn = fileChooser.showOpenDialog(new Stage());
            if(fileIn != null) {
                txtDirectoryIn.setText(fileIn.getName());
                Property.setDirIn(fileIn.getParent());
            } else {
                fileIn = null;
                txtDirectoryIn.setText(resourceBundle.getString("txt_directory_in"));
            }
        });

        btnSelectOut.setOnAction(event -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setInitialDirectory(new File(Property.getDirOut()));
            directoryChooser.setTitle(resourceBundle.getString("title_open_directory"));
            fileOut = directoryChooser.showDialog(new Stage());
            if(fileOut != null) {
                txtDirectoryOut.setText(fileOut.getName());
                Property.setDirOut(fileOut.getAbsolutePath());
            } else {
                fileOut = null;
                txtDirectoryOut.setText(resourceBundle.getString("txt_directory_out"));
            }
        });

        btnConvert.setOnAction(event -> {
            try {
                if(fileIn != null && fileOut != null) {
                    Converter converter = new ConverterStrategy().choiceOfConverterStrategy(
                            boxInputFormat.getValue(), boxOutputFormat.getValue(), fileIn, fileOut);
                    converter.convert();
                    showOkDialog(resourceBundle.getString("ok_dialog_title"),
                            resourceBundle.getString("ok_dialog_header"),
                            resourceBundle.getString("ok_dialog_context"));
                }

                if(fileIn == null) {
                    showErrorDialog(resourceBundle.getString("error_dialog_title"),
                            resourceBundle.getString("error_header_for_file"),
                            resourceBundle.getString("error_context_for_file"));
                }

                if(fileOut == null) {
                    showErrorDialog(resourceBundle.getString("error_dialog_title"),
                            resourceBundle.getString("error_header_for_directory"),
                            resourceBundle.getString("error_context_for_directory"));
                }
            } catch (Exception exc) {
                showExceptionDialog(resourceBundle.getString("error_dialog_title"),
                        resourceBundle.getString("exception_title"),
                        resourceBundle.getString("exception_context"),
                        exc.toString());
                exc.printStackTrace();
            }
        });
    }

    private void showOkDialog(String title, String header, String context) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(context);
        alert.showAndWait();
    }

    private void showErrorDialog(String title, String header, String context) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(context);
        alert.setContentText(header);
        alert.showAndWait();
    }

    private void showExceptionDialog(String title, String header, String context, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(context);

        TextArea textArea = new TextArea(message);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        GridPane expContent = new GridPane();
        expContent.add(textArea, 0, 0);

        alert.getDialogPane().setExpandableContent(expContent);
        alert.showAndWait();
    }

    private void loadLang(Locale locale) {
        resourceBundle = ResourceBundle.getBundle("bundles.Locale", locale);

        txtLanguage.setText(resourceBundle.getString("txt_language"));
        txtInputFormat.setText(resourceBundle.getString("txt_input_format"));
        txtOutputFormat.setText(resourceBundle.getString("txt_output_format"));

        if(fileIn == null) {
            txtDirectoryIn.setText(resourceBundle.getString("txt_directory_in"));
        }

        if(fileOut == null) {
            txtDirectoryOut.setText(resourceBundle.getString("txt_directory_out"));
        }

        btnSelectIn.setText(resourceBundle.getString("btn_select_in"));
        btnSelectOut.setText(resourceBundle.getString("btn_select_out"));
        btnConvert.setText(resourceBundle.getString("btn_convert"));
    }
}
