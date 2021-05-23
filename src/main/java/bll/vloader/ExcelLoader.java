package bll.vloader;

import bll.vloader.IViewLoader;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelLoader implements IViewLoader {

    /*
        This class is responsible for loading an excel file with the .xls extension.
        It loops through the excel sheet and checks if the data is and int or string.
        The data is than added to a line String and displayed as a raw data list.
     */
    @Override
    public Node loadView(String path) {
        ListView listView = new ListView();
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(path));
            HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);

            HSSFSheet sheet = workbook.getSheetAt(0);

            FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
            for (Row row: sheet){
                String line = "";
                for (Cell cell: row){
                    switch (formulaEvaluator.evaluateInCell(cell).getCellType()){
                        case NUMERIC:
                            line += cell.getNumericCellValue() + "\t\t";
                            break;

                        case STRING:
                            line += cell.getStringCellValue() + "\t\t";
                            break;
                    }
                }
                listView.getItems().add(line);
                line = "";
            };
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listView;
    }
}
