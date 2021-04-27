package bll;

import dal.LoginAuthentication;

public class LoginManager {

    private LoginAuthentication loginAuthentication = new LoginAuthentication();

    public String authenticateUser(String username, String password) {
        return loginAuthentication.authenticateUser(username,password);
    }
}
