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
    public void changeClientDepartment(int clientID, int departmentID) {
        try (Connection con = connection.getConnection()) {
            String sql = "UPDATE Client SET DepartmentID = ? WHERE ID = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, departmentID);
            statement.setInt(2, clientID);
            statement.execute();
        } catch (SQLException ex) {
            errorHandler.errorDevelopmentInfo("Issue changing client's department", ex);
        }
    }

    @Override
    public void createClient(String username, String password, int departmentID) {
        try (Connection con = connection.getConnection()) {
            String sql = "INSERT INTO Client Values(?,?,?)";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setInt(3, departmentID);
            statement.execute();
        } catch (SQLException ex) {
            errorHandler.errorDevelopmentInfo("Issue registering a client", ex);
        }
    }

    @Override
    public void deleteClient(int clientID) {
        try (Connection con = connection.getConnection()) {
            String sql = "DELETE FROM Client WHERE ID = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1,clientID);
            statement.execute();
        } catch (SQLException ex) {
            errorHandler.errorDevelopmentInfo("Issue deleting a client", ex);
        }
    }
    }
