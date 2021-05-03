package refresh;

import javafx.application.Platform;

import java.util.Timer;
import java.util.TimerTask;

public class RefreshTimer {
    private Notification notification;

   public RefreshTimer (){
        notification = new Notification();
    }


    public static void runTimer(){
        Timer timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Task complete");

            }
        };

        timer.scheduleAtFixedRate(timerTask,0,300000);
    }
}
