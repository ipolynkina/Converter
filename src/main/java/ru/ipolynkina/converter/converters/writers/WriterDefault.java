package ru.ipolynkina.converter.converters.writers;

import java.io.File;
import java.util.List;
import java.util.LinkedHashMap;

public class WriterDefault extends Writer {

    public WriterDefault(File outputFile) {
        super(outputFile);
    }

    @Override
    public void write(List<LinkedHashMap<String, String>> properties) throws Exception {
        System.out.println("default writer...");
        System.out.println(properties);
    }
}
