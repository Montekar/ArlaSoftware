package dal;

import be.users.Department;

import java.util.List;

public interface IDepartmentRepository {
    Department getDepartment(int departmentID);
    List<Department> getAllDepartments();

    void editDepartment(int departmentID, String username);
    void createDepartment(String username, String password);
    void deleteDepartment(int departmentID);
}
