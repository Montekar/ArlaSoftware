package bll.vloader;
import be.View;
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

    /*
        Method for loading a Csv file and converting it into a TableView.
        In the first loop we create the headers of the table and in the second
        the whole content will be loaded and add to the observable list that we is
        set to table view. After that the whole Table View is returned and displayed.
     */
    @Override
    public Node loadView(View view,boolean autoResizeEnabled) {
        TableView<List<String>> tableView = new TableView();
        List<String> lines = fileReader.loadData(view.getPath());
        List<String> headerStrings = Arrays.asList(lines.get(0).split(","));
        for (int i = 0; i < headerStrings.size(); i++) {
            final int index = i;
            TableColumn<List<String>, String> column = new TableColumn();
            column.setText(headerStrings.get(index));

            column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(index)));
            tableView.getColumns().add(column);
        }
        for (int i = 1; i < lines.size(); i++) {
            List<String> stringsInLine = Arrays.asList(lines.get(i).split(","));
            observableList.add(stringsInLine);
        }
        tableView.setItems(observableList);
        if (!autoResizeEnabled) {
            tableView.setMinHeight(view.getHeight());
            tableView.setMaxHeight(view.getHeight());
            tableView.setMinWidth(view.getWidth());
            tableView.setMaxWidth(view.getWidth());
        }
        return tableView;
    }
}




