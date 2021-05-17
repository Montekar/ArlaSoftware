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
            String sql = "Select * From Content WHERE DepartmentID = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, departmentID);
            if (statement.execute()) {
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    content.add(new View(resultSet.getInt("DepartmentID"),
                            resultSet.getInt("Column"), resultSet.getInt("Row"), resultSet.getString("Path"), resultSet.getString("Title")));
                }
            }
        } catch (SQLException ex) {
            errorHandler.errorDevelopmentInfo("Issue getting content", ex);
        }
        return content;
    }

    @Override
    public void createContent(int departmentID,String title,String path, int column, int row) {
        try (Connection con = connection.getConnection()) {
            String sql = "INSERT INTO Content Values(?,?,?,?,?)";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, departmentID);
            statement.setInt(2, column);
            statement.setInt(3, row);
            statement.setString(4, title);
            statement.setString(5, path);
            statement.execute();
        } catch (SQLException ex) {
            errorHandler.errorDevelopmentInfo("Issue adding content", ex);
        }
    }

    @Override
    public void deleteContent(int departmentID, int column, int row) {
        try (Connection con = connection.getConnection()) {
            String sql = "DELETE FROM Content WHERE DepartmentID = ? AND [Column] = ? AND Row = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1,departmentID);
            statement.setInt(2,column);
            statement.setInt(3,row);
            statement.execute();
        } catch (SQLException ex) {
            errorHandler.errorDevelopmentInfo("Issue deleting content", ex);
        }
    }


}
