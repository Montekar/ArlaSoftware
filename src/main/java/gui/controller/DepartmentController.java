package gui.controller;

import be.View;
import bll.*;
import gui.model.ContentModel;
import gui.model.SessionModel;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.io.*;
import java.net.URL;
import java.nio.file.*;
import java.util.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import refresh.Notification;
import refresh.RefreshTimer;

public class DepartmentController implements Initializable {
    @FXML
    GridPane mainGrid;
    private Notification notification;
    ContentModel contentModel;
    private final int zoomIn = 107;
    private final int zoomOut = 109;
    RefreshTimer refreshTimer;
    private Loader loader;
    private ObservableList<View> viewObservableList;
    private ArrayList<String> pathArrayList = new ArrayList<>();
    private ArrayList<Object> fileArrayList = new ArrayList<>();
    private SessionModel sessionModel;

    public DepartmentController(){
        loader = new Loader();
        refreshTimer = new RefreshTimer();
        notification = new Notification();
        refreshTimer.runTimer();
        sessionModel = SessionModel.getInstance();
        contentModel = ContentModel.getInstance();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        viewObservableList =  contentModel.getContentOverview();
        //loadComponents(viewArrayList);
        contentModel.updateContent(sessionModel.getUser().getId());
        contentModel.buildGrid(mainGrid);
       /* csvPane.setOnKeyPressed(keyEvent -> {
            zoomNode(csvPane, keyEvent.getCode().getCode());
        });

        excelPane.setOnKeyPressed(keyEvent -> {
            zoomNode(excelPane, keyEvent.getCode().getCode());
        });
*/
        Thread listenerThread = new Thread(() -> {
            Platform.runLater(() -> {
            });
        });
        listenerThread.start();


    }

    private void loadComponents(ObservableList<View> viewList) {
        for (View view : viewList){
                File file = new File(view.getPath());
                Path path = Paths.get(view.getPath());
                pathArrayList.add(file.getParent());
                fileArrayList.add(path.getFileName());
        }
    }

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

    @FXML
    void onESCAPE(KeyEvent enter) throws IOException {
        if (enter.getCode().equals(KeyCode.ESCAPE)) {
            Stage stage = (Stage) mainGrid.getScene().getWindow();
            stage.close();
            Platform.exit();
            System.exit(0);
        }
    }
}
