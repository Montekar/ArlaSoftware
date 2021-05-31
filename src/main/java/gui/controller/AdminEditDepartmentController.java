package gui.controller;

import be.users.Department;
import gui.model.DepartmentModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;


import java.net.URL;
import java.util.ResourceBundle;

public class AdminEditDepartmentController implements Initializable {

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
    @FXML
    private CheckBox createCheckResizable,editCheckResizable;

    private final DepartmentModel departmentModel;

    public AdminEditDepartmentController() {
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
                editCheckResizable.setSelected(departmentModel.isAutoResizeEnabled(t1.getId()));
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
                System.out.println(createCheckResizable.isSelected());
                departmentModel.createDepartment(username, password, refreshRate, createCheckResizable.isSelected());
                choiceDepartment.getSelectionModel().selectLast();
                newDepartmentNameField.setText("");
                newDepartmentPasswordField.setText("");
                newRefreshField.setText("5");
                Alert.displayAlert("Created","Content Department Created",(Stage)editCheckResizable.getScene().getWindow());
            }else{
                Alert.displayAlert("Error","Username not set or refresh 0 or less!",(Stage)editCheckResizable.getScene().getWindow());
            }
        }else{
            Alert.displayAlert("Error","Refresh is not set!",(Stage)editCheckResizable.getScene().getWindow());
        }
    }

    @FXML
    void deleteDepartment(ActionEvent event) {
        if (choiceDepartment.getSelectionModel().getSelectedItem() != null) {
            int departmentID = choiceDepartment.getSelectionModel().getSelectedItem().getId();
            departmentModel.deleteDepartment(departmentID);
            choiceDepartment.getSelectionModel().selectFirst();
            Alert.displayAlert("Deleted","Department deleted",(Stage)editCheckResizable.getScene().getWindow());
        }else{
            Alert.displayAlert("Error","Item not selected",(Stage)editCheckResizable.getScene().getWindow());
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

                departmentModel.editDepartment(departmentID, username, refreshTime,editCheckResizable.isSelected());

                choiceDepartment.getSelectionModel().select(selectedIndex);
                Alert.displayAlert("Edited","Department Edited",(Stage)editCheckResizable.getScene().getWindow());
            }else{
                Alert.displayAlert("Error","Username not set or refresh 0 or less!",(Stage)editCheckResizable.getScene().getWindow());
            }
        }else{
            Alert.displayAlert("Error","Refresh is not set or item not selected!",(Stage)editCheckResizable.getScene().getWindow());
        }

    }
}