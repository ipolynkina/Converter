package ru.ipolynkina.converter.converters.parsers;

import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public abstract class Parser {

    private File inputFile;
    private List<LinkedHashMap<String, String>> properties;

    public Parser(File inputFile) {
        this.inputFile = inputFile;
        properties = new ArrayList<>();
    }

    public File getInputFile() {
        return inputFile;
    }

    public List<LinkedHashMap<String, String>> getProperties() {
        return properties;
    }

    public void addProperty(LinkedHashMap<String, String> property) {
        properties.add(property);
    }

    public abstract void parse() throws Exception;
}
