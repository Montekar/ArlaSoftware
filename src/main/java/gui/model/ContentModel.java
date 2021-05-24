package gui.model;

import be.View;
import bll.ContentManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

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
                grid.setGridLinesVisible(true);
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
