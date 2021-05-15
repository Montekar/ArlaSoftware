package bll.gloader;

import javafx.scene.Node;

public interface IChartLoader {
    Node loadChart(String path, String nameColumn, String dataColumn);
}
