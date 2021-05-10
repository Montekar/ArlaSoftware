package gui.controller;

import be.View;
import bll.*;
import bll.vloader.CSVLoader;
import bll.vloader.ExcelLoader;
import bll.vloader.IViewLoader;
import bll.vloader.WebViewLoader;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.*;
import java.net.URL;
import java.nio.file.*;
import java.util.*;

import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

import javafx.stage.Stage;
import refresh.Notification;
import refresh.RefreshButton;
import refresh.RefreshTimer;

public class UserMockController implements Initializable {

    @FXML
    FlowPane mainPane;
    
    @FXML
    Button min;
    
    @FXML
    Button show;
    
    @FXML
    Button close;

    private ZoomPane webPane = new ZoomPane();
    private ZoomPane csvPane = new ZoomPane();
    private ZoomPane excelPane = new ZoomPane();
    private ZoomPane pdfPane = new ZoomPane();
    private RefreshButton refreshButton = new RefreshButton();
    private Notification notification;

    private IViewLoader webView;
    private IViewLoader csvView;
    private IViewLoader excelView;

    private final int zoomIn = 107;
    private final int zoomOut = 109;
    private Map<Pane, Integer> zoomLevel = new HashMap<>();
    RefreshTimer refreshTimer = new RefreshTimer();
    private Loader loader = new Loader();
    private ArrayList<View> viewArrayList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        notification = new Notification();
        refreshTimer.runTimer();
        webView = new WebViewLoader();
        csvView = new CSVLoader();
        excelView = new ExcelLoader();

        viewArrayList = loader.getScreenParts("cocio");
        /*
        for (View view : viewArrayList){
            if(view.getType().equals("webView")){
                webPane.getChildren().add( webView.loadView(view.getPath()));
            }else if(view.getType().equals("csvPane")){
                csvPane.getChildren().add(csvView.loadView(view.getPath()));
            }else if(view.getType().equals("excelPane")){
                excelPane.getChildren().add( excelView.loadView("src/main/resources/mockFiles/MOCK_DATA.xls"));
            }
        }
        */

        zoomLevel.put(csvPane,1);
        mainPane.getChildren().addAll(webPane,csvPane,excelPane,refreshButton);

        csvPane.setOnKeyPressed(keyEvent -> {
            zoomNode(csvPane, keyEvent.getCode().getCode());
        });

        excelPane.setOnKeyPressed(keyEvent -> {
            zoomNode(excelPane, keyEvent.getCode().getCode());
        });


        Thread listenerThread = new Thread(() -> {
            /*Platform.runLater(() -> {
                listenForChanges();
            });*/
            listenForChanges();
        });
        listenerThread.start();


    }

    private void listenForChanges() {
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path path = Paths.get("src/main/resources/mockFiles/");
            path.register(watchService,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY);
            while (true){
                WatchKey key = watchService.take();
                for (WatchEvent event: key.pollEvents()){
                    System.out.println(event.kind() + ": " + event.context());
                }
                boolean valid = key.reset();
                if (!valid){
                    break;
                }
            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
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

    public void onMinimize(ActionEvent actionEvent) {
        Stage stage = (Stage) min.getScene().getWindow();
        stage.setIconified(true);
    }

    public void fullScreen(ActionEvent actionEvent) {
        Stage stage = (Stage) show.getScene().getWindow();
        stage.setFullScreen(!stage.isFullScreen());
    }

    public void onClose(ActionEvent actionEvent) {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
        Platform.exit();
        System.exit(0);
    }

}
