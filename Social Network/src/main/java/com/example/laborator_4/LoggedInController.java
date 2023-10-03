package com.example.laborator_4;

import com.example.laborator_4.Domain.User;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LoggedInController implements Initializable {

    private String first_name;

    private String last_name;

    private String email;

    @FXML
    private Button logout_button;

    @FXML
    private Button friends_button;

    @FXML
    private Button friend_requests_button;

    @FXML
    private Label label_welcome;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logout_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "hello-view.fxml", "Log in!", null, null, null, null, null, null,600, 400);
            }
        });

        friends_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ObservableList<User> friends = DBUtils.getFriendsList(email);
                System.out.println(friends);
                DBUtils.changeScene(event, "friends.fxml", "Friends", email, first_name, last_name, friends, null, null,800, 520);
            }
        });

        friend_requests_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ObservableList<User> requests = DBUtils.getRequests(email);
                DBUtils.changeScene(event, "friend-requests.fxml", "Friend requests", email, first_name, last_name, null, requests, null,800, 520);
            }
        });
    }

    public void setUserInformation(String email, String firstName, String lastName){
        this.first_name = firstName;
        this.last_name = lastName;
        this.email = email;
        label_welcome.setText(first_name +" "+ last_name);
    }
}
