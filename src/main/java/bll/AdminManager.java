package bll;

import be.users.Admin;
import dal.IAdminRepository;
import dal.db.DBAdminRepository;

import java.util.List;

public class AdminManager {
    private final IAdminRepository adminRepository;

    public AdminManager() {
        adminRepository = new DBAdminRepository();
    }

    public Admin getAdmin(int adminID) {
        return adminRepository.getAdmin(adminID);
    }

    public List<Admin> getAllAdmins() {
        return adminRepository.getAllAdmins();
    }

    public void report(int departmentID,String title, String description){ adminRepository.report(departmentID, title, description);}

}
