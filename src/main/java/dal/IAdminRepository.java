package dal;

import be.users.Admin;

import java.util.List;

public interface IAdminRepository {
    Admin getAdmin(int adminID);
    List<Admin> getAllAdmins();
    void report(String departmentName, String title, String description);
}
