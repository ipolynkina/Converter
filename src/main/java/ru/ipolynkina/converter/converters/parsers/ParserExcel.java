package ru.ipolynkina.converter.converters.parsers;

import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;

public abstract class ParserExcel extends Parser {

    public abstract Workbook getWorkbook(FileInputStream fis) throws IOException;

    public ParserExcel(File inputFile) {
        super(inputFile);
    }

    @Override
    public void parse() throws Exception {
        FileInputStream fis = new FileInputStream(getInputFile().getAbsoluteFile());
        Workbook wb = getWorkbook(fis);

        int indexRow = 1;
        while(nextRowExist(wb, indexRow)) {
            readRow(wb, indexRow);
            ++indexRow;
        }

        fis.close();
        wb.close();
    }

    private void readRow(Workbook wb, int indexRow) {
        LinkedHashMap<String, String> property = new LinkedHashMap<>();
        int indexCell = 0;
        while(true) {
            try {
                String title = readData(wb, 0, indexCell);
                String value;
                try {
                    value = readData(wb, indexRow, indexCell);
                } catch (NullPointerException exc) {
                    value = ""; // ignore it. no value
                }
                property.put(title, value);
                ++indexCell;
            } catch (NullPointerException exc) {
                break; // ignore it. end of row
            }
        }
        addProperty(property);
    }

    private boolean nextRowExist(Workbook wb, int indexRow) {
        try {
            readData(wb, indexRow, 0);
            return true;
        } catch (NullPointerException exc) {
            return false;
        }
    }

    private String readData(Workbook wb, int indexRow, int indexCell) {
        String data;
        try {
            data = wb.getSheetAt(0). getRow(indexRow).getCell(indexCell).getStringCellValue();
        } catch(IllegalStateException exc) {
            data = String.valueOf(wb.getSheetAt(0).getRow(indexRow).getCell(indexCell).getNumericCellValue());
        }
        return data;
    }
}
