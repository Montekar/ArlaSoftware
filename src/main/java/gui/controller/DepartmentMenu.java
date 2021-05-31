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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class DepartmentMenu implements Initializable {
    @FXML
    AnchorPane menu;

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


    /*
        Initialization of the menu class in which dynamic labels are set
        to the required data as the department, time
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sessionModel = SessionModel.getInstance();
        adminManager = new AdminManager();
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

    // Sets up the parent stage
    public void setParentStage(Stage parentStage){
        this.parentStage = parentStage;
    }

    // Logout functionality redirects to the Login Page
    public void logOut(MouseEvent event) {
        Stage stage = (Stage) this.menu.getScene().getWindow();
        stage.close();
        this.parentStage.close();
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
        parentStage.setFullScreen(true);
    }

    // Method hides the window
    public void minScreen(MouseEvent event) {
        parentStage.setIconified(true);
    }

    // Method for getting the text out of the fields and sending them to the database
    public void sendReport(ActionEvent actionEvent) {
        if ( !title.getText().isEmpty() && !description.getText().isEmpty()){
            adminManager.report(sessionModel.getUser().getId(), title.getText(), description.getText());
            title.setText("");
            description.setText("");
        }
    }

    // Method for closing the application
    public void closeScreen(MouseEvent event) {
           Platform.exit();
           System.exit(0);
    }
}
