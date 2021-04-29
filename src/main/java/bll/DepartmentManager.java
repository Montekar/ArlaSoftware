package bll;

import be.Department;
import dal.IDepartmentRepository;
import dal.db.DBDepartmentRepository;

import java.util.List;

public class DepartmentManager {
    private IDepartmentRepository departmentRepository;

    public DepartmentManager() {
        departmentRepository = new DBDepartmentRepository();
    }

    Department getDepartment(int departmentID) {
        return departmentRepository.getDepartment(departmentID);
    }

    List<Department> getAllDepartments() {
        return departmentRepository.getAllDepartments();
    }

    void createDepartment(String name) {
        departmentRepository.createDepartment(name);
    }

    void editDepartment(int departmentID, String name) {
        departmentRepository.editDepartment(departmentID, name);
    }

    void deleteDepartment(int departmentID) {
        departmentRepository.deleteDepartment(departmentID);
    }
}
