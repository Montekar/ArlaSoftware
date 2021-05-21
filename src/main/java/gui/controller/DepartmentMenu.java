package gui.controller;

import bll.AdminManager;
import gui.model.SessionModel;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class DepartmentMenu implements Initializable {

    @FXML
    TextField title;

    @FXML
    TextArea description;

    @FXML
    Label profile;

    @FXML
    Label time;

    private Stage parentStage;

    private SessionModel sessionModel;

    private AdminManager adminManager;

    public DepartmentMenu() {
        sessionModel = SessionModel.getInstance();
        adminManager = new AdminManager();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        profile.setText(sessionModel.getUser().getUsername());
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1),
                new EventHandler() {
                    @Override
                    public void handle(Event event) {
                        DateFormat outputFormat = new SimpleDateFormat("hh:mm:ss");
                        Date date = new Date();
                        time.setText(outputFormat.format(date));
                    }
                }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }

    public void showMenu(Stage departmentStage) {
        this.parentStage = departmentStage;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/DepartmentMenu.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logOut(MouseEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/LoginPage.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stageNew = new Stage();
            stageNew.setScene(new Scene(root));
            stageNew.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fullScreen(MouseEvent event) {
    }

    public void minScreen(MouseEvent event) {
    }

    public void sendReport(ActionEvent actionEvent) {
        if ( !title.getText().isEmpty() && !description.getText().isEmpty()){
            adminManager.report(sessionModel.getUser().getUsername(), title.getText(), description.getText());
            title.setText("");
            description.setText("");
        }
    }

    public void closeScreen(MouseEvent event) {
            Stage stage = parentStage;
            stage.close();
            Platform.exit();
            System.exit(0);
    }
}
