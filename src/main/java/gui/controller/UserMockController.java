package gui.controller;

import bll.ViewLoader;
import bll.WebViewLoader;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.scene.control.ListView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.scene.web.WebView;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;

public class UserMockController implements Initializable {

    @FXML
    private WebView webView;

    @FXML
    private Pane csvFile;

    @FXML
    private Pane excelFile;

    @FXML
    private Pane pdfFile;

    private ViewLoader viewLoader;

    private ListView csvData = new ListView();

    private final int zoomIn = 107;
    private final int zoomOut = 109;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadWeb("https://www.google.com");
        loadPdf("test");
        loadExcel("src/main/resources/mockFiles/MOCK_DATA.xls");
        loadCsv("src/main/resources/mockFiles/MOCK_DATA.csv");

        csvFile.setOnKeyPressed(keyEvent -> {
            double zoom = 1;
            if (keyEvent.getCode().getCode() == zoomIn && zoom < 2){
                System.out.println("Plus has been entered");
                System.out.println(keyEvent);
                Scale scale = new Scale();
                csvData.setScaleX(zoom);
                csvData.setScaleY(zoom);
                zoom += 0.1;
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
        });

    }

    //reads and loads excel file
    private void loadExcel(String path) {
        ListView listView = new ListView();
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(path));
            HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);

            HSSFSheet sheet = workbook.getSheetAt(0);

            FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
            for (Row row: sheet){
                String line = "";
                for (Cell cell: row){
                    switch (formulaEvaluator.evaluateInCell(cell).getCellType()){
                        case NUMERIC:
                            line += cell.getNumericCellValue() + "\t\t";
                        break;

                        case STRING:
                            line += cell.getStringCellValue() + "\t\t";
                        break;
                    }
                }
                listView.getItems().add(line);
                line = "";
            }
            listView.setMinWidth(600);
            listView.setMaxHeight(300);
            excelFile.getChildren().add(listView);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //reads and loads a pdf file
    private void loadPdf(String path) {
    }

    //reads and loads the website based on provided url
    public void loadWeb(String path){
        viewLoader = new WebViewLoader();
        webView.getEngine().load(path);
    }

    //reads and outputs data from CSV file
    public void loadCsv(String path){
        try {
            Scanner csvScanner = new Scanner(new File(path));
            //csvScanner.useDelimiter(",");
            while (csvScanner.hasNext()) {
                csvData.getItems().add(csvScanner.next());
            }
            csvData.setMinWidth(400);
            csvData.setMaxHeight(330);
            csvFile.getChildren().add(csvData);
            csvScanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
