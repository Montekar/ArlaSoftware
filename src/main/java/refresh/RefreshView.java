package refresh;

import be.users.User;
import gui.controller.LoginPageController;
import javafx.stage.Stage;

public class RefreshView {

    private static RefreshView INSTANCE;
    private LoginPageController loginPageController;

    public static RefreshView getINSTANCE() {
        if (INSTANCE == null){
            INSTANCE = new RefreshView();
        }
        return INSTANCE;
    }

    private RefreshView(){
        loginPageController = new LoginPageController();
    }

    public void updateView(User department, Stage stage) {
        loginPageController.goToClientPage(stage, department);
    }
}
