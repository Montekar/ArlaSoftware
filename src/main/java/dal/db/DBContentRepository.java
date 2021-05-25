package dal.db;

import be.ChartView;
import be.View;
import be.users.Admin;
import bll.ChartType;
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
                    if (resultSet.getInt("chartId") < 0){
                        content.add(new View(resultSet.getInt("DepartmentID"),
                                resultSet.getInt("Column"), resultSet.getInt("Row"), resultSet.getString("Path"), resultSet.getString("Title")
                        ));
                    }else{
                        String sql1 = "SELECT * FROM Content " +
                                      "INNER JOIN ChartData " +
                                      "ON ChartData.id = Content.chartId " +
                                      "WHERE Content.DepartmentId = ? AND ChartData.id = ?";
                        PreparedStatement statement1 = con.prepareStatement(sql1);
                        statement1.setInt(1,departmentID);
                        statement1.setInt(2,resultSet.getInt("chartId"));

                        if (statement1.execute()){
                            ResultSet resultSet1 = statement1.getResultSet();
                            if(resultSet1.next()){
                                content.add(new ChartView(resultSet1.getInt("DepartmentID"),
                                        resultSet1.getInt("Column"), resultSet1.getInt("Row"),
                                        resultSet1.getString("Path"), resultSet1.getString("Title"),
                                        resultSet1.getString("nameCol"),resultSet1.getString("dataCol"),
                                        ChartType.getTypeFromString(resultSet1.getString("chartType"))
                                        ));
                            }
                        }
                    }

                }
            }
        } catch (SQLException ex) {
            errorHandler.errorDevelopmentInfo("Issue getting content", ex);
            ex.printStackTrace();
        }
        return content;
    }

    @Override
    public void createContent(View view) {
        try (Connection con = connection.getConnection()) {
            String sql = "INSERT INTO Content Values(?,?,?,?,?)";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, view.getId());
            statement.setInt(2, view.getColumn());
            statement.setInt(3, view.getRow());
            statement.setString(4, view.getTitle());
            statement.setString(5, view.getPath());
            statement.execute();
        } catch (SQLException ex) {
            errorHandler.errorDevelopmentInfo("Issue adding content", ex);
        }
    }

    @Override
    public void deleteContent(View view) {
        try (Connection con = connection.getConnection()) {
            String sql = "DELETE FROM Content WHERE DepartmentID = ? AND [Column] = ? AND Row = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1,view.getId());
            statement.setInt(2,view.getColumn());
            statement.setInt(3,view.getRow());
            statement.execute();
        } catch (SQLException ex) {
            errorHandler.errorDevelopmentInfo("Issue deleting content", ex);
        }
    }

    @Override
    public void editContent(View oldView, View newView) {
        try (Connection con = connection.getConnection()) {
            String sql = "UPDATE Content SET [Column] = ?, Row = ?, Title = ?, Path = ? WHERE DepartmentID = ? AND [Column] = ? AND Row = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1,newView.getColumn());
            statement.setInt(2,newView.getRow());
            statement.setString(3,newView.getTitle());
            statement.setString(4,newView.getPath());
            statement.setInt(5,oldView.getId());
            statement.setInt(6,oldView.getColumn());
            statement.setInt(7,oldView.getRow());
            statement.execute();
        } catch (SQLException ex) {
            errorHandler.errorDevelopmentInfo("Issue editing content", ex);
        }
    }
}
