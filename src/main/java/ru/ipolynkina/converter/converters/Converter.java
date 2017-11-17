package ru.ipolynkina.converter.converters;

import ru.ipolynkina.converter.converters.parsers.Parser;
import ru.ipolynkina.converter.converters.writers.Writer;

public class Converter {

    private Parser parser;
    private Writer writer;

    protected Converter(Parser parser, Writer writer) {
        this.parser = parser;
        this.writer = writer;
    }

    public void convert() throws Exception {
        parser.parse();
        writer.write(parser.getProperties());
    }
}
