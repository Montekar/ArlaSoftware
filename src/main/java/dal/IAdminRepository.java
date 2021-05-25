package dal;

import be.users.Admin;

import java.util.List;

public interface IAdminRepository {
    Admin getAdmin(int adminID);
    List<Admin> getAllAdmins();
    void report(int departmentId, String title, String description);
}
