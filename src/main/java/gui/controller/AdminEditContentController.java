package gui.controller;

import be.View;
import gui.model.ContentModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;

public class AdminEditContentController implements Initializable {
    @FXML
    private TextField pathField, titleField, columnField, rowField, widthField, heightField;
    @FXML
    private TableView<View> contentTable;
    @FXML
    private TableColumn<View, String> contentTitle;
    @FXML
    private TableColumn<View, String> contentPath;
    @FXML
    private TableColumn<View, Integer> contentCol;
    @FXML
    private TableColumn<View, Integer> contentRow;

    private final int departmentID;
    private final GridPane contentGrid;

    private final ContentModel contentModel;

    public AdminEditContentController(GridPane contentGrid, int departmentID) {
        this.contentGrid = contentGrid;
        this.departmentID = departmentID;
        contentModel = ContentModel.getInstance();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contentTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        contentPath.setCellValueFactory(new PropertyValueFactory<>("path"));
        contentCol.setCellValueFactory(new PropertyValueFactory<>("column"));
        contentRow.setCellValueFactory(new PropertyValueFactory<>("row"));
        contentTable.setItems(contentModel.getContentOverview());

        contentTable.getSelectionModel().selectedItemProperty().addListener((observableValue, view, t1) -> {
            if (t1 != null) {
                pathField.setText(t1.getPath());
                titleField.setText(t1.getTitle());
                columnField.setText(t1.getColumn() + "");
                rowField.setText(t1.getRow() + "");
                widthField.setText(t1.getWidth()+"");
                heightField.setText(t1.getHeight()+"");
            }
        });
        setNumberListener(columnField);
        setNumberListener(rowField);
        setNumberListener(widthField);
        setNumberListener(heightField);
    }

    public void selectPath(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(contentTable.getScene().getWindow());
        if (file != null) {
            Path path = Path.of(file.getPath());
            pathField.setText(path.toString());
        }
    }

    public void addContent(ActionEvent actionEvent) {
        if (!titleField.getText().isEmpty() && !pathField.getText().isEmpty() && !columnField.getText().isEmpty() && !rowField.getText().isEmpty()) {
            String title = titleField.getText();
            String path = pathField.getText();
            int col = Integer.parseInt(columnField.getText());
            int row = Integer.parseInt(rowField.getText());

            contentModel.createContent(new View(departmentID, col, row, 5, 5, path, title));
            contentModel.buildGrid(contentGrid);
        }
    }

    public void editContent(ActionEvent actionEvent) {
        if (contentTable.getSelectionModel().getSelectedItem() != null && !titleField.getText().isEmpty() && !pathField.getText().isEmpty() && !columnField.getText().isEmpty() && !rowField.getText().isEmpty()) {
            String title = titleField.getText();
            String path = pathField.getText();
            int col = Integer.parseInt(columnField.getText());
            int row = Integer.parseInt(rowField.getText());

            View oldView = contentTable.getSelectionModel().getSelectedItem();
            View newView = new View(departmentID, col, row, 5, 5, path, title);

            contentModel.editContent(oldView, newView);
            contentModel.buildGrid(contentGrid);
        }
    }

    public void deleteContent(ActionEvent actionEvent) {
        if (contentTable.getSelectionModel().getSelectedItem() != null) {
            View view = contentTable.getSelectionModel().getSelectedItem();
            contentModel.deleteContent(view);
            contentModel.buildGrid(contentGrid);
        }
    }
    private void setNumberListener(TextField textField){
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    textField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }
}
