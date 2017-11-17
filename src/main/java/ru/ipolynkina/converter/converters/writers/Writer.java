package ru.ipolynkina.converter.converters.writers;

import ru.ipolynkina.converter.converters.propertys.Property;

import java.io.File;
import java.util.List;

public abstract class Writer {

    private File outputFile;

    public Writer(File outputFile) {
        this.outputFile = outputFile;
    }

    public File getOutputFile() {
        return outputFile;
    }

    public abstract void write(List<Property> properties) throws Exception;
}
