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
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

    /*
        Initialization of the menu class in which dynamic labels are set
        to the required data as the department, time
     */
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

    // Method to load the fxml file for the department menu
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

    // Logout functionality redirects to the Login Page
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

    // Method changes the screen size
    public void fullScreen(MouseEvent event) {
    }

    // Method hides the window
    public void minScreen(MouseEvent event) {
    }

    // Method for getting the text out of the fields and sending them to the database
    public void sendReport(ActionEvent actionEvent) {
        if ( !title.getText().isEmpty() && !description.getText().isEmpty()){
            adminManager.report(sessionModel.getUser().getUsername(), title.getText(), description.getText());
            title.setText("");
            description.setText("");
        }
    }

    // Method for closing the application
    public void closeScreen(MouseEvent event) {
            Stage stage = parentStage;
            stage.close();
            Platform.exit();
            System.exit(0);
    }
}
