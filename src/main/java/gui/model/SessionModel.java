package gui.model;

import be.users.User;

public class SessionModel {
    private static SessionModel INSTANCE;
    private User user;

    private SessionModel() {
        INSTANCE = null;
        user = null;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public static SessionModel getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SessionModel();
        }
        return INSTANCE;
    }
}
