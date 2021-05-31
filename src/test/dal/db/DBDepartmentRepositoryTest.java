package dal.db;

import be.users.Department;
import be.users.User;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DBDepartmentRepositoryTest {
    private static DatabaseConnection connection = new DatabaseConnection();
    private static List<Department> departmentList = new ArrayList<>();
    static int lastInsertedId;
    private boolean createdDepartment = false;
    private final String departmentName = "DepartmentTest";
    private final int refreshTime = 5;
    private boolean editedDepartment = false;

    // Sets up the testing and retrieves list of all departments
    @BeforeAll
    static void setUp() {
        try (Connection con = connection.getConnection()) {
            String sql = "SELECT * FROM Department";
            PreparedStatement statement = con.prepareStatement(sql);
            if (statement.execute()) {
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    departmentList.add(new Department(
                            resultSet.getInt("ID"),
                            resultSet.getString("Username")

                    ));
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    // Testing if the all departments get retrieved
    @DisplayName("Get all departments")
    @Test
    @Order(1)
    void getAllDepartments() {
        List<Department> actualDepartmentList = new ArrayList<>();
        try (Connection con = connection.getConnection()) {
            String sql = "SELECT * FROM Department";
            PreparedStatement statement = con.prepareStatement(sql);
            if (statement.execute()) {
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    actualDepartmentList.add(new Department(
                            resultSet.getInt("ID"),
                            resultSet.getString("Username")
                    ));
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        Assertions.assertEquals(departmentList.size(), actualDepartmentList.size());
    }

    // Testing the possibility to create a department
    @DisplayName("Create a department")
    @Test
    @Order(2)
    void createDepartment() {
        try (Connection con = connection.getConnection()) {
            String sql = "INSERT INTO Department Values (? ,?, ?, ?)";
            PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, departmentName);
            statement.setString(2, "Test123");
            statement.setInt(3, refreshTime);
            statement.setInt(4,0);
            statement.execute();

            if (statement.getUpdateCount() > 0) {
                createdDepartment = true;
                ResultSet resultSet = statement.getGeneratedKeys();
                if(resultSet.next()) {
                    lastInsertedId = resultSet.getInt(1);
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        Assertions.assertTrue(createdDepartment);
    }

    // Testing if the department can be retrieved
    @DisplayName("Get department")
    @Test
    @Order(3)
    void getDepartment() {
        Department actualDepartment = new Department(lastInsertedId, departmentName);
        Department expectedDepartment = null;
        try (Connection con = connection.getConnection()) {
            String sql = "SELECT * FROM Department WHERE ID = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1,lastInsertedId);
            if (statement.execute()) {
                ResultSet resultSet = statement.getResultSet();
                if (resultSet.next()) {
                    expectedDepartment = new Department(
                            resultSet.getInt("ID"),
                            resultSet.getString("Username")
                    );
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        assert expectedDepartment != null;
        Assertions.assertEquals(actualDepartment.getUsername(),expectedDepartment.getUsername());
        Assertions.assertEquals(actualDepartment.getId(),expectedDepartment.getId());

    }

    // Test checking the possibility to edit the department
    @DisplayName("Edit the department")
    @Test
    @Order(4)
    void editDepartment() {
        try (Connection con = connection.getConnection()) {
            String sql = "UPDATE Department SET Username = ?, Refresh = ? WHERE ID = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, "EditTest");
            statement.setInt(2, refreshTime);
            statement.setInt(3, lastInsertedId);
            statement.execute();

            if (statement.getUpdateCount() > 0) {
                editedDepartment = true;
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        Assertions.assertTrue(editedDepartment);
    }

    // Test that checks if the refresh time is the same as the one inserted to database
    @DisplayName("Retrieve refresh time")
    @Test
    @Order(5)
    void getRefreshTime() {
        int refreshRetrieved = 0;
        try (Connection con = connection.getConnection()) {
            String sql = "SELECT Refresh From Department WHERE ID = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, lastInsertedId);
            if (statement.execute()) {
                ResultSet resultSet = statement.getResultSet();
                if (resultSet.next()) {
                    refreshRetrieved = resultSet.getInt("Refresh");
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        Assertions.assertEquals(refreshTime, refreshRetrieved);
    }

    // Test that checks if the department can be deleted
    @DisplayName("Deletion of department")
    @Test
    @Order(6)
    void deleteDepartment() {
        boolean deleted = false;
        try (Connection con = connection.getConnection()) {
            String sql = "DELETE FROM Department WHERE ID = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, lastInsertedId);
            statement.execute();

            if (statement.getUpdateCount() > 0) {
                deleted = true;
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        Assertions.assertTrue(deleted);
    }
}