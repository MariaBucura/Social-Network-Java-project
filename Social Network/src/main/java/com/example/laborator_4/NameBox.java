package com.example.laborator_4;

import com.example.laborator_4.Domain.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class NameBox {
    private static User result = null;
    public static User display(String title){
        Button search_button = new Button("Search");
        TextField tf_last_name = new TextField();
        tf_last_name.setPromptText("Last Name");
        TextField tf_first_name = new TextField();
        tf_first_name.setPromptText("First Name");
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        search_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                result = new User(tf_first_name.getText(), tf_last_name.getText());
                window.close();
            }
        });
        HBox layout1 = new HBox(10);
        layout1.getChildren().addAll(tf_first_name, tf_last_name);
        VBox layout = new VBox(10);
        layout.getChildren().addAll(layout1, search_button);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
        return result;
    }
}
