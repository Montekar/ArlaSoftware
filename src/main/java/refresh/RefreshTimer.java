package refresh;

import be.users.User;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;

public class RefreshTimer {

    private Notification notification;
    private boolean first = true;
    private final String message = "The view will update now";

    private static RefreshTimer INSTANCE;

    public static RefreshTimer getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RefreshTimer();
        }
        return INSTANCE;
    }

    private RefreshTimer() {
        notification = Notification.getInstance();
    }

    /*
        Method that runs timer and redirects to Notification after certain time.
        Set to skip the first run and has a delay as well.
     */
    public void runTimer(User department, Stage stage) {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (!first) {
                    Platform.runLater(() -> {
                        notification.displayAlert(department, stage, message);
                        setFirst();
                    });
                } else {
                    first = false;
                }
            }
        };
        timer.schedule(timerTask, 30000, 300000);
    }

    // Set the boolean value to true if it is false
    public void setFirst(){
        if (this.first == false){
            this.first = true;
        }
    }
}
