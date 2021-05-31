package dal.db;

import be.users.Admin;
import be.users.Department;
import be.users.User;
import dal.IAuthenticationRepository;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DBAuthenticationRepositoryTest {
    private static DatabaseConnection connection = new DatabaseConnection();
    private IAuthenticationRepository dbAuthenticationRepository = new DBAuthenticationRepository();
    private final static String adminUsername = "AdminTest";
    private final static String adminPassword = "admin123";
    private final static String departmentUsername = "DepartmentTest";
    private final static String departmentPassword = "department123";

    // Sets up the test data for admin and department
    @BeforeAll
    public static void setUp() {
        try(Connection con = connection.getConnection()){
            String sqlAdmin = "INSERT INTO Admin Values(?, ?)";
            String sqlDepartment = "INSERT INTO Department Values(?, ?, ?, ?)";

            PreparedStatement adminStatement = con.prepareStatement(sqlAdmin);
            adminStatement.setString(1,adminUsername);
            adminStatement.setString(2,adminPassword);
            adminStatement.execute();

            PreparedStatement departmentStatement = con.prepareStatement(sqlDepartment);
            departmentStatement.setString(1,departmentUsername);
            departmentStatement.setString(2,departmentPassword);
            departmentStatement.setInt(3,5);
            departmentStatement.setInt(4,0);
            departmentStatement.execute();
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }

    }

    // Cleans up the test data
    @AfterAll
    public static void tearDown() {
        try (Connection con = connection.getConnection()){
            String sqlAdmin = "DELETE FROM Admin WHERE Username = ? AND Password = ?";
            String sqlDepartment = "DELETE FROM Department WHERE Username = ? AND Password = ?";

            PreparedStatement adminStatement = con.prepareStatement(sqlAdmin);
            adminStatement.setString(1,adminUsername);
            adminStatement.setString(2,adminPassword);
            adminStatement.execute();

            PreparedStatement departmentStatement = con.prepareStatement(sqlDepartment);
            departmentStatement.setString(1,departmentUsername);
            departmentStatement.setString(2,departmentPassword);
            departmentStatement.execute();
        }catch (SQLException sqlException){

        }
    }

    // Test checks if the admin can be authenticated
    @DisplayName("Admin Authentication")
    @Test
    void authenticateAdmin() {
        User user = dbAuthenticationRepository.authenticateAdmin(adminUsername,adminPassword);
        Assertions.assertTrue(user instanceof Admin);
    }

    // Test checks if the department can be authenticated
    @DisplayName("Department Authentication")
    @Test
    void authenticateDepartment() {
        User user = dbAuthenticationRepository.authenticateDepartment(departmentUsername,departmentPassword);
        Assertions.assertTrue(user instanceof Department);
    }
}