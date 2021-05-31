package bll.vloader;

import be.View;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

import java.io.File;


public class VideoLoader implements IViewLoader{
    private boolean isPlaying = false;
    @Override
    public Node loadView(View view,boolean autoResizeEnabled) {
        Media media = new Media(new File(view.getPath()).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);
        Slider timeSlider = new Slider();
        timeSlider.setMin(0);
        mediaPlayer.totalDurationProperty().addListener((observableValue, duration, t1) -> timeSlider.setMax(t1.toMillis()));

        mediaPlayer.play();

        Button button = new Button();
        button.setText("||");
        button.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                MediaPlayer.Status status = mediaPlayer.getStatus(); // To get the status of Player
                if (status == status.PLAYING) {
                    // If the status is Video playing
                    if (mediaPlayer.getCurrentTime().greaterThanOrEqualTo(mediaPlayer.getTotalDuration())) {
                        // If the player is at the end of video
                        mediaPlayer.seek(mediaPlayer.getStartTime()); // Restart the video
                        mediaPlayer.play();
                    }
                    else {
                        // Pausing the player
                        mediaPlayer.pause();
                        button.setText(">");
                    }
                } // If the video is stopped, halted or paused
                if (status == MediaPlayer.Status.HALTED || status == MediaPlayer.Status.STOPPED || status == MediaPlayer.Status.PAUSED) {
                    mediaPlayer.play(); // Start the video
                    button.setText("||");
                }
            }
        });

        mediaPlayer.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
            if (!timeSlider.isValueChanging()) {
                timeSlider.setValue(newTime.toMillis());
            }
        });

        timeSlider.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov)
            {
                if (timeSlider.isPressed()) { // It would set the time
                    // as specified by user by pressing
                    mediaPlayer.seek(Duration.millis(timeSlider.getValue()));
                }
            }
        });

        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(mediaView);

        mediaView.fitWidthProperty().bind(borderPane.widthProperty());




        HBox hBox = new HBox(button,timeSlider);
        hBox.setAlignment(Pos.CENTER);
        borderPane.setBottom(hBox);

        return borderPane;
    }
}
