package bll.gloader;

import bll.FileReader;
import com.google.common.collect.Iterables;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PieChartLoader implements IChartLoader{
    private FileReader fileReader = new FileReader();

    @Override
    public Node loadChart(String path, String nameColumn, String dataColumn) {
        PieChart pieChart = new PieChart();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        List<String> data = fileReader.loadData("src/main/resources/mockFiles/Pie_Chart_Data.csv");
        int dataPosition[] = fileReader.getDataPosition(data,nameColumn,dataColumn);
        for (int number: dataPosition){
            System.out.println(dataPosition.length);
            System.out.println(number);
        }
        for (Object currentData: Iterables.skip(data, 1)){
            String[] splitLine = currentData.toString().split(",");
            pieChartData.add(new PieChart.Data(splitLine[dataPosition[0] + 1],Double.parseDouble(splitLine[dataPosition[1] + 1])));
        }
        pieChart.setData(pieChartData);
        return pieChart;
    }
}
