package bll.gloader;

import bll.FileReader;
import com.google.common.collect.Iterables;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.List;

public class LineChartLoader implements IChartLoader{
    private FileReader fileReader = new FileReader();

    /*
        This method is responsible for loading data and converting it into a Line Chart.
        Series name is set to static for now. And it only works if all necessary requirements
        are met. Those are providing a path to csv file in which data is separated by column. And
        providing two columns name that will be used as a foundation of creating the Line Chart.
     */
    @Override
    public Node loadChart(String path, String nameColumn, String dataColumn) {
        List<String> data = fileReader.loadData(path);
        int[] dataPosition = fileReader.getDataPosition(data,nameColumn,dataColumn);
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel(nameColumn);
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel(dataColumn);
        LineChart lineChart = new LineChart(xAxis, yAxis);
        XYChart.Series dataSeries = new XYChart.Series();
        dataSeries.setName("Monthly production");
        for (Object currentData: Iterables.skip(data, 1)){
            String currentLine = currentData.toString();
            String[] splitLine = currentLine.split(",");
            dataSeries.getData().add(new XYChart.Data( Integer.parseInt(splitLine[dataPosition[0]]), Integer.parseInt(splitLine[dataPosition[1]])));
        }
        lineChart.getData().add(dataSeries);
        return lineChart;
    }
}
