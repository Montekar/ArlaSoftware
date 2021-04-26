package be.users;

public class Users {
    private String username;
    private int id;

    public Users(String departmentName, int id) {
        this.username = departmentName;
        this.id = id;
    }

    public String getDepartmentName() {
        return username;
    }

    public void setDepartmentName(String departmentName) {
        this.username = departmentName;
    }

    public int getId() {
        return id;
    }

}
