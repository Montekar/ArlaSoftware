package dal.db;

import be.View;
import be.users.Admin;
import dal.IContentRepository;
import error.ErrorHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBContentRepository implements IContentRepository {
    private DatabaseConnection connection;
    private ErrorHandler errorHandler;

    public DBContentRepository() {
        connection = new DatabaseConnection();
        errorHandler = new ErrorHandler();
    }

    @Override
    public List<View> getContent(int departmentID) {
        List<View> content = new ArrayList<>();
        try (Connection con = connection.getConnection()) {
            String sql = "Select * From Content WHERE ID = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, departmentID);
            if (statement.execute()) {
                ResultSet resultSet = statement.getResultSet();
                if (resultSet.next()) {
                    content.add(new View(resultSet.getInt("DepartmentID"),
                            resultSet.getInt("Column"), resultSet.getInt("Row"), resultSet.getString("Path"), resultSet.getString("Title")));
                }
            }
        } catch (SQLException ex) {
            errorHandler.errorDevelopmentInfo("Issue getting departments ", ex);
        }
        return content;
    }
}
