package be;

public class View {
    private String title;
    private String path;
    private int column;
    private int row;
    private int id;
    private int width;
    private int height;

    public View(int departmentID, int column, int row, int width, int height, String path, String title) {
        this.path = path;
        this.title = title;
        this.column = column;
        this.row = row;
        this.width = width;
        this.height = height;
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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getPath() {
        return path;
    }

    public String getTitle() {
        return title;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
