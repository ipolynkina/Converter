package ru.ipolynkina.converter.converters;

import ru.ipolynkina.converter.converters.parsers.Parser;
import ru.ipolynkina.converter.converters.parsers.ParserDefault;
import ru.ipolynkina.converter.converters.parsers.ParserXLSX;
import ru.ipolynkina.converter.converters.parsers.ParserXML;
import ru.ipolynkina.converter.converters.writers.Writer;
import ru.ipolynkina.converter.converters.writers.WriterDefault;
import ru.ipolynkina.converter.converters.writers.WriterXLSX;

import java.io.File;

// TODO на 2 метода + имя файла?
public class ConverterStrategy {

    public Converter choiceOfConverterStrategy(String formatIn, String formatOut, File fileIn, File fileOut) throws Exception {
        Parser parser;
        if(formatIn.equals("xml")) parser = new ParserXML(fileIn);
        else if(formatIn.equals("xlsx")) parser = new ParserXLSX(fileIn);
        else parser = new ParserDefault(fileIn);

        String pathOut = fileOut + "\\" + fileIn.getName();
        int dot = pathOut.indexOf('.');
        File file = new File(pathOut.substring(0, dot + 1) + formatOut);

        Writer writer;
        if(formatOut.equals("xlsx")) writer = new WriterXLSX(file);
        else writer = new WriterDefault(file);
        return new Converter(parser, writer);
    }
}
