package bll;

import be.users.User;
import bll.helper.HashingHelper;
import bll.helper.IHashingHelper;
import dal.IAuthenticationRepository;
import dal.db.DBAuthenticationRepository;

public class AuthenticationManager {
    private final IAuthenticationRepository authentication;
    IHashingHelper hash;

    public AuthenticationManager() {
        authentication = new DBAuthenticationRepository();
        hash = new HashingHelper();
    }

    public User getAuthenticatedUser(String username, String password) {
        return authentication.getAuthenticatedUser(username, hash.hashPassword(password));
    }
}
