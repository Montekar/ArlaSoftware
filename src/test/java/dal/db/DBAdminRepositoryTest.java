package dal.db;

import be.users.Admin;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class DBAdminRepositoryTest {
    private DatabaseConnection connection;
    private static final String department = "TestDepartment";
    private static int lastInsertedId = 0;
    private boolean reportSend = false;
    private static Admin admin;
    private static List<Admin> admins = new ArrayList<>();

    // Retrieves the specific and list of admins for comparison
    @BeforeAll
    void setUp() {
        try (Connection con = connection.getConnection()) {
            String sqlAdmin = "SELECT * FROM Admin WHERE ID = ?";
            String sqlAdminList = "SELECT * FROM Admin";
            PreparedStatement statementAdmin = con.prepareStatement(sqlAdmin);
            PreparedStatement statementAdminList = con.prepareStatement(sqlAdminList);

            if (statementAdmin.execute()) {
                ResultSet resultSet = statementAdmin.getResultSet();
                if (resultSet.next()) {
                    admin = new Admin(
                            resultSet.getInt("ID"),
                            resultSet.getString("Username")
                    );
                }
            }

            if (statementAdminList.execute()) {
                ResultSet resultSet = statementAdminList.getResultSet();
                while (resultSet.next()) {
                    admins.add(new Admin(
                            resultSet.getInt("ID"),
                            resultSet.getString("Username")
                    ));
                }
            }

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

        Assertions.assertEquals(admin, adminActualValue);
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
        Assertions.assertEquals(admins, adminsActualValue);
    }

    // Test checks if the report was send
    @DisplayName("Sending an report")
    @Test
    void report() {
        try (Connection conn = connection.getConnection()) {
            String sqlReport = "INSERT INTO Report Values (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sqlReport);
            statement.setString(1, "TestDepartment");
            statement.setString(2, "TitleTest");
            statement.setString(3, "DescriptionTest");
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                lastInsertedId = resultSet.getInt(1);
            }
            reportSend = true;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        Assertions.assertTrue(reportSend);
    }
}