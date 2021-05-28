package gui.controller;

import be.users.Department;
import bll.*;
import gui.model.ContentModel;
import gui.model.DepartmentModel;
import gui.model.SessionModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.*;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import refresh.RefreshManager;

public class DepartmentController implements Initializable {

    @FXML
    Pane mainBox;

    @FXML
    Label department;

    @FXML
    GridPane mainGrid;

    private final int zoomIn = 107;
    private final int zoomOut = 109;

    private ArrayList<String> pathArrayList = new ArrayList<>();
    private ArrayList<Object> fileArrayList = new ArrayList<>();

    private SessionModel sessionModel;
    private ContentModel contentModel;
    private DepartmentModel departmentModel;
    private RefreshManager refreshManager;
    private Stage menuStage;


    public DepartmentController(){
        sessionModel = SessionModel.getInstance();
        contentModel = ContentModel.getInstance();
        departmentModel = DepartmentModel.getInstance();
        refreshManager = RefreshManager.getInstance();
    }

    // Initialize method in witch we set up the department view
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int departmentID = sessionModel.getUser().getId();
        department.setText(sessionModel.getUser().getUsername());
        contentModel.updateContent(departmentID);
        contentModel.buildGrid(mainGrid, departmentModel.isAutoResizeEnabled(departmentID));
    }

    // Method to setup the listener threads
    public void setupListeners(){
        Stage stage = (Stage) mainBox.getScene().getWindow();
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

    // Zooming method not fully functional
    private void zoomNode(ZoomPane selectedPane, int keyCode) {
        double zoom = selectedPane.getZoom();
        if (keyCode == zoomIn && zoom < 2){
            selectedPane.setScaleX(zoom);
            selectedPane.setScaleY(zoom);
            selectedPane.setPrefWidth(selectedPane.getWidth() * zoom);
            selectedPane.setZoom(selectedPane.getZoom()+0.1);
        }else if(keyCode == zoomOut && zoom > 0.5){
            selectedPane.setScaleX(zoom);
            selectedPane.setScaleY(zoom);
            selectedPane.setZoom(selectedPane.getZoom()-0.1);
        }
    }

    // Closing program method
    @FXML
    void close(Stage stage){ ;
            stage.close();
            Platform.exit();
            System.exit(0);
    }

    // Method listens for key press and redirect to the selected route
    public void onKeyPressed(KeyEvent keyEvent) {
        Stage stage = (Stage) mainBox.getScene().getWindow();
        switch (keyEvent.getCode()){
            case ESCAPE: close(stage);
            break;
            case SHIFT: openDepartmentWindow();
            break;
        }
    }

    // Opens the department menu
    private void openDepartmentWindow(){
        try {
            if (menuStage == null){
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/DepartmentMenu.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                DepartmentMenu controller = fxmlLoader.getController();
                controller.setParentStage((Stage)mainBox.getScene().getWindow());
                menuStage = new Stage();
                menuStage.setOnHiding( we -> menuStage = null);
                menuStage.setScene(new Scene(root));
                menuStage.initModality(Modality.APPLICATION_MODAL);
                menuStage.show();
            }else{
                menuStage.toFront();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void min(ActionEvent actionEvent) {
    }

    public void close(ActionEvent actionEvent) {
    }

    public void full(ActionEvent actionEvent) {
    }

    public void openMenu(MouseEvent event) {
        openDepartmentWindow();
    }
}
