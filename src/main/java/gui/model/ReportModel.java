package gui.model;


import be.Report;
import bll.ReportManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class ReportModel {
    private final ReportManager reportManager;
    private ObservableList<Report> reportOverview;

    public ReportModel() {
        reportManager = new ReportManager();
        reportOverview = FXCollections.observableArrayList(new ArrayList<>());
    }

    public void createReport(int departmentID, String title, String description) {
        reportManager.createReport(departmentID, title, description);
    }

    public void removeReport(Report report) {
        reportManager.removeReport(report.getId());
        updateReports(report.getDepartmentID());
    }

    public void updateReports(int departmentID) {
        reportOverview.clear();
        reportOverview.addAll(reportManager.getReports(departmentID));
    }

    public ObservableList<Report> getReportOverview() {
        return reportOverview;
    }

}
