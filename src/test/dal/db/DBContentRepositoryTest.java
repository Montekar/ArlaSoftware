package dal.db;

import be.View;
import org.junit.jupiter.api.*;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DBContentRepositoryTest {
    private static DatabaseConnection connection = new DatabaseConnection();
    private static int lastDepartmentID = 0;
    private boolean createdContent = false;
    private boolean deleteContent = false;
    private boolean editContent = false;

    // Sets up the test
    @BeforeAll
    static void setUp() {
        try (Connection con = connection.getConnection()) {
            String sql = "INSERT INTO Department Values (? ,?, ?, ?)";
            PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, "Test content");
            statement.setString(2, "Test123");
            statement.setInt(3, 5);
            statement.setInt(4,0);
            statement.execute();

            if (statement.getUpdateCount() > 0) {
                ResultSet resultSet = statement.getGeneratedKeys();
                if(resultSet.next()) {
                    lastDepartmentID = resultSet.getInt(1);
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @AfterAll
    static void cleanUp() {
        try (Connection con = connection.getConnection()) {
            String sql = "DELETE FROM Department WHERE ID = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, lastDepartmentID);
            statement.execute();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @DisplayName("Creating new content")
    @Test
    @Order(1)
    void createContent() {
        try (Connection con = connection.getConnection()) {
            String sql = "INSERT INTO Content Values(?,?,?,?,?,?,?,-1)";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, lastDepartmentID);
            statement.setInt(2, 1 );
            statement.setInt(3, 1);
            statement.setInt(4, 500);
            statement.setInt(5, 500);
            statement.setString(6, "Test Title");
            statement.setString(7, "Test path");
            statement.execute();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        Assertions.assertTrue(createdContent);
    }

    @DisplayName("Retrieving the content")
    @Test
    @Order(2)
    void getContent() {
        View getContent = null;
        try (Connection con = connection.getConnection()) {
            String sql = "Select * From Content WHERE DepartmentID = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, lastDepartmentID);
            if (statement.execute()) {
                ResultSet resultSetView = statement.getResultSet();
                while (resultSetView.next()) {
                        getContent = (new View(
                                resultSetView.getInt("DepartmentID"),
                                resultSetView.getInt("Column"),
                                resultSetView.getInt("Row"),
                                resultSetView.getInt("Width"),
                                resultSetView.getInt("Height"),
                                resultSetView.getString("Path"),
                                resultSetView.getString("Title")
                        ));
                    }}
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        System.out.println(lastDepartmentID + " " + getContent.getId());
        Assertions.assertEquals(lastDepartmentID, getContent.getId());
    }

    @DisplayName("Editing content")
    @Test
    @Order(3)
    void editContent() {
        try (Connection con = connection.getConnection()) {
            String sql = "UPDATE Content SET [Column] = ?, Row = ?, Width = ?, Height = ?, Title = ?, Path = ? WHERE DepartmentID = ? AND [Column] = ? AND Row = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, 2);
            statement.setInt(2, 2);
            statement.setInt(3, 300);
            statement.setInt(4, 300);
            statement.setString(5, "Edited Title");
            statement.setString(6, "Edited Path");
            statement.setInt(7, lastDepartmentID);
            statement.setInt(8, 1);
            statement.setInt(9, 1);
            if (statement.execute()){
                editContent = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        Assertions.assertTrue(editContent);
    }

    @DisplayName("Deleting content")
    @Test
    @Order(4)
    void deleteContent() { try (Connection con = connection.getConnection()) {
        String sql = "DELETE FROM Content WHERE DepartmentID = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, lastDepartmentID);
        if(statement.execute()){
            deleteContent = true;
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    Assertions.assertTrue(deleteContent);
    }
}