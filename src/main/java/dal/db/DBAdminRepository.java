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
    public void report(String departmentName, String title, String description) {
        try (Connection con = connection.getConnection()) {
            String sql = "INSERT INTO Report Values(?,?,?)";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, departmentName);
            statement.setString(2, title);
            statement.setString(3, description);
            statement.execute();
        } catch (SQLException ex) {
            errorHandler.errorDevelopmentInfo("Issue while sending a report", ex);
        }
    }
}
