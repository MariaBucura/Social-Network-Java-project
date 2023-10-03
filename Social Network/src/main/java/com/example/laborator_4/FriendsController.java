package com.example.laborator_4;

import com.example.laborator_4.Domain.Friendship;
import com.example.laborator_4.Domain.Tuple;
import com.example.laborator_4.Domain.User;
import com.example.laborator_4.Domain.Validators.FriendshipValidator;
import com.example.laborator_4.Domain.Validators.UserValidator;
import com.example.laborator_4.Repository.Repository;
import com.example.laborator_4.Repository.dbrepo.FriendshipDBRepository;
import com.example.laborator_4.Repository.dbrepo.UserDBRepository;
import com.example.laborator_4.Services.FriendshipService;
import com.example.laborator_4.Services.UserService;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class FriendsController implements Initializable {

    private String firstName;

    private String lastName;

    private String email;


    @FXML
    private Button back_button;

    @FXML
    private Button remove_button;

    @FXML
    private Button add_button;

    @FXML
    private TableView<User> friends_table;

    @FXML
    private TableColumn<User, Long> id_column;

    @FXML
    private TableColumn<User, String> firstname_column;

    @FXML
    private TableColumn<User, String> lastname_column;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        id_column.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstname_column.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastname_column.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        back_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println(firstName);
                System.out.println(lastName);
                DBUtils.changeScene(event, "logged-in.fxml", "Welcome!",email, firstName, lastName, null, null, null,800, 520);
            }
        });
        remove_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                removeFriend();
            }
        });
        add_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                User resultUser = NameBox.display("Set User");
                ObservableList<User> results = DBUtils.getNameResults(resultUser.getFirstName(), resultUser.getLastName());
                DBUtils.changeScene(event, "search-result.fxml", "Add Friend", email, firstName, lastName, null, null, results, 800, 520);
            }
        });
    }

    private void removeFriend(){
        Repository<Long, User> userRepository = new UserDBRepository("jdbc:postgresql://localhost:5432/social_network",
                "postgres", "IWuvJohn!1", new UserValidator());
        UserService userService = new UserService(userRepository);
        Repository<Tuple<Long, Long>, Friendship> friendshipRepository = new FriendshipDBRepository("jdbc:postgresql://localhost:5432/social_network",
                "postgres", "IWuvJohn!1", new FriendshipValidator());
        FriendshipService friendshipService = new FriendshipService(friendshipRepository);
        ObservableList<User> users, selectedUser;
        users = friends_table.getItems();
        selectedUser = friends_table.getSelectionModel().getSelectedItems();
        User loggedUser = userService.getOne(email).get();
        selectedUser.forEach(e->{
            users.remove(e);
            Tuple<Long, Long> id = new Tuple<>(e.getId(), loggedUser.getId());
            friendshipService.deleteFriendship(id);
        });
    }

    public void setInfo(ObservableList<User> friends, String email, String firstName, String lastName){
        friends_table.setItems(friends);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
