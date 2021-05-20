package refresh;

import be.View;
import be.users.User;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

public class RefreshManager {

    private static  RefreshManager INSTANCE;
    private RefreshTimer refreshTimer;
    private ChangesListener changesListener;

    public static RefreshManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RefreshManager();
        }
        return INSTANCE;
    }
    private RefreshManager(){
        refreshTimer = RefreshTimer.getInstance();
        changesListener = ChangesListener.getInstance();
    }

    public void runTimer(User department, Stage stage){
        refreshTimer.runTimer(department, stage);
    }

    public void listenChanges(ObservableList<View> viewList, int departmentID){
        changesListener.listenForChanges(viewList,departmentID);
    }
}
