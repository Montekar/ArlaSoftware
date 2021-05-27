package dal;

import be.users.Department;

import java.util.List;

public interface IDepartmentRepository {
    Department getDepartment(int departmentID);
    List<Department> getAllDepartments();
    int getRefreshTime(int departmentID);
    boolean isAutoResizeEnabled(int departmentID);

    void editDepartment(int departmentID, String username, int refresh, boolean isAutoResizable);
    void createDepartment(String username, String password, int refresh, boolean isAutoResizable);
    void deleteDepartment(int departmentID);
}
