package bll.gloader;

import bll.FileReader;
import com.google.common.collect.Iterables;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import java.util.List;

public class LineChartLoader implements IChartLoader{
    private FileReader fileReader = new FileReader();

    @Override
    public Node loadChart(String path, String nameColumn, String dataColumn) {
        List<String> data = fileReader.loadData("src/main/resources/mockFiles/Line_Chart_Data.csv");
        int[] dataPosition = fileReader.getDataPosition(data,nameColumn,dataColumn);
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel(nameColumn);
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel(dataColumn);
        LineChart lineChart = new LineChart(xAxis, yAxis);
        XYChart.Series dataSeries = new XYChart.Series();
        dataSeries.setName("Monthly production");
        System.out.println(dataPosition[0] + " " + dataPosition[1]);
        System.out.println(data.size());
        for (Object currentData: Iterables.skip(data, 1)){
            String currentLine = currentData.toString();
            String[] splitLine = currentLine.split(",");
            dataSeries.getData().add(new XYChart.Data( Integer.parseInt(splitLine[dataPosition[0]]), Integer.parseInt(splitLine[dataPosition[1]])));
        }
        lineChart.getData().add(dataSeries);
        return lineChart;
    }
}
