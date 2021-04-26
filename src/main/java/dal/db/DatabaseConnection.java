package dal.db;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import error.ErrorHandler;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;

public class DatabaseConnection {
    private static final String PROP_FILE = "src/main/resources/connection.properties";
    private SQLServerDataSource ds = new SQLServerDataSource();
    private ErrorHandler errorHandler = new ErrorHandler();

    public DatabaseConnection() {
        try {

            Properties databaseProperties = new Properties();
            databaseProperties.load(new FileInputStream(PROP_FILE));
            ds.setServerName(databaseProperties.getProperty("Server"));
            ds.setDatabaseName(databaseProperties.getProperty("Database"));
            ds.setUser(databaseProperties.getProperty("User"));
            ds.setPassword(databaseProperties.getProperty("Password"));
        } catch (Exception ex) {
            errorHandler.errorDevelopmentInfo("Issue occurred in DatabaseConnection file", ex);
        }
    }
    public Connection getConnection() throws SQLServerException {
        return ds.getConnection();
    }
}