package gui.controller;

import be.users.Department;
import com.jfoenix.controls.JFXTextField;
import gui.model.DepartmentModel;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ResourceBundle;

public class AdminEditController implements Initializable {

    @FXML
    private TextField editDepartmentNameField;
    @FXML
    private TextField newDepartmentNameField;
    @FXML
    private PasswordField newDepartmentPasswordField;
    @FXML
    private Button deleteDepartmentButton;
    @FXML
    public ChoiceBox<Department> choiceDepartment;
    @FXML
    private TextField editRefreshField, newRefreshField;

    private final DepartmentModel departmentModel;

    public AdminEditController() {
        departmentModel = DepartmentModel.getInstance();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceDepartment.setItems(departmentModel.getDepartmentsObservable());
        editRefreshField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    editRefreshField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        newRefreshField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    newRefreshField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        choiceDepartment.getSelectionModel().selectedItemProperty().addListener((observableValue, department, t1) -> {
            if (t1 != null) {
                editDepartmentNameField.setText(t1.getUsername());
                editRefreshField.setText(departmentModel.getRefreshTime(t1.getId()) + "");
            }
        });
    }

    @FXML
    void closeWindow(ActionEvent event) {
        Stage stage = (Stage) deleteDepartmentButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void createDepartment(ActionEvent event) {
        String username = newDepartmentNameField.getText();
        String password = newDepartmentPasswordField.getText();
        String refresh = newRefreshField.getText();
        if (!refresh.isBlank()) {
            int refreshRate = Integer.parseInt(refresh);
            if (!username.isBlank() && refreshRate > 0) {
                departmentModel.createDepartment(username, password, refreshRate);
                choiceDepartment.getSelectionModel().selectLast();
                newDepartmentNameField.setText("");
                newDepartmentPasswordField.setText("");
                newRefreshField.setText("5");
            }
        }
    }

    @FXML
    void deleteDepartment(ActionEvent event) {
        if (choiceDepartment.getSelectionModel().getSelectedItem() != null) {
            int departmentID = choiceDepartment.getSelectionModel().getSelectedItem().getId();
            departmentModel.deleteDepartment(departmentID);
            choiceDepartment.getSelectionModel().selectFirst();
        }
    }

    @FXML
    void editDepartment(ActionEvent event) {
        String refresh = editRefreshField.getText();
        String username = editDepartmentNameField.getText();
        if (!refresh.isBlank() && choiceDepartment.getSelectionModel().getSelectedItem() != null) {
            int refreshTime = Integer.parseInt(refresh);
            int departmentID = choiceDepartment.getSelectionModel().getSelectedItem().getId();
            if (!username.isBlank() && refreshTime > 0) {
                int selectedIndex = choiceDepartment.getSelectionModel().getSelectedIndex();

                departmentModel.editDepartment(departmentID, username, refreshTime);

                choiceDepartment.getSelectionModel().select(selectedIndex);
            }
        }
    }
    @FXML
    void onESCAPE(KeyEvent enter) throws IOException {
        if (enter.getCode().equals(KeyCode.ESCAPE)) {
            Stage stage = (Stage) newDepartmentNameField.getScene().getWindow();
            stage.close();
            Platform.exit();
            System.exit(0);
        }
    }
}