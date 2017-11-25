package ru.ipolynkina.converter.converters.writers;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class WriterXLSX extends WriterExcel {

    public WriterXLSX(File outputFile) {
        super(outputFile);
    }

    @Override
    public String getFileName() {
        return "src/main/resources/excel/template.xlsx";
    }

    @Override
    public Workbook getWorkbook(FileInputStream fis) throws IOException {
        return new XSSFWorkbook(fis);
    }
}
