package be.users;

import be.Department;

public class Client extends User {
    private Department department;

    public Client(int id, String username) {
        super(id, username);
    }

    public Department getDepartment() {
        return department;
    }
}
