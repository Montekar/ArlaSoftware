package gui.model;

import be.users.Department;
import bll.DepartmentManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class DepartmentModel {
    private final DepartmentManager departmentManager;
    private final ObservableList<Department> departmentsOverview;

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

    public void editDepartment(int departmentID, String username, int refresh) {
        departmentManager.editDepartment(departmentID, username, refresh);
        updateDepartments();
    }

    public void createDepartment(String username, String password, int refresh) {
        departmentManager.createDepartment(username, password,refresh);
        updateDepartments();
    }

    public void deleteDepartment(int departmentID) {
        departmentManager.deleteDepartment(departmentID);
        updateDepartments();
    }

    private void updateDepartments() {
        departmentsOverview.clear();
        departmentsOverview.addAll(departmentManager.getAllDepartments());
    }

    public int getRefreshTime(int departmentID){
        return departmentManager.getRefreshTime(departmentID);
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
