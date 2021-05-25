package be;

import bll.ChartType;

public class ChartView extends View{

    private String nameColumn;
    private String dataColumn;
    private ChartType chartType;

    // Chart View is an extended View class related to chart loaders as they require more parameters

    public ChartView(int departmentID, int column, int row, int width, int height, String path, String title, String nameColumn, String dataColumn, ChartType chartType) {
        super(departmentID,  column, row, width, height, path, title);
        this.nameColumn = nameColumn;
        this.dataColumn = dataColumn;
        this.chartType = chartType;
    }

    public String getNameColumn() {
        return nameColumn;
    }

    public String getDataColumn() {
        return dataColumn;
    }

    public ChartType getChartType() {
        return chartType;
    }
}
