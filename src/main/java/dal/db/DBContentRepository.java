package dal.db;

import be.ChartView;
import be.View;
import be.users.Admin;
import bll.ChartType;
import dal.IContentRepository;
import error.ErrorHandler;
import gui.controller.Alert;

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
            String sqlView = "Select * From Content WHERE DepartmentID = ?";
            PreparedStatement statementView = con.prepareStatement(sqlView);
            statementView.setInt(1, departmentID);
            if (statementView.execute()) {
                ResultSet resultSetView = statementView.getResultSet();
                while (resultSetView.next()) {
                    if (resultSetView.getInt("ChartID") < 0){
                        content.add(new View(
                                resultSetView.getInt("DepartmentID"),
                                resultSetView.getInt("Column"),
                                resultSetView.getInt("Row"),
                                resultSetView.getInt("Width"),
                                resultSetView.getInt("Height"),
                                resultSetView.getString("Path"),
                                resultSetView.getString("Title")
                        ));
                    }else{
                        String sqlChartView = "SELECT * FROM Content " +
                                      "INNER JOIN ChartData " +
                                      "ON ChartData.id = Content.chartId " +
                                      "WHERE Content.DepartmentId = ? AND ChartData.id = ?";
                        PreparedStatement statementChartView = con.prepareStatement(sqlChartView);
                        statementChartView.setInt(1,departmentID);
                        statementChartView.setInt(2,resultSetView.getInt("chartId"));

                        if (statementChartView.execute()){
                            ResultSet resultSetChartView = statementChartView.getResultSet();
                            if(resultSetChartView.next()){
                                content.add(new ChartView(
                                        resultSetChartView.getInt("DepartmentID"),
                                        resultSetChartView.getInt("Column"),
                                        resultSetChartView.getInt("Row"),
                                        resultSetChartView.getInt("Width"),
                                        resultSetChartView.getInt("Height"),
                                        resultSetChartView.getString("Path"),
                                        resultSetChartView.getString("Title"),
                                        resultSetChartView.getString("nameCol"),
                                        resultSetChartView.getString("dataCol"),
                                        ChartType.getTypeFromString(resultSetChartView.getString("chartType"))
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
            String sql = "INSERT INTO Content Values(?,?,?,?,?,?,?,-1)";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, view.getId());
            statement.setInt(2, view.getColumn());
            statement.setInt(3, view.getRow());
            statement.setInt(4, view.getWidth());
            statement.setInt(5, view.getHeight());
            statement.setString(6, view.getTitle());
            statement.setString(7, view.getPath());
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
            statement.setInt(1, view.getId());
            statement.setInt(2, view.getColumn());
            statement.setInt(3, view.getRow());
            statement.execute();
        } catch (SQLException ex) {
            errorHandler.errorDevelopmentInfo("Issue deleting content", ex);
        }
    }

    @Override
    public void editContent(View oldView, View newView) {
        try (Connection con = connection.getConnection()) {
            String sql = "UPDATE Content SET [Column] = ?, Row = ?, Width = ?, Height = ?, Title = ?, Path = ? WHERE DepartmentID = ? AND [Column] = ? AND Row = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, newView.getColumn());
            statement.setInt(2, newView.getRow());
            statement.setInt(3, newView.getWidth());
            statement.setInt(4, newView.getHeight());
            statement.setString(5, newView.getTitle());
            statement.setString(6, newView.getPath());
            statement.setInt(7, oldView.getId());
            statement.setInt(8, oldView.getColumn());
            statement.setInt(9, oldView.getRow());
            statement.execute();
        } catch (SQLException ex) {
            errorHandler.errorDevelopmentInfo("Issue editing content", ex);
        }
    }
}
