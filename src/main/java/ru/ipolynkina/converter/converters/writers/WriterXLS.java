package ru.ipolynkina.converter.converters.writers;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class WriterXLS extends WriterExcel {

    public WriterXLS(File outputFile) {
        super(outputFile);
    }

    @Override
    public String getFileName() {
        return "src/main/resources/excel/template.xls";
    }

    @Override
    public Workbook getWorkbook(FileInputStream fis) throws IOException {
        return new HSSFWorkbook(fis);
    }
}
