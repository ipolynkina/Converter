package ru.ipolynkina.converter.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ru.ipolynkina.converter.beans.FileFormat;
import ru.ipolynkina.converter.beans.Language;
import ru.ipolynkina.converter.beans.Property;
import ru.ipolynkina.converter.converters.Converter;
import ru.ipolynkina.converter.converters.ConverterStrategy;
import ru.ipolynkina.converter.dialogs.Dialog;
import ru.ipolynkina.converter.start.Main;

import java.io.File;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private static final Logger LOGGER = LogManager.getLogger(MainController.class.getSimpleName());

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
                LOGGER.info("set locale: " + resourceBundle.getLocale());
            }
        });

        allFormats = Main.getContext().getBean(FileFormat.class);
        FileFormat inputFormat = new FileFormat(allFormats.getFormats());
        boxInputFormat.getItems().setAll(inputFormat.getFormats());
        boxInputFormat.setValue(inputFormat.getFormatByIndex(0));

        FileFormat outputFormat = new FileFormat(allFormats.getFormatsWithout(boxInputFormat.getValue()));
        boxOutputFormat.getItems().setAll(outputFormat.getFormats());
        boxOutputFormat.setValue(outputFormat.getFormatByIndex(0));
        boxOutputFormat.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            LOGGER.info("format out: select value = " + boxOutputFormat.getValue());
        });

        boxInputFormat.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if(!oldValue.equals(newValue)) {
                outputFormat.clear();
                outputFormat.addAllFormats(allFormats.getFormatsWithout(boxInputFormat.getValue()));
                boxOutputFormat.getItems().setAll(outputFormat.getFormats());
                boxOutputFormat.setValue(outputFormat.getFormatByIndex(0));
                fileIn = null;
                txtDirectoryIn.setText(resourceBundle.getString("txt_directory_in"));
                LOGGER.info("format in: select value = " + boxInputFormat.getValue());
                LOGGER.info("format out: select value = " + boxOutputFormat.getValue());
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
                LOGGER.info("file in: select file = " + fileIn.getName());
            } else {
                fileIn = null;
                txtDirectoryIn.setText(resourceBundle.getString("txt_directory_in"));
                LOGGER.info("file in: select file = null");
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
                LOGGER.info("file out: select file = " + fileOut.getName());
            } else {
                fileOut = null;
                txtDirectoryOut.setText(resourceBundle.getString("txt_directory_out"));
                LOGGER.info("file out: select file = null");
            }
        });

        btnConvert.setOnAction(event -> {
            Dialog dialog = new Dialog();
            try {
                if(fileIn != null && fileOut != null) {
                    Converter converter = new ConverterStrategy().choiceOfConverterStrategy(
                            boxInputFormat.getValue(), boxOutputFormat.getValue(), fileIn, fileOut);
                    converter.convert();
                    dialog.showOkDialog(resourceBundle.getString("ok_dialog_title"),
                            resourceBundle.getString("ok_dialog_header"),
                            resourceBundle.getString("ok_dialog_context"));
                    LOGGER.info("conversion was successful");
                }

                if(fileIn == null) {
                    dialog.showErrorDialog(resourceBundle.getString("error_dialog_title"),
                            resourceBundle.getString("error_header_for_file"),
                            resourceBundle.getString("error_context_for_file"));
                    LOGGER.info("show error dialog: file in not found");
                }

                if(fileOut == null) {
                    dialog.showErrorDialog(resourceBundle.getString("error_dialog_title"),
                            resourceBundle.getString("error_header_for_directory"),
                            resourceBundle.getString("error_context_for_directory"));
                    LOGGER.info("show error dialog: file out not found");
                }
            } catch (Exception exc) {
                dialog.showExceptionDialog(resourceBundle.getString("error_dialog_title"),
                        resourceBundle.getString("exception_title"),
                        resourceBundle.getString("exception_context"),
                        exc.toString());
                LOGGER.warn(exc.toString());
                exc.printStackTrace();
            }
        });
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
