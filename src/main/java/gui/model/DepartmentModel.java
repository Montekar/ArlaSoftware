package gui.model;

import be.Department;
import be.Screen;
import bll.DepartmentManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class DepartmentModel {
    private ObservableList<Department> departments;
    private ObservableList<Screen> departmentScreens;

    private final DepartmentManager departmentManager;

    public DepartmentModel() {
        departmentManager = new DepartmentManager();
        departments = FXCollections.observableArrayList(new ArrayList<>());
    }

    public void createDepartment(String name) {
        departmentManager.createDepartment(name);
        updateDepartments();
    }

    public void editDepartment(int departmentID, String name) {
        departmentManager.editDepartment(departmentID, name);
        updateDepartments();
    }

    public void deleteDepartment(int departmentID) {
        departmentManager.deleteDepartment(departmentID);
        updateDepartments();
    }

    public List<Department> getAllDepartments() {
        return departmentManager.getAllDepartments();
    }

    public Department getDepartment(int departmentID) {
        return departmentManager.getDepartment(departmentID);
    }

    public ObservableList<Department> getDepartments() {
        return departments;
    }

    public void updateDepartmentScreens() {
        departmentScreens.clear();
        departmentScreens.addAll(new ArrayList<>()); //TODO
    }

    public void updateDepartments() {
        departments.clear();
        departments.addAll(departmentManager.getAllDepartments());
    }
}
