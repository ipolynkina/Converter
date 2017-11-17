package ru.ipolynkina.converter.converters.writers;

import ru.ipolynkina.converter.converters.propertys.Property;

import java.io.File;
import java.util.List;

public class WriterDefault extends Writer {

    public WriterDefault(File outputFile) {
        super(outputFile);
    }

    @Override
    public void write(List<Property> properties) throws Exception {
        System.out.println("default writer...");
        System.out.println(properties);
    }
}
