package ru.ipolynkina.converter.converters.parsers;

import java.io.File;

public class ParserDefault extends Parser {

    public ParserDefault(File inputFile) {
        super(inputFile);
    }

    @Override
    public void parse() throws Exception {
        System.out.println("default parser");
    }
}
