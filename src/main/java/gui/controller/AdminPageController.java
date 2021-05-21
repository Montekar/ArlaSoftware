package gui.controller;

import be.View;
import be.users.Department;
import gui.model.ContentModel;
import gui.model.DepartmentModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;

public class AdminPageController implements Initializable {
    @FXML
    private Label departmentName;
    @FXML
    private Button hideButton;
    @FXML
    private ChoiceBox<Department> choiceDepartment;
    private final DepartmentModel departmentModel;
    private final ContentModel contentModel = ContentModel.getInstance();
    @FXML
    private GridPane contentGrid;
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
    @FXML
    private TextField pathField;
    @FXML
    private TextField titleField;
    @FXML
    private TextField columnField;
    @FXML
    private TextField rowField;
    @FXML
    private BorderPane borderPane;

    public AdminPageController() {
        departmentModel = DepartmentModel.getInstance();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceDepartment.setOnAction(this::changeDepartment);
        choiceDepartment.setItems(departmentModel.getDepartmentsObservable());
        choiceDepartment.getSelectionModel().selectFirst();

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
            }
        });
    }

    public void hideWindow(ActionEvent actionEvent) {
        Stage stage = (Stage) hideButton.getScene().getWindow();
        stage.setIconified(true);
    }

    //Load nodes to the gridpane
    public void loadContent() {
        contentModel.buildGrid(contentGrid);
    }

    @FXML
    private void changeDepartment(ActionEvent event) {
        if (choiceDepartment.getSelectionModel().getSelectedItem() != null) {
            departmentName.setText(choiceDepartment.getSelectionModel().getSelectedItem().getUsername() + "");
            contentModel.updateContent(choiceDepartment.getSelectionModel().getSelectedItem().getId());
            loadContent();
        }
    }

    public void logout(ActionEvent actionEvent) throws IOException {
        Stage mainStage = (Stage) choiceDepartment.getScene().getWindow();
        mainStage.close();

        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void onEscape(KeyEvent enter) throws IOException {
        if (enter.getCode().equals(KeyCode.ESCAPE)) {
            Stage stage = (Stage) hideButton.getScene().getWindow();
            stage.close();
            Platform.exit();
            System.exit(0);
        }
    }

    public void refreshContent(ActionEvent actionEvent) {
        loadContent();
    }

    public void selectPath(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(choiceDepartment.getScene().getWindow());
        Path path = Path.of(file.getPath());
        pathField.setText(path.toString());
    }

    public void addContent(ActionEvent actionEvent) {
        if (!titleField.getText().isEmpty() && !pathField.getText().isEmpty() && !columnField.getText().isEmpty() && !rowField.getText().isEmpty() && choiceDepartment.getSelectionModel().getSelectedItem() != null) {
            String title = titleField.getText();
            String path = pathField.getText();
            int col = Integer.parseInt(columnField.getText());
            int row = Integer.parseInt(rowField.getText());
            int departmentID = choiceDepartment.getSelectionModel().getSelectedItem().getId();

            contentModel.createContent(new View(departmentID, col, row, path, title));
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

    public void editContent(ActionEvent actionEvent) {
        if (contentTable.getSelectionModel().getSelectedItem() != null && !titleField.getText().isEmpty() && !pathField.getText().isEmpty() && !columnField.getText().isEmpty() && !rowField.getText().isEmpty() && choiceDepartment.getSelectionModel().getSelectedItem() != null) {
            String title = titleField.getText();
            String path = pathField.getText();
            int col = Integer.parseInt(columnField.getText());
            int row = Integer.parseInt(rowField.getText());
            int departmentID = choiceDepartment.getSelectionModel().getSelectedItem().getId();

            View oldView = contentTable.getSelectionModel().getSelectedItem();
            View newView = new View(departmentID, col, row, path, title);

            contentModel.editContent(oldView, newView);
            contentModel.buildGrid(contentGrid);
        }
    }

    public void openEdit(ActionEvent actionEvent) throws IOException {
        Stage mainStage = (Stage) choiceDepartment.getScene().getWindow();
        Stage stage = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EditDepartment.fxml"));
        EditController editController = new EditController();
        loader.setController(editController);
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(mainStage);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.show();

        editController.choiceDepartment.getSelectionModel().select(choiceDepartment.getSelectionModel().getSelectedIndex());
    }
}
