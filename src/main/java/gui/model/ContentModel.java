package gui.model;

import be.View;
import bll.ContentManager;
import bll.PathManager;
import bll.vloader.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.GridPane;
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

        IViewLoader iViewLoader;
        for (View view : contentOverview) {
            switch (pathManager.getType(view.getPath())) {
                case CSV -> iViewLoader = new CSVLoader();
                case PDF -> iViewLoader = new PdfLoader();
                case JPG -> iViewLoader = new ImageLoader();
                case WEB -> iViewLoader = new WebViewLoader();
                case XLS -> iViewLoader = new ExcelLoader();
                default ->  iViewLoader = null;
            }
            assert iViewLoader != null;
            grid.add(iViewLoader.loadView(view.getPath()), view.getColumn(), view.getRow());
        }
    }

    public void updateContent(int departmentID) {
        contentOverview.clear();
        contentOverview.addAll(contentManager.getContent(departmentID));
    }

    public ObservableList<View> getContentOverview() {
        return contentOverview;
    }
}
