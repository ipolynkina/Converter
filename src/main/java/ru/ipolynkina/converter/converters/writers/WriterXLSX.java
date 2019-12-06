package ru.ipolynkina.converter.converters.writers;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
public class WriterXLSX extends WriterExcel {

    public WriterXLSX(File outputFile) {
        super(outputFile);
    }

    @Override
    public Workbook getWorkbook() {
        return new XSSFWorkbook();
    }
}
