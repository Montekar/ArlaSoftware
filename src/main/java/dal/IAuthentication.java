package dal;

import be.users.Admin;
import be.users.Client;
import be.users.User;

public interface IAuthentication {
    User getAuthentication(String username, String password);
    Admin authenticateAdmin(String username, String password);
    Client authenticateClient(String username, String password);
}
