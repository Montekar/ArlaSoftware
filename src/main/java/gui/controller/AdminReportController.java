package gui.controller;

import be.Report;
import gui.model.ReportModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminReportController implements Initializable {

    @FXML
    private TableView<Report> reportTable;
    @FXML
    private TableColumn<Report, String> title;
    @FXML
    private TableColumn<Report, String> description;
    @FXML
    private TableColumn<Report, String> id;

    @FXML
    private Label titleLabel;
    @FXML
    private TextArea descriptionText;

    private final int departmentID;

    private final ReportModel reportModel;

    public AdminReportController(int departmentID){
        this.departmentID = departmentID;
        reportModel = new ReportModel();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        reportTable.setItems(reportModel.getReportOverview());

        reportTable.getSelectionModel().selectedItemProperty().addListener((observableValue, report, t1) -> {
            if(t1!=null){
                titleLabel.setText(t1.getTitle());
                descriptionText.setText(t1.getDescription());
            }
        });
        reportModel.updateReports(departmentID);
    }

    @FXML
    public void solveAction(ActionEvent e){
        if(reportTable.getSelectionModel().getSelectedItem()!=null){
            Report report = reportTable.getSelectionModel().getSelectedItem();
            reportModel.removeReport(report);
            titleLabel.setText("");
            descriptionText.clear();
        }
    }
}
