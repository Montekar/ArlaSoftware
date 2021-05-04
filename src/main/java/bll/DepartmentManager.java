package bll;

import be.users.Department;
import dal.IDepartmentRepository;
import dal.db.DBDepartmentRepository;

import java.util.List;

public class DepartmentManager {
    IDepartmentRepository departmentRepository;

    public DepartmentManager() {
        departmentRepository = new DBDepartmentRepository();
    }

    public Department getDepartment(int departmentID) {
        return departmentRepository.getDepartment(departmentID);
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.getAllDepartments();
    }

    public void editDepartment(int departmentID, String username) {
        departmentRepository.editDepartment(departmentID, username);
    }

    public void createDepartment(String username, String password) {
        departmentRepository.createDepartment(username, password);
    }

    public void deleteDepartment(int departmentID) {
        departmentRepository.deleteDepartment(departmentID);
    }
}
