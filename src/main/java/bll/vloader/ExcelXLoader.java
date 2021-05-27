package bll.vloader;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

public class ExcelXLoader implements IViewLoader{
    @Override
    public Node loadView(String path, int width, int height) {
        ListView listView = new ListView();
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()){
                String line = "";
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()){
                    Cell cell = cellIterator.next();
                    switch (cell.getCellType()){
                        case NUMERIC: line += cell.getNumericCellValue() + "\t\t";
                            break;
                        case STRING: line += cell.getStringCellValue() + "\t\t";
                            break;
                    }
                }
                listView.getItems().add(line);
                line = "";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listView;
    }
}
