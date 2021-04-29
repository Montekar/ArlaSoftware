package dal;

import be.Department;

import java.util.List;

public interface IDepartmentRepository {
    Department getDepartment(int departmentID);
    List<Department> getAllDepartments();

    void createDepartment(String name);
    void editDepartment(int departmentID, String name);
    void deleteDepartment(int departmentID);
}
