package dal;

import be.Report;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public interface IReportRepository {
    void createReport(int departmentID, String title, String description);
    void removeReport(int reportID);
    List<Report> getReports(int departmentID);
}
