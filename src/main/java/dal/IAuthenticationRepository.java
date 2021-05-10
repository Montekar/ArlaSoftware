package dal;

import be.users.Admin;
import be.users.Department;
import be.users.User;

public interface IAuthenticationRepository {
    User getAuthenticatedUser(String username, String password);
    Admin authenticateAdmin(String username, String password);
    Department authenticateDepartment(String username, String password);
}
