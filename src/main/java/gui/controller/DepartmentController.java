package gui.controller;

import gui.model.ContentModel;
import gui.model.DepartmentModel;
import gui.model.SessionModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import refresh.RefreshManager;

public class DepartmentController implements Initializable {

    @FXML
    GridPane mainGrid;

    private SessionModel sessionModel;
    private ContentModel contentModel;
    private DepartmentModel departmentModel;
    private RefreshManager refreshManager;


    public DepartmentController() {
        sessionModel = SessionModel.getInstance();
        contentModel = ContentModel.getInstance();
        departmentModel = DepartmentModel.getInstance();
        refreshManager = RefreshManager.getInstance();
    }

    // Initialize method in witch we set up the department view
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int departmentID = sessionModel.getUser().getId();
        contentModel.updateContent(departmentID);
        contentModel.buildGrid(mainGrid, departmentModel.isAutoResizeEnabled(departmentID));
    }

    // Method to setup the listener threads
    public void setupListeners() {
        Stage stage = (Stage) mainGrid.getScene().getWindow();
        System.out.println("Stage: " + stage);
        Thread timerThread = new Thread(() -> {
            Platform.runLater(() -> {
                refreshManager.runTimer(sessionModel.getUser(), stage);
            });
        });
        timerThread.setDaemon(true);
        timerThread.start();

        Thread listenerThread = new Thread(() -> {
            refreshManager.listenChanges(contentModel.getContentOverview(), sessionModel.getUser(), stage);
        });
        listenerThread.setDaemon(true);
        listenerThread.start();
    }

    // Opens the department menu
    public void openDepartmentWindow() {
        try {
            Stage mainStage = (Stage) mainGrid.getScene().getWindow();
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DepartmentMenu.fxml"));
            Parent root = loader.load();
            DepartmentMenu controller = loader.getController();
            controller.setParentStage((Stage) mainGrid.getScene().getWindow());
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(mainStage);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close(ActionEvent actionEvent) {
        System.out.println("clicked");
        Platform.exit();
        System.exit(0);
    }

    public void openMenu(MouseEvent event) {
        openDepartmentWindow();
    }
}
