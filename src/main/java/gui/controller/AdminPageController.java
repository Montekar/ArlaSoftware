package gui.controller;

import be.Department;
import gui.model.DepartmentModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdminPageController implements Initializable {
    @FXML
    private Button hideButton;

    @FXML
    private Button minMaxButton;
    @FXML
    private ChoiceBox<Department> chooseDepartment;

    private final DepartmentModel departmentModel = new DepartmentModel();
    @FXML
    private FlowPane departmentScreens;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chooseDepartment.setItems(departmentModel.getDepartments());
        chooseDepartment.getSelectionModel().selectFirst();
    }

    public void hideWindow(ActionEvent actionEvent) {
        Stage stage = (Stage) hideButton.getScene().getWindow();
        stage.setIconified(true);
    }

    public void minMaxWindow(ActionEvent actionEvent) {
        Stage stage = (Stage) minMaxButton.getScene().getWindow();
        if (!stage.isMaximized()) {
            stage.setMaximized(true);
        } else {
            stage.setMaximized(false);
        }
    }

    public void closeWindow(ActionEvent actionEvent) {
        Stage stage = (Stage) hideButton.getScene().getWindow();
        stage.close();
        Platform.exit();
        System.exit(0);
    }

    //Load nodes to the flowpane
    public void loadWindows(List<Node> nodes) {
        departmentScreens.getChildren().removeAll();

        for (Node n : nodes) {
            departmentScreens.getChildren().add(n);
        }
    }

    @FXML
    public void openSettings(ActionEvent event) {

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
}
