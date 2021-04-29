package dal;

import be.users.Admin;

import java.util.List;

public interface IAdminRepository {
    Admin getAdmin(int adminID);
    List<Admin> getAllAdmins();

    void changeClientDepartment(int clientID, int departmentID);
    void createClient(String username, String password, int departmentID);
    void deleteClient(int clientID);
}
