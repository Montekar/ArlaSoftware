package bll;

import be.Department;
import dal.IDepartmentRepository;
import dal.db.DBDepartmentRepository;

import java.util.List;

public class DepartmentManager {
    private final IDepartmentRepository departmentRepository;

    public DepartmentManager() {
        departmentRepository = new DBDepartmentRepository();
    }

    public Department getDepartment(int departmentID) {
        return departmentRepository.getDepartment(departmentID);
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.getAllDepartments();
    }

    public void createDepartment(String name) {
        departmentRepository.createDepartment(name);
    }

    public void editDepartment(int departmentID, String name) {
        departmentRepository.editDepartment(departmentID, name);
    }

    public void deleteDepartment(int departmentID) {
        departmentRepository.deleteDepartment(departmentID);
    }
}
