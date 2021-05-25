package gui.controller;

import be.users.Admin;
import be.users.Department;
import be.users.User;
import bll.AuthenticationManager;
import bll.helper.HashingHelper;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import gui.model.SessionModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginPageController {

    @FXML
    private JFXTextField usernameField;

    @FXML
    private JFXPasswordField passwordField;

    @FXML
    private Label message;

    private final AuthenticationManager authenticationManager;
    private final SessionModel sessionModel = SessionModel.getInstance();

    private final Color colorRed = Color.RED;
    private final Color colorOrange = Color.ORANGE;

    public LoginPageController() {
        authenticationManager = new AuthenticationManager();
    }

    @FXML
    void closeWindow(ActionEvent event) {
        Stage stage = (Stage) message.getScene().getWindow();
        stage.close();
        Platform.exit();
        System.exit(0);
    }

    public void loginClickAction(ActionEvent actionEvent) {
        login();
    }

    @FXML
    void onEnter(KeyEvent enter) {
        if (enter.getCode().equals(KeyCode.ENTER)) {
            login();
        }
    }

    public void goToAdminPage(Stage stage, User user) {
        try {
            sessionModel.setUser(user);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/AdminPage.fxml"));
            Parent root = fxmlLoader.load();
            stage.setScene(new Scene(root));
            stage.setFullScreen(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToClientPage(Stage stage, User user) {
        try {
            sessionModel.setUser(user);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/DepartmentView.fxml"));
            Parent root = fxmlLoader.load();
            stage.setScene(new Scene(root));
            DepartmentController deptCtrl = fxmlLoader.getController(); // Must be loaded AFTER fxmlloader.load()
            deptCtrl.setupListeners();
            stage.setFullScreen(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void login() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (!username.isBlank() && !password.isBlank()) {
            message.setText("Please wait ...");
            message.setTextFill(colorOrange);
            authenticateUser(username, password);
        } else if (username.isBlank() && password.isBlank()){
            message.setText("Fill the blank fields");
            message.setTextFill(colorRed);
        } else if (username.isEmpty()) {
            message.setText("Fill in the username");
            message.setTextFill(colorRed);
        } else if (password.isEmpty()) {
            message.setText("Fill in the password");
            message.setTextFill(colorRed);
        }
    }

    private void authenticateUser(String username, String password) {
        new Thread(() -> {
            User user = authenticationManager.getAuthenticatedUser(username, password);
            Platform.runLater(() -> {
                if (user != null) {
                    Stage stage = (Stage) message.getScene().getWindow();
                    if (user instanceof Admin) {
                        goToAdminPage(stage, user);
                    } else if (user instanceof Department) {
                        goToClientPage(stage, user);
                    }
                } else {
                    message.setText("Account not found!");
                    message.setTextFill(colorRed);
                }
            });
        }).start();
    }

    @FXML
    void onESCAPE(KeyEvent enter) throws IOException {
        if (enter.getCode().equals(KeyCode.ESCAPE)) {
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.close();
            Platform.exit();
            System.exit(0);
        }
    }
}
