package refresh;

import be.users.User;
import gui.model.ContentModel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Notification {

    private static Notification INSTANCE;
    private static final Integer startTime = 10;
    private Integer timeSeconds;
    private Timeline timeline;

    private ContentModel contentModel;
    private RefreshView refreshView;

    public static Notification getInstance(){
        if (INSTANCE == null){
            INSTANCE = new Notification();
        }
        return INSTANCE;
    }

    private Notification(){
        contentModel = ContentModel.getInstance();
        refreshView = RefreshView.getINSTANCE();
    }

    /*
        Method to display a dynamic alert with a timeline inside that
        is responsible for the counter like label. After the timeline reaches
        the selected point the view will be redirected and refreshed
     */
    public void displayAlert(User department, Stage stage, String message){
        if (timeline != null) {
            timeline.stop();
        }
        timeSeconds = startTime;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(message);
        alert.setHeaderText(null);
        alert.setContentText(timeSeconds.toString());
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1),
                    new EventHandler() {
                    @Override
                        public void handle(Event event) {
                            timeSeconds--;
                            alert.setContentText(timeSeconds.toString());
                            if (timeSeconds <= 0){
                                timeline.stop();
                                alert.close();

                                refreshView.updateView(department,stage);
                        }
                    }
                }));
        timeline.playFromStart();
        alert.showAndWait();
    }
}
