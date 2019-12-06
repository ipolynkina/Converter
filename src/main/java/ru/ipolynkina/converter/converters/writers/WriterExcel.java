package ru.ipolynkina.converter.converters.writers;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class WriterExcel extends Writer {

    public abstract Workbook getWorkbook();

    public WriterExcel(File outputFile) {
        super(outputFile);
    }

    @Override
    public void write(List<LinkedHashMap<String, String>> properties) throws Exception {
        Workbook wb = getWorkbook();
        wb.createSheet();

        Map<String, Integer> titles = new HashMap<>();
        writeData(wb, properties, titles);
        writeTitles(wb, titles);

        FileOutputStream fos = new FileOutputStream(getOutputFile().getAbsoluteFile());

        wb.write(fos);
        fos.close();
        wb.close();
    }

    private void writeData(Workbook wb, List<LinkedHashMap<String, String>> properties, Map<String, Integer> titles) {
        int indexRow = 1;
        int indexTitle = 0;

        for(Map<String, String> property : properties) {
            Row row = wb.getSheetAt(0).createRow(indexRow);
            Cell cell;

            for(Map.Entry<String, String> entry : property.entrySet()) {
                if(!titles.containsKey(entry.getKey())) {
                    titles.put(entry.getKey(), indexTitle);
                    ++indexTitle;
                }
                cell = row.createCell(titles.get(entry.getKey()));
                cell.setCellValue(entry.getValue());
            }
            ++indexRow;
        }
    }

    private void writeTitles(Workbook wb, Map<String, Integer> titles) {
        Row row = wb.getSheetAt(0).createRow(0);
        for(Map.Entry<String, Integer> title : titles.entrySet()) {
            Cell cell = row.createCell(title.getValue());
            cell.setCellValue(title.getKey());
        }
    }
}
