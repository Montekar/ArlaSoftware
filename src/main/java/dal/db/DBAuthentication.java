package dal.db;

import be.users.Admin;
import be.users.Client;
import be.users.User;
import dal.IAuthentication;
import error.ErrorHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBAuthentication implements IAuthentication {
    private DatabaseConnection connection;
    private ErrorHandler errorHandler;

    public DBAuthentication() {
        connection = new DatabaseConnection();
        errorHandler = new ErrorHandler();
    }

    @Override
    public User getAuthenticatedUser(String username, String password) {
        return authenticateAdmin(username, password) != null ? authenticateAdmin(username, password) : authenticateClient(username, password);
    }

    public Admin authenticateAdmin(String username, String password) {
        try (Connection con = connection.getConnection()) {
            String sql = "SELECT * FROM Admin WHERE Username = ? AND Password = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            if (statement.execute()) {
                ResultSet resultSet = statement.getResultSet();
                if (resultSet.next()) {
                    return new Admin(
                            resultSet.getInt("ID"), resultSet.getString("Username"));
                }
            }
        } catch (SQLException ex) {
            errorHandler.errorDevelopmentInfo("Issue in Dao DB Authentication. ", ex);
        }
        return null;
    }

    public Client authenticateClient(String username, String password) {
        try (Connection con = connection.getConnection()) {
            String sql = "SELECT * FROM Client WHERE Username = ? AND Password = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            if (statement.execute()) {
                ResultSet resultSet = statement.getResultSet();
                if (resultSet.next()) {
                    return new Client(
                            resultSet.getInt("ID"), resultSet.getString("Username"));
                }
            }
        } catch (SQLException ex) {
            errorHandler.errorDevelopmentInfo("Issue in Dao DB Authentication. ", ex);
        }
        return null;
    }
}
