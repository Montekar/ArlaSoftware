package dal.db;

import be.users.Admin;
import be.users.Client;
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
    public Object getAuthentication(String username, String password) {

        if (authenticateAdmin(username, password)){
            return getAdminWithCredentials(username, password);
        }else if(authenticateUser(username, password)){
            return getClientWithCredentials(username, password);
        }
        return null;
    }

    @Override
    public boolean authenticateAdmin(String username, String password) {
        try (Connection con = connection.getConnection()) {
            String sql = "SELECT Username, Password " +  "FROM Admin WHERE Username = ? AND Password = ?";
            PreparedStatement statement = con.prepareStatement(sql);

            statement.setString(1, username);
            statement.setString(2, password);

            if (statement.execute()) {
                ResultSet resultSet = statement.getResultSet();
                if (resultSet.next()) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            errorHandler.errorDevelopmentInfo("Issue in Dao DB Authentication. ", ex);
        }
        return false;
    }

    @Override
    public boolean authenticateUser(String username, String password) {
        try (Connection con = connection.getConnection()) {
            String sql = "SELECT Username, Password " +  "FROM User WHERE Username = ? AND Password = ?";
            PreparedStatement statement = con.prepareStatement(sql);

            statement.setString(1, username);
            statement.setString(2, password);

            if (statement.execute()) {
                ResultSet resultSet = statement.getResultSet();
                if (resultSet.next()) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            errorHandler.errorDevelopmentInfo("Issue in Dao DB Authentication. ", ex);
        }
        return false;
    }

    public Admin getAdminWithCredentials(String username, String password){
        try (Connection con = connection.getConnection()) {
            String sql = "SELECT * FROM Admin WHERE Username = ? AND Password = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            if (statement.execute()) {
                ResultSet resultSet = statement.getResultSet();
                if (resultSet.next()) {
                    return new Admin(
                            resultSet.getString("Username"),
                            resultSet.getInt("ID"));
                }
            }
        } catch (SQLException ex) {
            errorHandler.errorDevelopmentInfo("Issue in Dao DB Authentication. ", ex);
        }
        return null;
    }

    public Client getClientWithCredentials(String username, String password){
        try (Connection con = connection.getConnection()) {
            String sql = "SELECT * FROM User WHERE Username = ? AND Password = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            if (statement.execute()) {
                ResultSet resultSet = statement.getResultSet();
                if (resultSet.next()) {
                    return new Client(
                            resultSet.getString("Username"),
                            resultSet.getInt("ID"));
                }
            }
        } catch (SQLException ex) {
            errorHandler.errorDevelopmentInfo("Issue in Dao DB Authentication. ", ex);
        }
        return null;
    }
}
