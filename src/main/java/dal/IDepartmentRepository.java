package dal;

import be.users.Department;

import java.util.List;

public interface IDepartmentRepository {
    Department getDepartment(int departmentID);
    List<Department> getAllDepartments();
    int getRefreshTime(int departmentID);

    void editDepartment(int departmentID, String username, int refresh);
    void createDepartment(String username, String password, int refresh);
    void deleteDepartment(int departmentID);
}
