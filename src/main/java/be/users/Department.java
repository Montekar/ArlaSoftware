package be.users;

public class Department extends User {

    public Department(int id, String username) {
        super(id, username);
    }

    @Override
    public String toString() {
        return getUsername();
    }
}
