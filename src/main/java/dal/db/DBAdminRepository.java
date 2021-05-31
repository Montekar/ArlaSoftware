package dal.db;

import be.users.Admin;
import dal.IAdminRepository;
import error.ErrorHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBAdminRepository implements IAdminRepository {
    private DatabaseConnection connection;
    private ErrorHandler errorHandler;

    public DBAdminRepository() {
        connection = new DatabaseConnection();
        errorHandler = new ErrorHandler();
    }

    @Override
    public Admin getAdmin(int adminID) {
        try (Connection con = connection.getConnection()) {
            String sql = "Select * From Admin WHERE ID = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, adminID);
            if (statement.execute()) {
                ResultSet resultSet = statement.getResultSet();
                if (resultSet.next()) {
                    return new Admin(
                            resultSet.getInt("ID"),
                            resultSet.getString("Username"));
                }
            }
        } catch (SQLException ex) {
            errorHandler.errorDevelopmentInfo("Issue getting an admin", ex);
        }
        return null;
    }

    /**
     *
     * @param username username assigned.
     * @param password password assigned.
     * @return id of a created admin.
     */
    @Override
    public int createAdmin(String username, String password) {
        try (Connection con = connection.getConnection()) {
            String sql = "INSERT INTO Admin Values(?,?)";
            PreparedStatement statement = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.execute();
                ResultSet rs = statement.getGeneratedKeys();
                if (rs != null && rs.next()) {
                    return rs.getInt(1);
                }
        } catch (SQLException ex) {
            errorHandler.errorDevelopmentInfo("Issue creating an admin", ex);
        }
        return -1;
    }

    @Override
    public boolean deleteAdmin(int adminID) {
        try (Connection con = connection.getConnection()) {
            String sql = "DELETE FROM Admin WHERE ID = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, adminID);
            statement.execute();
            return true;
        } catch (SQLException ex) {
            errorHandler.errorDevelopmentInfo("Issue deleting an admin", ex);
        }
        return false;
    }

    @Override
    public List<Admin> getAllAdmins() {
        List<Admin> admins = new ArrayList<>();
        try (Connection con = connection.getConnection()) {
            String sql = "Select * From Admin";
            Statement statement = con.createStatement();

            if (statement.execute(sql)) {
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    admins.add(new Admin(
                            resultSet.getInt("ID"),
                            resultSet.getString("Username")));
                }
                return admins;
            }
        } catch (SQLException ex) {
            errorHandler.errorDevelopmentInfo("Issue getting all admins", ex);
        }
        return null;
    }

    @Override
    public void report(int departmentID, String title, String description) {
        try (Connection con = connection.getConnection()) {
            String sql = "INSERT INTO Report Values(?,?,?)";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, departmentID);
            statement.setString(2, title);
            statement.setString(3, description);
            statement.execute();
        } catch (SQLException ex) {
            errorHandler.errorDevelopmentInfo("Issue while sending a report", ex);
        }
    }
}
