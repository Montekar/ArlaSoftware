package bll.vloader;

import be.View;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

import java.io.File;


public class VideoLoader implements IViewLoader{
    private boolean isPlaying = false;
    @Override
    public Node loadView(View view,boolean autoResizeEnabled) {
        Pane pane = new Pane();
        Media media = new Media(new File(view.getPath()).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);
        Slider timeSlider = new Slider();
        mediaPlayer.play();
        if (!autoResizeEnabled) {
            mediaView.setFitHeight(view.getHeight());
            mediaView.setFitWidth(view.getWidth());
        }
        VBox vBox = new VBox();

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
                timeSlider.setValue(newTime.toSeconds());
            }
        });


        timeSlider.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov)
            {
                if (timeSlider.isPressed()) { // It would set the time
                    // as specified by user by pressing
                    mediaPlayer.seek(mediaPlayer.getMedia().getDuration().multiply(timeSlider.getValue() / 100));
                }
            }
        });


        vBox.getChildren().addAll(mediaView,button, timeSlider);
        pane.getChildren().addAll(vBox);

        return pane;
    }
}
