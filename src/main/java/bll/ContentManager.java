package bll;

import be.ChartView;
import be.View;
import bll.gloader.*;
import bll.vloader.*;
import dal.IContentRepository;
import dal.db.DBContentRepository;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
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

    /**
     * This method will get a loader to load a specific type of content depending on the path.
     * ContentType is a enum.
     *
     * @param view content configuration entity.
     * @return returns a content loader.
     */

    private IViewLoader getLoader(View view) {
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
                case XLS -> {
                    return new ExcelLoader();
                }
                case XLSX -> {
                    return new ExcelXLoader();
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

    /**
     * This method will get a chart loader to load a specific type of chart.
     *
     * @param chartView chart view configuration entity.
     * @return returns a chart loader.
     */
    private IChartLoader getChartLoader(ChartView chartView) {
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

    /**
     * This method will get a single window with content inserted.
     *
     * @param view              content configuration entity.
     * @param autoResizeEnabled resizing mode, true = auto resize, false = custom size.
     * @return returns a window with content in it.
     */
    public Node getWindow(View view, boolean autoResizeEnabled) {
        HBox title = new HBox(new Label(view.getTitle()));
        title.getStylesheets().add("/stylesheets/view.css");
        title.setAlignment(Pos.CENTER);
        VBox window = new VBox();
        window.setAlignment(Pos.TOP_CENTER);

        Platform.runLater(() -> {
            if (view instanceof ChartView) {
                IChartLoader loader = getChartLoader((ChartView) view);
                window.getChildren().addAll(title, loader.loadChart(view.getPath(),
                        ((ChartView) view).getNameColumn(), ((ChartView) view).getDataColumn()));
            } else {
                IViewLoader loader = getLoader(view);
                window.getChildren().addAll(title, loader.loadView(view,autoResizeEnabled));
            }
        });

        if (!autoResizeEnabled) {
            window.setPrefSize(view.getWidth(), view.getHeight());
        }
        return window;
    }

    /**
     * The code below will build/configure the grid with the provided content and grid itself.
     *
     * @param grid              grid that you want to edit.
     * @param autoResizeEnabled resizing mode, true = auto resize, false = custom size
     * @param contentObservable a list of content to be inserted into grid pane.
     */

    public void buildGrid(GridPane grid, boolean autoResizeEnabled, ObservableList<View> contentObservable) {
        grid.getChildren().clear();
        new Thread(() -> {
            for (View view : contentObservable) {
                Platform.runLater(() -> {
                    grid.getRowConstraints().clear();
                    grid.getColumnConstraints().clear();
                    grid.add(getWindow(view, autoResizeEnabled), view.getColumn(), view.getRow());

                    if (autoResizeEnabled) {
                        int rows = grid.getRowCount();
                        int columns = grid.getColumnCount();

                        ColumnConstraints columnConstraints = new ColumnConstraints(grid.getWidth() / columns);
                        RowConstraints rowConstraints = new RowConstraints(grid.getHeight() / rows);

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
