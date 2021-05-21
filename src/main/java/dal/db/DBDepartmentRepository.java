package dal.db;

import be.users.Admin;
import be.users.Department;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.IDepartmentRepository;
import error.ErrorHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBDepartmentRepository implements IDepartmentRepository {
    private DatabaseConnection connection;
    private ErrorHandler errorHandler;

    public DBDepartmentRepository() {
        connection = new DatabaseConnection();
        errorHandler = new ErrorHandler();
    }

    @Override
    public Department getDepartment(int departmentID) {
        try (Connection con = connection.getConnection()) {
            String sql = "Select * From Department WHERE ID = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, departmentID);
            if (statement.execute()) {
                ResultSet resultSet = statement.getResultSet();
                if (resultSet.next()) {
                    return new Department(
                            resultSet.getInt("ID"),
                            resultSet.getString("Username"));
                }
            }
        } catch (SQLException ex) {
            errorHandler.errorDevelopmentInfo("Issue getting a department", ex);
        }
        return null;
    }

    @Override
    public List<Department> getAllDepartments() {
        List<Department> departments = new ArrayList<>();
        try (Connection con = connection.getConnection()) {
            String sql = "Select * From Department";
            Statement statement = con.createStatement();

            if (statement.execute(sql)) {
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    departments.add(new Department(
                            resultSet.getInt("ID"),
                            resultSet.getString("Username")));
                }
                return departments;
            }
        } catch (SQLException ex) {
            errorHandler.errorDevelopmentInfo("Issue getting all departments", ex);
        }
        return null;
    }

    @Override
    public void editDepartment(int departmentID, String username) {
        try (Connection con = connection.getConnection()) {
            String sql = "UPDATE Department SET Username = ? WHERE ID = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, username);
            statement.setInt(2, departmentID);
            statement.execute();
        } catch (SQLException ex) {
            errorHandler.errorDevelopmentInfo("Issue changing department's name", ex);
        }
    }

    @Override
    public void createDepartment(String username, String password) {
        try (Connection con = connection.getConnection()) {
            String sql = "INSERT INTO Department Values(?,?,?)";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setInt(3, 5);
            statement.execute();
        } catch (SQLException ex) {
            errorHandler.errorDevelopmentInfo("Issue creating a department", ex);
        }
    }

    @Override
    public void deleteDepartment(int departmentID) {
        try (Connection con = connection.getConnection()) {
            String sql = "DELETE FROM Department WHERE ID = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, departmentID);
            statement.execute();
        } catch (SQLException ex) {
            errorHandler.errorDevelopmentInfo("Issue deleting a department", ex);
        }
    }
    @Override
    public int getRefreshTime(int departmentID) {
        try (Connection con = connection.getConnection()) {
            String sql = "SELECT Refresh From Department Where ID = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, departmentID);
            if (statement.execute()) {
                ResultSet resultSet = statement.getResultSet();
                if (resultSet.next()) {
                    return resultSet.getInt("Refresh");
                }
                return -1;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }
}
