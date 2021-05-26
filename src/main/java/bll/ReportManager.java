package bll;


import be.Report;
import dal.IReportRepository;
import dal.db.DBReportRepository;

import java.util.List;

public class ReportManager {
    private final IReportRepository reportRepository;

    public ReportManager() {
        reportRepository = new DBReportRepository();
    }

    public void createReport(int departmentID, String title, String description) {
        reportRepository.createReport(departmentID, title, description);
    }

    public List<Report> getReports(int departmentID) {
        return reportRepository.getReports(departmentID);
    }

    public void removeReport(int reportID){
        reportRepository.removeReport(reportID);
    }
}
