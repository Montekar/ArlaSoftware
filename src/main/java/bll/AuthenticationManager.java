package bll;

import be.users.User;
import dal.IAuthentication;
import dal.db.DBAuthentication;

public class AuthenticationManager {
    private IAuthentication authentication;

    public AuthenticationManager() { authentication = new DBAuthentication(); }

    public User authenticateUser(String username, String password){
        return authentication.getAuthentication(username, password);
    }
}
