package gui.controller;

import be.users.Department;
import bll.DepartmentManager;
import gui.model.DepartmentModel;
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
    @FXML
    private ChoiceBox<Department> choiceDepartment;

    private final DepartmentModel departmentModel = new DepartmentModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceDepartment.setItems(departmentModel.getDepartments());
        choiceDepartment.getSelectionModel().selectFirst();
    }

    @FXML
    void closeWindow(ActionEvent event) {
        Stage stage = (Stage) deleteDepartmentButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void createDepartment(ActionEvent event) {
        departmentModel.createDepartment(newDepartmentNameField.getText(), newDepartmentPasswordField.getText());
        newDepartmentNameField.setText("");
        newDepartmentNameField.setPromptText("Department created!");
        newDepartmentPasswordField.setText("");

        choiceDepartment.getSelectionModel().selectLast();
    }

    @FXML
    void deleteDepartment(ActionEvent event) {
        departmentModel.deleteDepartment(choiceDepartment.getSelectionModel().getSelectedItem().getId());
        editDepartmentNameField.setPromptText("Department deleted!");

        choiceDepartment.getSelectionModel().selectFirst();
    }

    @FXML
    void editDepartment(ActionEvent event) {
        int selectedIndex = choiceDepartment.getSelectionModel().getSelectedIndex();

        departmentModel.editDepartment
                (choiceDepartment.getSelectionModel().getSelectedItem().getId(), editDepartmentNameField.getText());
        editDepartmentNameField.setText("");
        editDepartmentNameField.setPromptText("Department changed!");

        choiceDepartment.getSelectionModel().select(selectedIndex);


    }
}