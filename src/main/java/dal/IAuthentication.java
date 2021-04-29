package dal;

import be.users.Admin;
import be.users.Client;
import be.users.User;

public interface IAuthentication {
    User getAuthenticatedUser(String username, String password);
}
