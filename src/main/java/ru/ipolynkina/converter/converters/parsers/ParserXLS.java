package ru.ipolynkina.converter.converters.parsers;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ParserXLS extends ParserExcel {

    public ParserXLS(File inputFile) {
        super(inputFile);
    }

    @Override
    public Workbook getWorkbook(FileInputStream fis) throws IOException {
        return new HSSFWorkbook(fis);
    }
}
