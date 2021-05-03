package gui.controller;

import be.Department;
import gui.model.DepartmentModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsPopUpController implements Initializable {

    @FXML
    private Text editDepartmentNameField;

    @FXML
    private Button editDepartmentButton;

    @FXML
    private Button deleteDepartmentButton;

    @FXML
    private Text newDepartmentNameField;

    @FXML
    private Button createDepartmentButton;

    @FXML
    private ChoiceBox<Department> chooseDepartment;

    private final DepartmentModel departmentModel = new DepartmentModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chooseDepartment.setItems(departmentModel.getDepartmentsOverview());
        chooseDepartment.getSelectionModel().selectFirst();
    }

    @FXML
    void closeWindow(ActionEvent event) {
        Stage stage = (Stage) deleteDepartmentButton.getScene().getWindow();
        stage.close();
        Platform.exit();
        System.exit(0);
    }

    @FXML
    void createDepartment(ActionEvent event) {

    }

    @FXML
    void deleteDepartment(ActionEvent event) {

    }

    @FXML
    void editDepartment(ActionEvent event) {

    }
}