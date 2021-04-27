package dal;

public interface IAuthentication {
    public Object getAuthentication(String username, String password);
    boolean authenticateAdmin(String username, String password);
    boolean authenticateUser(String username, String password);
}