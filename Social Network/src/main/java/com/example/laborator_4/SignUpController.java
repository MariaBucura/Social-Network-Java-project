package com.example.laborator_4;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    @FXML
    private Button signin_button;

    @FXML
    private Button login_button;

    @FXML
    private TextField tf_first_name;

    @FXML
    private TextField tf_last_name;

    @FXML
    private TextField tf_password;

    @FXML
    private TextField tf_email_adress;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        signin_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!tf_email_adress.getText().trim().isEmpty() && !tf_password.getText().trim().isEmpty() && !tf_first_name.getText().trim().isEmpty() && !tf_last_name.getText().trim().isEmpty()){
                    DBUtils.signUpUser(event, tf_email_adress.getText(), tf_password.getText(), tf_first_name.getText(), tf_last_name.getText());
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("PLease fill up all information to sign up!");
                    alert.show();
                }
            }
        });

        login_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "hello-view.fxml", "Log in!", null, null, null, null, null, null,600, 400);
            }
        });
    }
}
