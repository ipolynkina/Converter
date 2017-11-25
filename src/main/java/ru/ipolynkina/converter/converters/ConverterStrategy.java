package ru.ipolynkina.converter.converters;

import ru.ipolynkina.converter.converters.parsers.*;
import ru.ipolynkina.converter.converters.writers.*;

import java.io.File;

public class ConverterStrategy {

    public Converter choiceOfConverterStrategy(String formatIn, String formatOut, File fileIn, File fileOut) throws Exception {
        Parser parser = choiceParser(formatIn, fileIn);
        File fileOutAbsoluteDir = createOutputFile(fileIn, fileOut, formatOut);
        Writer writer = choiceWriter(formatOut, fileOutAbsoluteDir);
        return new Converter(parser, writer);
    }

    private Parser choiceParser(String formatIn, File fileIn) {
        switch(formatIn) {
            case "json" : return new ParserJSON(fileIn);
            case "xml"  : return new ParserXML(fileIn);
            case "xls" : return new ParserXLS(fileIn);
            case "xlsx" : return new ParserXLSX(fileIn);
            default: return new ParserDefault(fileIn);
        }
    }

    private Writer choiceWriter(String formatOut, File fileOut) {
        switch(formatOut) {
            case "json" : return new WriterJSON(fileOut);
            case "xml"  : return new WriterXML(fileOut);
            case "xls" : return new WriterXLS(fileOut);
            case "xlsx" : return new WriterXLSX(fileOut);
            default: return new WriterDefault(fileOut);
        }
    }

    private File createOutputFile(File fileIn, File fileOut, String formatOut) {
        String pathOut = fileOut + "\\" + fileIn.getName();
        int dot = pathOut.indexOf('.');
        return new File(pathOut.substring(0, dot + 1) + formatOut);
    }
}
