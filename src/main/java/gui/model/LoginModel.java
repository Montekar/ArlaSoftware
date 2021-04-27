package gui.model;

import bll.LoginManager;

public class LoginModel {

    private LoginManager loginManager = new LoginManager();

    public String authenticateUser(String username, String password) {
        return loginManager.authenticateUser(username, password);
    }
}
