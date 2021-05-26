package be;

public class Report {
    private int id;
    private String title;
    private String description;

    private int departmentID;
    private String departmentName;

    public Report(int id, int departmentID, String departmentName, String title, String description) {
        this.id = id;
        this.departmentID = departmentID;
        this.departmentName = departmentName;
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getDepartmentID() {
        return departmentID;
    }
}
