package gui.controller;

import bll.*;
import gui.model.ContentModel;
import gui.model.SessionModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.*;
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
    private DepartmentMenu departmentMenu;


    public DepartmentController(){
        sessionModel = SessionModel.getInstance();
        contentModel = ContentModel.getInstance();
        refreshManager = RefreshManager.getInstance();
        departmentMenu = new DepartmentMenu();
    }

    /*
        Initialize method in witch we set up the department view as well start
        the necessary listeners such as the timer and file change listener
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contentModel.updateContent(sessionModel.getUser().getId());
        contentModel.buildGrid(mainGrid);

        Thread listenerThread = new Thread(() -> {
            Platform.runLater(() -> {
                Stage stage = (Stage) mainGrid.getScene().getWindow();
                refreshManager.runTimer(sessionModel.getUser(), stage);
                //refreshManager.listenChanges(contentModel.getContentOverview(), sessionModel.getUser(), stage);
            });
        });
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
            case SHIFT: departmentMenu.showMenu(stage);
            break;
        }
    }
}
