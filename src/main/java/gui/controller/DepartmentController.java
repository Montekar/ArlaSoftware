package gui.controller;

import be.View;
import bll.*;
import bll.vloader.*;
import gui.model.SessionModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.*;
import java.net.URL;
import java.nio.file.*;
import java.util.*;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.validator.routines.UrlValidator;
import refresh.Notification;
import refresh.RefreshButton;
import refresh.RefreshTimer;

public class DepartmentController implements Initializable {
    enum fileType {
        CSV,
        XLS,
        XLSX,
        PDF,
        JPG
    }

    @FXML
    GridPane mainPane;

    private final String imagePath = "src/main/resources/mockFiles/warning.jpg";
    private RefreshButton refreshButton = new RefreshButton();
    private Notification notification;

    private IViewLoader webView;
    private IViewLoader csvView;
    private IViewLoader excelView;
    private IViewLoader imageView;
    private IViewLoader pdfView;
    private final int zoomIn = 107;
    private final int zoomOut = 109;
    private Map<Pane, Integer> zoomLevel = new HashMap<>();
    RefreshTimer refreshTimer = new RefreshTimer();
    private Loader loader = new Loader();
    private ArrayList<View> viewArrayList;
    private ArrayList<String> pathArrayList = new ArrayList<>();
    private ArrayList<Object> fileArrayList = new ArrayList<>();
    private SessionModel sessionModel = SessionModel.getInstance();
    private UrlValidator urlValidator;

    public DepartmentController(){
        notification = new Notification();
        refreshTimer.runTimer();
        webView = new WebViewLoader();
        csvView = new CSVLoader();
        excelView = new ExcelLoader();
        urlValidator = new UrlValidator();
        imageView = new ImageLoader();
        pdfView = new PdfLoader();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        viewArrayList = loader.getScreenParts(25);
        loadComponents(viewArrayList);


       /* csvPane.setOnKeyPressed(keyEvent -> {
            zoomNode(csvPane, keyEvent.getCode().getCode());
        });

        excelPane.setOnKeyPressed(keyEvent -> {
            zoomNode(excelPane, keyEvent.getCode().getCode());
        });
*/

        Thread listenerThread = new Thread(() -> {
            /*Platform.runLater(() -> {
                listenForChanges();
            });*/
            listenForChanges();
        });
        listenerThread.start();


    }

    private void loadComponents(ArrayList<View> viewArrayList) {
        for (View view : viewArrayList){
            if(urlValidator.isValid(view.getPath())){
                //mainPane.add(webView.loadView(view.getPath()), view.getRow(), view.getColumn());
                mainPane.add(pdfView.loadView(view.getPath()), view.getRow(), view.getColumn());
            }else if (new File(view.getPath()).isFile()){
                File file = new File(view.getPath());
                Path path = Paths.get(view.getPath());
                pathArrayList.add(file.getParent());
                fileArrayList.add(path.getFileName());
                String fileExtension = FilenameUtils.getExtension(view.getPath()).toUpperCase();
                if (fileType.CSV.toString().equalsIgnoreCase(FilenameUtils.getExtension(view.getPath()))){
                    mainPane.add(csvView.loadView(view.getPath()), view.getRow(),view.getColumn());
                }else if (fileType.XLS.toString().equalsIgnoreCase(FilenameUtils.getExtension(view.getPath()))){
                    mainPane.add(excelView.loadView(view.getPath()), view.getRow(),view.getColumn());
                }else if (fileType.PDF.toString().equalsIgnoreCase(FilenameUtils.getExtension(view.getPath()))){
                    mainPane.add(pdfView.loadView(view.getPath()), view.getRow(), view.getColumn());
                }else if (fileType.JPG.toString().equalsIgnoreCase(FilenameUtils.getExtension(view.getPath()))){
                    mainPane.add(imageView.loadView(view.getPath()), view.getRow(), view.getColumn());
                }
            }else {
                mainPane.add(imageView.loadView(imagePath), view.getRow(), view.getColumn());
            }
        }
    }

    private void listenForChanges() {
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            for (int i = 0; i < pathArrayList.size(); i++){
                Path path = Path.of(String.valueOf(pathArrayList.get(i)));
                path.register(watchService,
                        StandardWatchEventKinds.ENTRY_DELETE,
                        StandardWatchEventKinds.ENTRY_MODIFY);
            }
                while (true){
                    WatchKey key = watchService.take();
                    for (WatchEvent event: key.pollEvents()){
                        for (Object file : fileArrayList){
                            if (file.equals(event.context())){
                                System.out.println("Changes have been made");
                                System.out.println(event.kind() + ": " + event.context() );
                            }
                        }
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
