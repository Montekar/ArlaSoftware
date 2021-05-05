package bll;

import be.users.User;
import bll.helper.HashingHelper;
import bll.helper.IHashingHelper;
import dal.IAuthentication;
import dal.db.DBAuthentication;

public class AuthenticationManager {
    private final IAuthentication authentication;
    IHashingHelper hash;

    public AuthenticationManager() {
        authentication = new DBAuthentication();
        hash = new HashingHelper();
    }

    public User getAuthenticatedUser(String username, String password) {
        return authentication.getAuthenticatedUser(username, hash.hashPassword(password));
    }
}
