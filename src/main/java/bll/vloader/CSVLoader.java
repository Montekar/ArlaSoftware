package bll.vloader;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.File;
import java.io.FileNotFoundException;;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CSVLoader implements IViewLoader {

    private boolean isFirstLine = true;
    private ObservableList observableList = FXCollections.observableArrayList();

    @Override
    public Node loadView(String path) {
        TableView tableView = new TableView();
        List<String> data = loadData(path);
        for (Object item: data) {
            String currentLine = item.toString();
            String[] itemArray = currentLine.split(",");
            if (isFirstLine){
                for (Object splitItem: itemArray){
                    TableColumn column = new TableColumn();
                    column.setText(splitItem.toString());
                    tableView.getColumns().add(column);
                }
                isFirstLine = false;
            }else{
                observableList.add(currentLine);
            }
        }
        System.out.println(observableList);
        tableView.setItems(observableList);
        return tableView;
    }

    private List<String> loadData(String path) {
        List<String> csvData = new ArrayList<>();
        try {
            Scanner csvScanner = new Scanner(new File(path));
            while (csvScanner.hasNext()) {
                csvData.add(csvScanner.next());
            }
            csvScanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return csvData;
    }
}




