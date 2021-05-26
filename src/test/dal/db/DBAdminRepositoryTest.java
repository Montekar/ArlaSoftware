package dal.db;

import be.users.Admin;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class DBAdminRepositoryTest {
    private static DatabaseConnection connection = new DatabaseConnection();
    private static final String department = "TestDepartment";
    private static int lastInsertedId = 0;
    private static final int departmentId = 1;
    private boolean reportSend = false;
    private static Admin admin;
    private static List<Admin> admins = new ArrayList<>();

    // Retrieves the specific and list of admins for comparison
    @BeforeAll
    static void setUp() {
        try (Connection con = connection.getConnection()) {
            String sqlAdmin = "SELECT * FROM Admin WHERE ID = ?";
            String sqlAdminList = "SELECT * FROM Admin";
            PreparedStatement statementAdmin = con.prepareStatement(sqlAdmin);
            PreparedStatement statementAdminList = con.prepareStatement(sqlAdminList);
            statementAdmin.setInt(1,departmentId);

            if (statementAdminList.execute()) {
                ResultSet resultSetAdmin = statementAdminList.getResultSet();
                while (resultSetAdmin.next()) {
                    admins.add(new Admin(
                            resultSetAdmin.getInt("ID"),
                            resultSetAdmin.getString("Username")
                    ));
                }
            }

            if (statementAdmin.execute()) {
                ResultSet resultSet = statementAdmin.getResultSet();
                if (resultSet.next()) {
                    admin = new Admin(
                            resultSet.getInt("ID"),
                            resultSet.getString("Username")
                    );
                }
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @AfterAll
    static void cleanUp(){
        try (Connection conn = connection.getConnection()) {
            String sqlDeleteReport = "DELETE FROM Report WHERE ID = ?";
            PreparedStatement statement = conn.prepareStatement(sqlDeleteReport);
            statement.setInt(1, lastInsertedId);
            statement.execute();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    // Test checks if the retrieved admins are equal
    @DisplayName("Get specific Admin")
    @Test
    void getAdmin() {
        Admin adminActualValue = null;
        try (Connection con = connection.getConnection()) {
            String sqlAdmin = "SELECT * FROM Admin WHERE ID = ?";
            PreparedStatement statementAdmin = con.prepareStatement(sqlAdmin);
            statementAdmin.setInt(1, departmentId);

            if (statementAdmin.execute()) {
                ResultSet resultSet = statementAdmin.getResultSet();
                if (resultSet.next()) {
                    adminActualValue = new Admin(
                            resultSet.getInt("ID"),
                            resultSet.getString("Username")
                    );
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        Assertions.assertEquals(admin.getId(), adminActualValue.getId());
    }

    // Test checks if the lists are equals
    @DisplayName("Get All Admins")
    @Test
    void getAllAdmins() {
        List<Admin> adminsActualValue = new ArrayList<>();
        try (Connection con = connection.getConnection()) {
            String sqlAdminList = "SELECT * FROM Admin";
            PreparedStatement statementAdminList = con.prepareStatement(sqlAdminList);

            if (statementAdminList.execute()) {
                ResultSet resultSet = statementAdminList.getResultSet();
                while (resultSet.next()) {
                    adminsActualValue.add(new Admin(
                            resultSet.getInt("ID"),
                            resultSet.getString("Username")
                    ));
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        Assertions.assertEquals(admins.size(), adminsActualValue.size());
    }

    // Test checks if the report was send
    @DisplayName("Sending an report")
    @Test
    void report() {
        try (Connection conn = connection.getConnection()) {
            String sqlReport = "INSERT INTO Report Values (?, ?, ?)";
            PreparedStatement statementReport = conn.prepareStatement(sqlReport);
            statementReport.setInt(1, departmentId);
            statementReport.setString(2, "TitleTest");
            statementReport.setString(3, "DescriptionTest");
            //error
            if (statementReport.execute()){
                ResultSet resultSet = statementReport.getResultSet();
                if (resultSet.next()){
                    lastInsertedId = resultSet.getInt(1);
                }
            }
            reportSend = true;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        Assertions.assertTrue(reportSend);
    }
}