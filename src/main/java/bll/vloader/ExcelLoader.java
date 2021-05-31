package bll.vloader;

import be.View;
import bll.vloader.IViewLoader;
import com.google.common.collect.Iterables;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelLoader implements IViewLoader {
    private ObservableList<List<String>> observableList = FXCollections.observableArrayList();
    private boolean isFirst = true;

    /*
        This class is responsible for loading an excel file with the .xls extension.
        It loops through the excel sheet and checks if the data is and int or string.
        The data is than added to a line String and displayed as a raw data list.
     */
    @Override
    public Node loadView(View view,boolean autoResizeEnabled) {
        TableView<List<String>> tableView = new TableView();
        try {
            FileInputStream fileInputStream = new FileInputStream(view.getPath());
            HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);

            HSSFSheet sheet = workbook.getSheetAt(0);

            FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
            for (Row row: sheet){
                if (isFirst){
                    for (Cell cell : row.getSheet().getRow(0)){
                        TableColumn<List<String>, String> column = new TableColumn();
                        column.setText(cell.getStringCellValue());
                        column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(cell.getColumnIndex())));
                        tableView.getColumns().add(column);
                    }
                    isFirst = false;
                }else{
                    List<String> rowData = new ArrayList<>();
                    for (Cell cell: row){
                        switch (formulaEvaluator.evaluateInCell(cell).getCellType()) {
                            case NUMERIC -> rowData.add(String.valueOf(cell.getNumericCellValue()));
                            case STRING -> rowData.add(cell.getStringCellValue());
                        }
                    }
                    observableList.add(rowData);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        tableView.setItems(observableList);
        return tableView;
    }
}
