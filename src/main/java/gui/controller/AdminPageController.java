package gui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AdminPageController {
    @FXML
    private Button dashboardButton;

    @FXML
    private MenuItem starbucksButton;

    @FXML
    private MenuItem cocioButton;

    @FXML
    private MenuItem truckButton;

    @FXML
    private Button settingsButton;

    @FXML
    private ImageView hideButton;

    @FXML
    private ImageView minMaxButton;

    @FXML
    private ImageView closeButton;

    public void hideWindow(MouseEvent mouseEvent) {
        Stage stage = (Stage) hideButton.getScene().getWindow();
        stage.close();
    }

    public void minMaxWindow(MouseEvent mouseEvent) {
        Stage stage = (Stage) minMaxButton.getScene().getWindow();
        stage.close();
    }

    public void closeWindow(MouseEvent mouseEvent) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
