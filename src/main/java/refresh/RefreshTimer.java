package refresh;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.apache.poi.ss.formula.functions.T;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

public class RefreshTimer {
    private Notification notification;
    private boolean first = true;

   public RefreshTimer (){
        notification = new Notification();
    }
    private Thread thread = Thread.currentThread();

    public void runTimer(){
        Timer timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (!first) {
                    Platform.runLater(() -> {
                        notification.displayAlert();
                    });
                }else{
                    first = false;
                }
            }
        };
        timer.scheduleAtFixedRate(timerTask,0,300000);
    }
}
