package dal.db;

import be.Report;
import dal.IReportRepository;
import error.ErrorHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBReportRepository implements IReportRepository {

    private DatabaseConnection connection;
    private ErrorHandler errorHandler;

    public DBReportRepository() {
        connection = new DatabaseConnection();
        errorHandler = new ErrorHandler();
    }

    @Override
    public void createReport(int departmentID, String title, String description) {
        try (Connection con = connection.getConnection()) {
            String sql = "INSERT INTO Report Values(?,?,?)";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, departmentID);
            statement.setString(2, title);
            statement.setString(3, description);
            statement.execute();
        } catch (SQLException ex) {
            errorHandler.errorDevelopmentInfo("Issue creating report", ex);
        }
    }

    @Override
    public void removeReport(int reportID) {
        try (Connection con = connection.getConnection()) {
            String sql = "Delete FROM Report WHERE ID = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, reportID);
            statement.execute();
        } catch (SQLException ex) {
            errorHandler.errorDevelopmentInfo("Issue deleting report", ex);
        }
    }

    @Override
    public List<Report> getReports(int departmentID) {
        List<Report> reports = new ArrayList<>();
        try (Connection con = connection.getConnection()) {
            String sql = "Select Report.ID,Department.Username,Report.Title,Report.[Description] FROM Report JOIN Department ON Report.DepartmentID = Department.ID WHERE Department.ID = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, departmentID);
            if(statement.execute()){
                ResultSet rs = statement.getResultSet();
                while(rs.next()){
                    reports.add(new Report(rs.getInt("ID"),departmentID,rs.getString("Username"),rs.getString("Title"),rs.getString("Description")));
                }
            }
        } catch (SQLException ex) {
            errorHandler.errorDevelopmentInfo("Issue getting reports", ex);
        }
        return reports;
    }

}
