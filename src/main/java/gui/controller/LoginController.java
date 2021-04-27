package gui.controller;

import animatefx.animation.*;
import com.jfoenix.controls.JFXButton;
import gui.model.LoginModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private TextField usernameInput;

    @FXML
    private TextField passwordInput;

    @FXML
    private JFXButton loginButton;

    @FXML
    private Label errorMessage;

    @FXML
    private Text welcomeText;

    private LoginModel loginModel;

    private Color red = Color.RED;
    private Color green = Color.GREEN;

    public LoginController(){
        loginModel = new LoginModel();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        new JackInTheBox(welcomeText).play();
    }

    public void login(ActionEvent actionEvent) {
        errorMessage.setText("Please wait");
        errorMessage.setTextFill(green);
        if ((usernameInput.getText().isEmpty()) && (passwordInput.getText().isEmpty())) {
            errorMessage.setText("Fill in all fields");
            errorMessage.setTextFill(red);
            animateMessage();
        }else{
            if(!loginModel.authenticateUser(usernameInput.getText(),passwordInput.getText()).isEmpty()){
                goToView();
            }else{
                errorMessage.setText("Incorrect credintials");
                errorMessage.setTextFill(red);
                animateMessage();
            }
        }
    }

    private void goToView() {
        System.out.println("View");
    }

    public void buttonHover(MouseEvent mouseEvent) {
        new Pulse(loginButton).play();
    }

    public void animateMessage(){
        new Shake(errorMessage).setCycleCount(1).play();
    }

}
