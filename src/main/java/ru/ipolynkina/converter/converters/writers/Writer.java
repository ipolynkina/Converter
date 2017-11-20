package ru.ipolynkina.converter.converters.writers;

import java.io.File;
import java.util.List;
import java.util.LinkedHashMap;

public abstract class Writer {

    private File outputFile;

    public Writer(File outputFile) {
        this.outputFile = outputFile;
    }

    public File getOutputFile() {
        return outputFile;
    }

    public abstract void write(List<LinkedHashMap<String, String>> properties) throws Exception;
}
