package ru.ipolynkina.converter.converters.parsers;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ParserXLSX extends ParserExcel {

    public ParserXLSX(File inputFile) {
        super(inputFile);
    }

    @Override
    public Workbook getWorkbook(FileInputStream fis) throws IOException {
        return new XSSFWorkbook(fis);
    }
}
