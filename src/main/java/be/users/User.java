package be.users;

public class User {
    private String username;
    private int id;

    public User(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }
}
