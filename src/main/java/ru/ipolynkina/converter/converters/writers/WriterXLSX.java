package ru.ipolynkina.converter.converters.writers;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class WriterXLSX extends Writer {

    public WriterXLSX(File outputFile) {
        super(outputFile);
    }

    @Override
    public void write(List<LinkedHashMap<String, String>> properties) throws Exception {
        FileInputStream fis = new FileInputStream("src/main/resources/temp_xlsx/template.xlsx");
        Workbook wb = new XSSFWorkbook(fis);

        Map<String, Integer> titles = new HashMap<>();
        int indexTitle = 0;

        int indexRow = 1;
        for(Map<String, String> property : properties) {
            Row row = wb.getSheetAt(0).createRow(indexRow);
            Cell cell;

            for(Map.Entry<String, String> entry : property.entrySet()) {
                if(titles.containsKey(entry.getKey())) {
                    cell = row.createCell(titles.get(entry.getKey()));
                    cell.setCellValue(entry.getValue());
                } else {
                    titles.put(entry.getKey(), indexTitle);
                    cell = row.createCell(titles.get(entry.getKey()));
                    cell.setCellValue(entry.getValue());
                    ++indexTitle;
                }
            }
            ++indexRow;
        }

        Row row = wb.getSheetAt(0).createRow(0);
        for(Map.Entry<String, Integer> title : titles.entrySet()) {
            Cell cell = row.createCell(title.getValue());
            cell.setCellValue(title.getKey());
        }

        FileOutputStream fos = new FileOutputStream(getOutputFile().getAbsoluteFile());
        wb.write(fos);
        fos.close();
        wb.close();
        fis.close();
    }
}
