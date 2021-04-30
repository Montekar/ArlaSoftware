package gui.model;

import be.Department;
import bll.DepartmentManager;

import java.util.List;

public class DepartmentModel {
    private final DepartmentManager departmentManager;

    public DepartmentModel() {
        departmentManager = new DepartmentManager();
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
}
