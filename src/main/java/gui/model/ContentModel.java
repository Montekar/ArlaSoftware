package gui.model;

import be.View;
import bll.ContentManager;
import bll.ContentType;
import bll.PathManager;
import bll.vloader.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
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
    private PathManager pathManager = new PathManager();

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
        grid.getChildren().clear();
        IViewLoader iViewLoader = null;
        for (View view : contentOverview) {
            ContentType contentType = pathManager.getType(view.getPath());
            if (contentType != null) {
                switch (contentType) {
                    case CSV -> iViewLoader = new CSVLoader();
                    case PDF -> iViewLoader = new PdfLoader();
                    case JPG -> iViewLoader = new ImageLoader();
                    case WEB -> iViewLoader = new WebViewLoader();
                    case XLS -> iViewLoader = new ExcelLoader();
                }
            } else {
                iViewLoader = new ErrorView();
            }
            assert iViewLoader != null;
            HBox titleBox = new HBox(new Label(view.getTitle()));
            titleBox.getStylesheets().add("/stylesheets/view.css");
            titleBox.setAlignment(Pos.CENTER);

            VBox viewToDisplay = new VBox();
            viewToDisplay.getChildren().addAll(titleBox,iViewLoader.loadView(view.getPath()));
            viewToDisplay.setAlignment(Pos.TOP_CENTER);

            grid.add(viewToDisplay, view.getColumn(), view.getRow());
        }
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
