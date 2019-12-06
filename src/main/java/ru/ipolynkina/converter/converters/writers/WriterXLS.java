package ru.ipolynkina.converter.converters.writers;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;

public class WriterXLS extends WriterExcel {

    public WriterXLS(File outputFile) {
        super(outputFile);
    }

    @Override
    public Workbook getWorkbook() {
        return new HSSFWorkbook();
    }
}
