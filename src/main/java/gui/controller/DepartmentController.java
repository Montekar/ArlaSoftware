package gui.controller;

import be.users.Department;
import bll.*;
import gui.model.ContentModel;
import gui.model.SessionModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.*;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import refresh.RefreshManager;

public class DepartmentController implements Initializable {
    @FXML
    GridPane mainGrid;

    private final int zoomIn = 107;
    private final int zoomOut = 109;

    private ArrayList<String> pathArrayList = new ArrayList<>();
    private ArrayList<Object> fileArrayList = new ArrayList<>();

    private SessionModel sessionModel;
    private ContentModel contentModel;
    private RefreshManager refreshManager;


    public DepartmentController(){
        sessionModel = SessionModel.getInstance();
        contentModel = ContentModel.getInstance();
        refreshManager = RefreshManager.getInstance();
    }

    /*
        Initialize method in witch we set up the department view as well start
        the necessary listeners such as the timer and file change listener
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contentModel.updateContent(sessionModel.getUser().getId());
        contentModel.buildGrid(mainGrid);
    }

    public void setupListeners(){
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
        Stage stage = (Stage) mainGrid.getScene().getWindow();
        switch (keyEvent.getCode()){
            case ESCAPE: close(stage);
            break;
            case SHIFT: openDepartmentWindow();
            break;
        }
    }

    private void openDepartmentWindow(){

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/DepartmentMenu.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            DepartmentMenu controller = fxmlLoader.getController();
            controller.setParentStage((Stage)mainGrid.getScene().getWindow());
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
