package bll.vloader;

import bll.FileReader;
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
    private FileReader fileReader = new FileReader();

    @Override
    public Node loadView(String path) {
        TableView tableView = new TableView();
        List<String> data = fileReader.loadData(path);
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

}




