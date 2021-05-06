package gui.controller;

import bll.*;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.*;
import java.net.URL;
import java.nio.file.*;
import java.util.*;

import javafx.scene.control.ListView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.scene.web.WebView;

import javafx.util.Pair;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import refresh.Notification;
import refresh.RefreshButton;
import refresh.RefreshTimer;

public class UserMockController implements Initializable {

    @FXML
    FlowPane mainPane;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        notification = new Notification();
        refreshTimer.runTimer();
        webView = new WebViewLoader();
        csvView = new CSVLoader();
        excelView = new ExcelLoader();
        webPane.getChildren().add( webView.loadView("https://www.google.com"));
        csvPane.getChildren().add(csvView.loadView("src/main/resources/mockFiles/MOCK_DATA.csv"));
        excelPane.getChildren().add( excelView.loadView("src/main/resources/mockFiles/MOCK_DATA.xls"));
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
}
