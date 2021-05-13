package gui.controller;

import be.users.Department;
import be.users.User;
import bll.ContentType;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminPageController implements Initializable {
    @FXML
    private Button logoutButton;
    @FXML
    private Label departmentName;
    @FXML
    private Button hideButton;
    @FXML
    private Button settingsButton;
    @FXML
    private Button minMaxButton;
    @FXML
    private ChoiceBox<Department> choiceDepartment;
    private final DepartmentModel departmentModel;
    private final ContentModel contentModel = ContentModel.getInstance();
    @FXML
    private GridPane contentGrid;

    public AdminPageController() {
        departmentModel = DepartmentModel.getInstance();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceDepartment.setOnAction(this::changeDepartment);
        choiceDepartment.setItems(departmentModel.getDepartmentsObservable());
        departmentName.setText("Dashboard");
    }

    public void hideWindow(ActionEvent actionEvent) {
        Stage stage = (Stage) hideButton.getScene().getWindow();
        stage.setIconified(true);
    }

    public void minMaxWindow(ActionEvent actionEvent) {
        Stage stage = (Stage) minMaxButton.getScene().getWindow();
        stage.setFullScreen(!stage.isFullScreen());
    }

    public void closeWindow(ActionEvent actionEvent) {
        Stage stage = (Stage) hideButton.getScene().getWindow();
        stage.close();
        Platform.exit();
        System.exit(0);
    }

    //Load nodes to the gridpane
    public void loadContent() {
        contentModel.buildGrid(contentGrid);
    }

    @FXML
    public void openSettings(ActionEvent event) throws IOException {
        Stage mainStage = (Stage) settingsButton.getScene().getWindow();

        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/view/SettingsPopUp.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(mainStage);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.show();

    }


    public void openStarbucksDepartment(ActionEvent actionEvent) {
    }

    public void openCocioDepartment(ActionEvent actionEvent) {
    }

    public void openTruckDepartment(ActionEvent actionEvent) {
    }

    public void addImage(ActionEvent actionEvent) {
    }

    public void addTable(ActionEvent actionEvent) {
    }

    public void addChart(ActionEvent actionEvent) {
    }

    public void updateScreen(ActionEvent actionEvent) {
    }

    public void editItem(ActionEvent actionEvent) {
    }

    public void deleteItem(ActionEvent actionEvent) {
    }

    @FXML
    private void changeDepartment(ActionEvent event) {
        departmentName.setText(choiceDepartment.getSelectionModel().getSelectedItem().toString());
        contentModel.updateContent(choiceDepartment.getSelectionModel().getSelectedItem().getId());
        loadContent();
    }

    public void openDashboard(ActionEvent actionEvent) {
        departmentName.setText("Dashboard");
    }

    public void logout(ActionEvent actionEvent)  throws IOException {
        Stage mainStage = (Stage) settingsButton.getScene().getWindow();
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
    void onESCAPE(KeyEvent enter) throws IOException {
        if (enter.getCode().equals(KeyCode.ESCAPE)) {
            Stage stage = (Stage) hideButton.getScene().getWindow();
            stage.close();
            Platform.exit();
            System.exit(0);
        }
    }
}
