package bll.gloader;

import javafx.scene.Node;

public interface IChartLoader {
    /*
        Interface that loads the selected chart. It is necessary as it is structured
        by a strategy pattern. This means that we have interface with a method and classes
        behind the interface that inherit the method and use it in a way they need to.
     */
    Node loadChart(String path, String nameColumn, String dataColumn);
}
