package bll.vloader;

import be.View;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelXLoader implements IViewLoader{
    private ObservableList<List<String>> observableList = FXCollections.observableArrayList();
    private boolean isFirst = true;

    @Override
    public Node loadView(View view,boolean autoResizeEnabled) {
        TableView<List<String>> tableView = new TableView<>();
        try {
            FileInputStream fileInputStream = new FileInputStream(view.getPath());
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()){
                Row row = rowIterator.next();
                if (isFirst){
                    Iterator<Cell> cellHeaderIterator = row.cellIterator();
                    while (cellHeaderIterator.hasNext()){
                        Cell cell = cellHeaderIterator.next();
                        TableColumn<List<String>, String> column = new TableColumn<>();
                        column.setText(cell.getStringCellValue());
                        column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(cell.getColumnIndex())));
                        tableView.getColumns().add(column);
                    }
                    isFirst = false;
                }else{
                    List<String> rowData = new ArrayList<>();
                    Iterator<Cell> cellDataIterator = row.cellIterator();
                    while (cellDataIterator.hasNext()){
                        Cell cell = cellDataIterator.next();
                        switch (cell.getCellType()) {
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
        if (!autoResizeEnabled) {
            tableView.setMinHeight(view.getHeight());
            tableView.setMaxHeight(view.getHeight());
            tableView.setMinWidth(view.getWidth());
            tableView.setMaxWidth(view.getWidth());
        }
        tableView.setItems(observableList);
        return tableView;
    }
}
