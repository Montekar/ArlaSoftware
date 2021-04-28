package gui.controller;

import be.users.Admin;
import be.users.Client;
import be.users.User;
import bll.AuthenticationManager;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class LoginController {
    @FXML
    private ImageView closeButton;

    @FXML
    private JFXTextField usernameField;

    @FXML
    private JFXPasswordField passwordField;

    @FXML
    private Label message;

    @FXML
    private Button logInButton;

    private final Color redColor = Color.RED;
    private final Color orangeColor = Color.ORANGE;

    private final AuthenticationManager authenticationManager;

    public LoginController() {
        authenticationManager = new AuthenticationManager();
    }

    @FXML
    void closeWindow(MouseEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();

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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/AdminPage.fxml"));
            Parent root = fxmlLoader.load();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToClientPage(Stage stage, User user) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/AdminPage.fxml"));
            Parent root = fxmlLoader.load();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void login() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        message.setText("Please wait ...");
        message.setTextFill(orangeColor);

        if (!username.isBlank() && !password.isBlank()) {
            authenticateUser(username, password);
        } else if (username.isBlank() && password.isBlank()) {
            message.setText("Fill the blank fields");
            message.setTextFill(redColor);
        } else if (username.isEmpty()) {
            message.setText("Fill in the username");
            message.setTextFill(redColor);
        } else if (password.isEmpty()) {
            message.setText("Fill in the password");
            message.setTextFill(redColor);
        }
    }

    private void authenticateUser(String username, String password) {
        User user = authenticationManager.authenticateUser(username, password);
        if (user != null) {
            Stage stage = (Stage) logInButton.getScene().getWindow();
            if (user instanceof Admin) {
                goToAdminPage(stage, user);
            } else if (user instanceof Client) {
                goToClientPage(stage, user);
            }
        } else {
            message.setText("Account not found!");
            message.setTextFill(redColor);
        }
    }
}
