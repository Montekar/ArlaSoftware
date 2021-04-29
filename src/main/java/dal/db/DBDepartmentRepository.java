package dal.db;

import be.Department;
import be.users.Admin;
import be.users.Client;
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
    public Department getDepartment(int departmentId) {
        try (Connection con = connection.getConnection()) {
            String sql = "SELECT * FROM Department WHERE ID = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, departmentId);
            if (statement.execute()) {
                ResultSet resultSet = statement.getResultSet();
                if (resultSet.next()) {
                    return new Department(resultSet.getInt("ID"), resultSet.getString("Name"));
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
                    departments.add(new Department(resultSet.getInt("ID"), resultSet.getString("Name")));
                }
                return departments;
            }
        } catch (SQLException ex) {
            errorHandler.errorDevelopmentInfo("Issue getting all departments", ex);
        }
        return null;
    }

    @Override
    public void createDepartment(String name) {
        try (Connection con = connection.getConnection()) {
            String sql = "INSERT INTO Department Values(?)";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, name);
            statement.execute();
        } catch (SQLException ex) {
            errorHandler.errorDevelopmentInfo("Issue creating a department", ex);
        }
    }

    @Override
    public void editDepartment(int id, String name) {
        try (Connection con = connection.getConnection()) {
            String sql = "UPDATE Department SET Name = ? WHERE ID = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, name);
            statement.setInt(2, id);
            statement.execute();
        } catch (SQLException ex) {
            errorHandler.errorDevelopmentInfo("Issue changing department's name", ex);
        }
    }

    @Override
    public void deleteDepartment(int departmentID) {
        try (Connection con = connection.getConnection()) {
            String sql = "DELETE FROM Department WHERE ID = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1,departmentID);
            statement.execute();
        } catch (SQLException ex) {
            errorHandler.errorDevelopmentInfo("Issue deleting a department", ex);
        }
    }
}
