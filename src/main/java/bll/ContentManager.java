package bll;

import be.ChartView;
import be.View;
import bll.gloader.*;
import bll.vloader.*;
import dal.IContentRepository;
import dal.db.DBContentRepository;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.util.List;

public class ContentManager {
    private final IContentRepository contentRepository;
    private final PathManager pathManager;

    public ContentManager() {
        contentRepository = new DBContentRepository();
        pathManager = new PathManager();
    }

    public List<View> getContent(int departmentID) {
        return contentRepository.getContent(departmentID);
    }

    public void createContent(View view) {
        contentRepository.createContent(view);
    }

    public void deleteContent(View view) {
        contentRepository.deleteContent(view);
    }

    public void editContent(View oldView, View newView) {
        contentRepository.editContent(oldView, newView);
    }

    public IViewLoader getLoader(View view) {
        ContentType contentType = pathManager.getType(view.getPath());
        if (contentType != null) {
            switch (contentType) {
                case CSV -> {
                    return new CSVLoader();
                }
                case PDF -> {
                    return new PdfLoader();
                }
                case JPG, PNG -> {
                    return new ImageLoader();
                }
                case WEB -> {
                    return new WebViewLoader();
                }
                case XLS, XLSX -> {
                    return new ExcelLoader();
                }
                case MP4 -> {
                    return new VideoLoader();
                }
            }
        } else {
            return new ErrorView();
        }
        return null;
    }

    public IChartLoader getChartLoader(ChartView chartView) {
        ChartType chartType = chartView.getChartType();
        if (chartType != null) {
            switch (chartType) {
                case BAR -> {
                    return new BarChartLoader();
                }
                case PIE -> {
                    return new PieChartLoader();
                }
                case LINE -> {
                    return new LineChartLoader();
                }

            }
        } else {
            return new ErrorChart();
        }
        return null;
    }

    public VBox getWindow(View view, boolean autoResizeEnabled) {
        HBox title = new HBox(new Label(view.getTitle()));
        title.getStylesheets().add("/stylesheets/view.css");
        title.setAlignment(Pos.CENTER);

        VBox window = new VBox();
        window.setAlignment(Pos.TOP_CENTER);
        if (view instanceof ChartView) {
            IChartLoader loader = getChartLoader((ChartView) view);
            Platform.runLater(() -> {
                window.getChildren().addAll(title, loader.loadChart(view.getPath(), ((ChartView) view).getNameColumn(), ((ChartView) view).getDataColumn()));
            });
        } else {
            IViewLoader loader = getLoader(view);
            Platform.runLater(() -> {
                Node content = loader.loadView(view, autoResizeEnabled);
                window.getChildren().addAll(title, content);
            });
        }

        if (!autoResizeEnabled) {
            window.setPrefSize(view.getWidth(), view.getHeight());
        }

        return window;
    }

    public void buildGrid(GridPane grid, boolean autoResizeEnabled, ObservableList<View> contentObservable) {
        grid.getChildren().clear();
        new Thread(() -> {
            for (View view : contentObservable) {
                VBox vbox = getWindow(view, autoResizeEnabled);
                Platform.runLater(() -> {
                    grid.getRowConstraints().clear();
                    grid.getColumnConstraints().clear();

                    grid.add(vbox, view.getColumn(), view.getRow());

                    if (autoResizeEnabled) {
                        int rows = grid.getRowCount();
                        int columns = grid.getColumnCount();
                        ColumnConstraints columnConstraints = new ColumnConstraints(grid.getWidth()/columns);
                        RowConstraints rowConstraints = new RowConstraints(grid.getHeight()/rows);

                        for (int i = 0; i < rows; i++) {
                            grid.getRowConstraints().add(rowConstraints);
                        }

                        for (int i = 0; i < columns; i++) {
                            grid.getColumnConstraints().add(columnConstraints);
                        }
                    }

                });
            }
        }).start();
    }

}
