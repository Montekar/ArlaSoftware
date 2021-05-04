package be;

public class Department {
    private String name;
    private int id;

    public Department(int id, String name) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return getName();
    }
}
