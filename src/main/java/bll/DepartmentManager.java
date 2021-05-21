package bll;

import be.users.Department;
import bll.helper.HashingHelper;
import bll.helper.IHashingHelper;
import dal.IDepartmentRepository;
import dal.db.DBDepartmentRepository;
import java.util.List;

public class DepartmentManager {
    private final IDepartmentRepository departmentRepository;
    private final IHashingHelper hash;

    public DepartmentManager() {
        departmentRepository = new DBDepartmentRepository();
        hash = new HashingHelper();
    }

    public Department getDepartment(int departmentID) {
        return departmentRepository.getDepartment(departmentID);
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.getAllDepartments();
    }

    public void editDepartment(int departmentID, String username, int refresh) {
        departmentRepository.editDepartment(departmentID, username, refresh);
    }

    public void createDepartment(String username, String password, int refresh) {

        departmentRepository.createDepartment(username, hash.hashPassword(password),refresh);
    }

    public int getRefreshTime(int departmentID){
        return departmentRepository.getRefreshTime(departmentID);
    }

    public void deleteDepartment(int departmentID) {
        departmentRepository.deleteDepartment(departmentID);
    }
}
