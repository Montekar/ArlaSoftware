package gui.model;

import be.Department;
import bll.DepartmentManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class DepartmentModel {
    private ObservableList<Department> departmentsOverview;

    private final DepartmentManager departmentManager;

    public DepartmentModel() {
        departmentManager = new DepartmentManager();

        departmentsOverview = FXCollections.observableArrayList(departmentManager.getAllDepartments());
    }

    public Department getDepartment(int departmentID) {
        return departmentManager.getDepartment(departmentID);
    }

    public List<Department> getAllDepartments() {
        return departmentManager.getAllDepartments();
    }

    public void createDepartment(String name) {
        departmentManager.createDepartment(name);
    }

    public void editDepartment(int departmentID, String name) {
        departmentManager.editDepartment(departmentID, name);
    }

    public void deleteDepartment(int departmentID) {
        departmentManager.deleteDepartment(departmentID);
    }

    public ObservableList<Department> getDepartmentsOverview() {
        return departmentsOverview;
    }
}
