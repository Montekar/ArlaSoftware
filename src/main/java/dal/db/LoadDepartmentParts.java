package dal.db;

import be.View;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.ILoadDepartmentParts;
import dal.db.DatabaseConnection;
import error.ErrorHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoadDepartmentParts implements ILoadDepartmentParts {

    private ArrayList<View> viewArrayList;
    private View view;
    private DatabaseConnection connection;
    private ErrorHandler errorHandler;

    public LoadDepartmentParts() {
        viewArrayList = new ArrayList<>();
        connection = new DatabaseConnection();
        errorHandler = new ErrorHandler();
    }

    @Override
    public ArrayList<View> getScreenParts(int departmentId) {
        try (Connection conn = connection.getConnection()) {
            String sql = "SELECT * From Content WHERE DepartmentID = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, departmentId);
            if (statement.execute()){
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()){
                    view = new View(resultSet.getInt("DepartmentID"),
                            resultSet.getInt("Column"),
                            resultSet.getInt("Row"),
                            resultSet.getString("Path"),
                            resultSet.getString("Title"));
                    viewArrayList.add(view);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return viewArrayList;
    }
}
