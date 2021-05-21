package be;

public class ChartView extends View{

    String nameColumn;
    String dataColumn;

    public ChartView(int departmentID, int column, int row, String path, String title, String nameColumn, String dataColumn) {
        super(departmentID, column, row, path, title);
        this.nameColumn = nameColumn;
        this.dataColumn = dataColumn;
    }

    public String getNameColumn() {
        return nameColumn;
    }

    public String getDataColumn() {
        return dataColumn;
    }
}
