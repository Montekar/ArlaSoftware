package bll.vloader;
import bll.FileReader;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.util.Arrays;
import java.util.List;

public class CSVLoader implements IViewLoader {
    private ObservableList<List<String>> observableList = FXCollections.observableArrayList();
    private FileReader fileReader = new FileReader();
    @Override
    public Node loadView(String path) {
        TableView<List<String>> tableView = new TableView();
        List<String> lines = fileReader.loadData(path);
        // Create header
        List<String> headerStrings = Arrays.asList(lines.get(0).split(","));
        for (int i = 0; i < headerStrings.size(); i++) {
            final int index = i;
            TableColumn<List<String>, String> column = new TableColumn();
            column.setText(headerStrings.get(index));

            column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(index)));
            tableView.getColumns().add(column);
        }
        // Set data rows
        for (int i = 1; i < lines.size(); i++) {
            List<String> stringsInLine = Arrays.asList(lines.get(i).split(","));
            observableList.add(stringsInLine);
        }
        tableView.setItems(observableList);
        return tableView;
    }
}




