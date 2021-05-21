package gui.model;

import be.View;
import bll.ContentManager;
import bll.ContentType;
import bll.PathManager;
import bll.vloader.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.validator.routines.UrlValidator;

import java.util.ArrayList;

public class ContentModel {
    private final ContentManager contentManager;
    private final ObservableList<View> contentOverview;

    private static ContentModel INSTANCE;

    public static ContentModel getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ContentModel();
        }
        return INSTANCE;
    }

    private ContentModel() {
        contentManager = new ContentManager();
        contentOverview = FXCollections.observableArrayList(new ArrayList<>());
    }

    public void buildGrid(GridPane grid) {
        new Thread(() -> {
            Platform.runLater(() -> {
                grid.getChildren().clear();
            });

            for (View view : contentOverview) {
                VBox vbox = contentManager.getWindow(view);
                Platform.runLater(() -> {
                    grid.add(vbox, view.getColumn(), view.getRow());
                });

            }
        }).start();
    }

    public void updateContent(int departmentID) {
        contentOverview.clear();
        contentOverview.addAll(contentManager.getContent(departmentID));
    }

    public void createContent(View view) {
        contentManager.createContent(view);
        updateContent(view.getId());
    }

    public void deleteContent(View view) {
        contentManager.deleteContent(view);
        updateContent(view.getId());
    }

    public void editContent(View oldView, View newView) {
        contentManager.editContent(oldView, newView);
        updateContent(newView.getId());
    }

    public ObservableList<View> getContentOverview() {
        return contentOverview;
    }
}
