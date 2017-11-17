package ru.ipolynkina.converter.converters.parsers;

import ru.ipolynkina.converter.converters.propertys.Property;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class Parser {

    private File inputFile;
    private List<Property> properties;

    public Parser(File inputFile) {
        this.inputFile = inputFile;
        properties = new ArrayList<>();
    }

    public File getInputFile() {
        return inputFile;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void addProperty(Property property) {
        properties.add(property);
    }

    public abstract void parse() throws Exception;
}
