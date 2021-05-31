package gui.controller;

import be.users.Department;
import gui.model.ContentModel;
import gui.model.DepartmentModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminPageController implements Initializable {
    @FXML
    private Label departmentName;
    @FXML
    private ChoiceBox<Department> choiceDepartment;
    @FXML
    private GridPane contentGrid;

    private final DepartmentModel departmentModel;
    private final ContentModel contentModel;

    public AdminPageController() {
        departmentModel = DepartmentModel.getInstance();
        contentModel = ContentModel.getInstance();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceDepartment.setItems(departmentModel.getDepartmentsObservable());
        choiceDepartment.getSelectionModel().selectedItemProperty().addListener((observableValue, department, t1) -> {
            if (t1 != null) {
                departmentName.setText(t1.getUsername());
                contentModel.updateContent(t1.getId());
                loadContent(departmentModel.isAutoResizeEnabled(t1.getId()));
            }
        });
        choiceDepartment.getSelectionModel().selectFirst();
    }

    //Load nodes to the gridpane
    public void loadContent(boolean autoResizeEnabled) {
        contentModel.buildGrid(contentGrid,autoResizeEnabled);
    }

    public void refreshContent(ActionEvent actionEvent) {
        if (choiceDepartment.getSelectionModel().getSelectedItem() != null) {
            boolean autoresizeEnabled = departmentModel.isAutoResizeEnabled(choiceDepartment.getSelectionModel().getSelectedItem().getId());
            loadContent(autoresizeEnabled);
        }
    }

    // Logout and go back to login page
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

    public void openEdit(ActionEvent actionEvent) throws IOException {
        Stage mainStage = (Stage) choiceDepartment.getScene().getWindow();
        Stage stage = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdminEditDepartment.fxml"));
        AdminEditDepartmentController editController = new AdminEditDepartmentController();
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

    public void openContentSetup(ActionEvent actionEvent) throws IOException {
        if (choiceDepartment.getSelectionModel().getSelectedItem() != null) {
            Stage mainStage = (Stage) choiceDepartment.getScene().getWindow();
            Stage stage = new Stage();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdminEditContent.fxml"));
            AdminEditContentController editContentController = new AdminEditContentController(contentGrid, choiceDepartment.getSelectionModel().getSelectedItem().getId());
            loader.setController(editContentController);
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(mainStage);
            stage.show();
        }
    }

    public void openIssueLog(ActionEvent actionEvent) throws IOException {
        if (choiceDepartment.getSelectionModel().getSelectedItem() != null) {
            Stage mainStage = (Stage) choiceDepartment.getScene().getWindow();
            Stage stage = new Stage();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdminReport.fxml"));
            AdminReportController reportController = new AdminReportController(choiceDepartment.getSelectionModel().getSelectedItem().getId());
            loader.setController(reportController);
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(mainStage);
            stage.show();
        }
    }
}
