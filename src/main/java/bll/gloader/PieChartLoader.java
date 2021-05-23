package bll.gloader;

import bll.FileReader;
import com.google.common.collect.Iterables;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import java.util.List;

public class PieChartLoader implements IChartLoader{
    private FileReader fileReader = new FileReader();

    /*
        This method is responsible for loading data and converting it into a Pie Chart.
        The Pie Chart will only be loaded if all necessary requirements are met. Those are providing
        a path to csv file in which data is separated by column. And providing two columns
        name that will be used as a foundation of creating the Pie Chart.
     */

    @Override
    public Node loadChart(String path, String nameColumn, String dataColumn) {
        PieChart pieChart = new PieChart();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        List<String> data = fileReader.loadData(path);
        int dataPosition[] = fileReader.getDataPosition(data,nameColumn,dataColumn);
        for (Object currentData: Iterables.skip(data, 1)){
            String currentLine = currentData.toString();
            String[] splitLine = currentLine.split(",");
            pieChartData.add(new PieChart.Data(splitLine[dataPosition[0]],Double.parseDouble(splitLine[dataPosition[1]])));
        }
        pieChart.setData(pieChartData);
        return pieChart;
    }
}
