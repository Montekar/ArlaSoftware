package gui.controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Alert {
    /**
     *
     * Popup for the program.
     *
     * @param alert window title.
     * @param message message inside the window.
     * @param ownerStage stage to make sure the popup is on top.
     */
    public static void displayAlert(String alert, String message, Stage ownerStage) {
        Stage window = new Stage();
        window.initOwner(ownerStage);

        window.initModality(Modality.APPLICATION_MODAL);
        window.toFront();
        window.setTitle(alert);

        Label label = new Label();
        label.setText(message);


        VBox layout = new VBox(30);
        layout.setPadding(new Insets(15,15,15,15));
        layout.getChildren().addAll(label);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);

        window.setScene(scene);

        window.showAndWait();

    }
}
