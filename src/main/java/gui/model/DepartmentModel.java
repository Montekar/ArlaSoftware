package gui.model;

import be.users.Department;
import bll.DepartmentManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class DepartmentModel {
    private final DepartmentManager departmentManager;
    private ObservableList<Department> departmentsOverview;

    private static DepartmentModel INSTANCE;

    public static DepartmentModel getInstance(){
        if(INSTANCE == null){
            INSTANCE = new DepartmentModel();
        }
        return INSTANCE;
    }

    private DepartmentModel() {
        departmentManager = new DepartmentManager();
        departmentsOverview = FXCollections.observableArrayList(departmentManager.getAllDepartments());
    }

    public void editDepartment(int departmentID, String username) {
        departmentManager.editDepartment(departmentID, username);
        updateDepartments();
    }

    public void createDepartment(String username, String password) {
        departmentManager.createDepartment(username, password);
        updateDepartments();
    }

    public void deleteDepartment(int departmentID) {
        departmentManager.deleteDepartment(departmentID);
        updateDepartments();
    }

    private void updateDepartments() {
        departmentsOverview.clear();
        departmentsOverview.addAll(departmentManager.getAllDepartments());
        System.out.println(departmentsOverview);
    }

    public Department getDepartment(int departmentID) {
        return departmentManager.getDepartment(departmentID);
    }

    public List<Department> getAllDepartments() {
        return getAllDepartments();
    }

    public ObservableList<Department> getDepartmentsObservable() {
        return departmentsOverview;
    }
}
