package gui.controller;

import bll.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.*;
import java.net.URL;
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

public class UserMockController implements Initializable {

    @FXML
    FlowPane mainPane;

    private ZoomPane webPane = new ZoomPane();
    private ZoomPane csvPane = new ZoomPane();
    private ZoomPane excelPane = new ZoomPane();
    private ZoomPane pdfPane = new ZoomPane();

    private IViewLoader webView;
    private IViewLoader csvView;
    private IViewLoader excelView;

    private final int zoomIn = 107;
    private final int zoomOut = 109;
    private Map<Pane, Integer> zoomLevel = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        webView = new WebViewLoader();
        csvView = new CSVLoader();
        excelView = new ExcelLoader();
        webPane.getChildren().add( webView.loadView("https://www.google.com"));
        csvPane.getChildren().add(csvView.loadView("src/main/resources/mockFiles/MOCK_DATA.csv"));
        excelPane.getChildren().add( excelView.loadView("src/main/resources/mockFiles/MOCK_DATA.xls"));
        zoomLevel.put(csvPane,1);

        mainPane.getChildren().addAll(webPane,csvPane,excelPane);
       // idr
       /* csvFile.setOnKeyPressed(keyEvent -> {
           // int zoom = zoomLevel.get(csvFile);
            int zoom = csvFile.getZoom();
            if (keyEvent.getCode().getCode() == zoomIn && zoom < 2){
                System.out.println("Plus has been entered");
                System.out.println(keyEvent);
                Scale scale = new Scale();
                csvData.setScaleX(zoom);
                csvData.setScaleY(zoom);
               // zoom += 0.1; csvFile.setZoom(csvFile.getZoom()+0.1);
                System.out.println(zoom);
                csvData.setMinWidth(100);

            }else if(keyEvent.getCode().getCode() == zoomOut && zoom > 0.5){
                System.out.println("Minus has been pressed");
                System.out.println(keyEvent);
                csvData.setScaleX(zoom);
                csvData.setScaleY(zoom);
                zoom -= 0.1;
                System.out.println(zoom);
                csvData.setMinWidth(400);
            }else {
                System.out.println(keyEvent);
            }
        });*/

    }
}
