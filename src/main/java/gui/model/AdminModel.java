package gui.model;

import be.users.Admin;
import bll.AdminManager;

import java.util.List;

public class AdminModel {
    private final AdminManager adminManager;

    public AdminModel() {
        adminManager = new AdminManager();
    }

    public Admin getAdmin(int adminID) {
        return adminManager.getAdmin(adminID);
    }

    public List<Admin> getAllAdmins() {
        return adminManager.getAllAdmins();
    }
}
