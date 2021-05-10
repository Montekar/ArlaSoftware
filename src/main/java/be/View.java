package be;

public class View {
    private String title;
    private String path;
    private int column;
    private int row;
    private int id;

    public View(int departmentID, int column, int row, String path,String title) {
        this.path = path;
        this.title = title;
        this.column = column;
        this.row = row;
        this.id = departmentID;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public int getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public String getTitle() {
        return title;
    }
}
