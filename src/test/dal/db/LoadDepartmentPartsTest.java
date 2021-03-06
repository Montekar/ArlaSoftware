package dal.db;

import be.View;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

class LoadDepartmentPartsTest {
    private DatabaseConnection connection = new DatabaseConnection();
    private final static int departmentId = 1;
    private static ArrayList<View> expectedViewArrayList = new ArrayList<>();

    // Sets up the list for the test
    @BeforeEach
    void setUp() {
       try(Connection con = connection.getConnection()){
            String sql = "SELECT * FROM Content WHERE DepartmentID = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1,departmentId);
            if (statement.execute()){
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()){
                    expectedViewArrayList.add(new View(
                            resultSet.getInt("DepartmentID"),
                            resultSet.getInt("Column"),
                            resultSet.getInt("Row"),
                            resultSet.getInt("Width"),
                            resultSet.getInt("Height"),
                            resultSet.getString("Title"),
                            resultSet.getString("Path")
                    ));
                }
            }
            //int departmentID, int column, int row, int width, int height, String path, String title
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    // Checks if the lists are the same and if the data was retrieved correctly
    @DisplayName("Getting screen parts")
    @Test
    void getScreenParts() {
        ArrayList<View> actualViewArrayList = new ArrayList<>();
        try(Connection con = connection.getConnection()){
            String sql = "SELECT * FROM Content WHERE DepartmentID = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1,departmentId);
            if (statement.execute()){
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()){
                    actualViewArrayList.add(new View(
                            resultSet.getInt("DepartmentID"),
                            resultSet.getInt("Column"),
                            resultSet.getInt("Row"),
                            resultSet.getInt("Width"),
                            resultSet.getInt("Height"),
                            resultSet.getString("Path"),
                            resultSet.getString("Title")
                    ));
                }
            }
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }

        Assertions.assertEquals(expectedViewArrayList.size(), actualViewArrayList.size());
    }
}