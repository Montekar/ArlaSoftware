package dal.db;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;

class DatabaseConnectionTest {
    private static Connection connection;
    private static DatabaseConnection databaseConnection;

    // Sets up the database connection
    @BeforeAll
    public static void setUp() {
        databaseConnection = new DatabaseConnection();
        try {
            connection = new DatabaseConnection().getConnection();
        }catch (SQLServerException sqlServerException){
            sqlServerException.printStackTrace();
        }
    }

    // Cleans the database connection
    @AfterAll
    public static void tearDown() {
        try{
            connection.close();
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    // Test checks if the database is connected
    @DisplayName("Test of the connection")
    @Test
    void getConnection() {
        try{
            Assertions.assertTrue(!databaseConnection.getConnection().isClosed());
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
}