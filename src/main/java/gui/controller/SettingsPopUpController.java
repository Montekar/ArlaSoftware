package gui.controller;

import bll.DepartmentManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsPopUpController implements Initializable {

    @FXML
    private TextField editDepartmentNameField;
    @FXML
    private TextField newDepartmentNameField;
    @FXML
    private PasswordField newDepartmentPasswordField;
    @FXML
    private Button createDepartmentButton;
    @FXML
    private Button editDepartmentButton;
    @FXML
    private Button deleteDepartmentButton;

    //private final DepartmentModel departmentModel = new DepartmentModel();

    private final DepartmentManager departmentManager = new DepartmentManager();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //chooseDepartment.setItems(departmentModel.getDepartments());
        //chooseDepartment.getSelectionModel().selectFirst();
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
        //departmentModel.createDepartment(newDepartmentNameField.getText());
    }

    @FXML
    void deleteDepartment(ActionEvent event) {
        //departmentModel.deleteDepartment(chooseDepartment.getSelectionModel().getSelectedIndex());
    }

    @FXML
    void editDepartment(ActionEvent event) {

    }
}