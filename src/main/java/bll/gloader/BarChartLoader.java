package bll.gloader;

import bll.FileReader;
import com.google.common.collect.Iterables;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.List;

public class BarChartLoader implements IChartLoader{
    private FileReader fileReader = new FileReader();

    /*
        This class loads a Bar Chart based on the data provided and if it
        received all the parameters. Series name is set to static. When all data
        gets loaded it returns a Bar Chart
     */
    @Override
    public Node loadChart(String path, String nameColumn, String dataColumn) {
        List<String> data = fileReader.loadData(path);
        int[] dataPosition = fileReader.getDataPosition(data, nameColumn, dataColumn);
        CategoryAxis categoryAxis = new CategoryAxis();
        NumberAxis numberAxis = new NumberAxis();
        BarChart barChart = new BarChart(categoryAxis, numberAxis);
        XYChart.Series series = new XYChart.Series();
        series.setName("Year 2020");
        for (Object currentData: Iterables.skip(data, 1)){
            String currentLine = currentData.toString();
            String[] splitLine = currentLine.split(",");
            series.getData().add(new XYChart.Data<>( splitLine[dataPosition[0]], Integer.parseInt(splitLine[dataPosition[1]])));
        }
        barChart.getData().add(series);
        return barChart;
    }
}
