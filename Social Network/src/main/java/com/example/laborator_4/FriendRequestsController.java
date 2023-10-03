package com.example.laborator_4;

import com.example.laborator_4.Domain.FriendRequest;
import com.example.laborator_4.Domain.Friendship;
import com.example.laborator_4.Domain.Tuple;
import com.example.laborator_4.Domain.User;
import com.example.laborator_4.Domain.Validators.FriendRequestValidator;
import com.example.laborator_4.Domain.Validators.FriendshipValidator;
import com.example.laborator_4.Domain.Validators.UserValidator;
import com.example.laborator_4.Repository.Repository;
import com.example.laborator_4.Repository.dbrepo.FriendRequestDBRepository;
import com.example.laborator_4.Repository.dbrepo.FriendshipDBRepository;
import com.example.laborator_4.Repository.dbrepo.UserDBRepository;
import com.example.laborator_4.Services.FriendRequestService;
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

public class FriendRequestsController implements Initializable {
    private String firstName;

    private String lastName;

    private String email;


    @FXML
    private Button back_button;

    @FXML
    private Button accept_button;

    @FXML
    private Button remove_button;

    @FXML
    private TableView<User> friend_request_table;

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
                DBUtils.changeScene(event, "logged-in.fxml", "Welcome!",email, firstName, lastName, null, null,  null,800, 520);
            }
        });
        remove_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                removeRequest();
            }
        });
        accept_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                acceptRequest();
            }
        });
    }

    private void removeRequest(){
        Repository<Long, User> userRepository = new UserDBRepository("jdbc:postgresql://localhost:5432/social_network",
                "postgres", "IWuvJohn!1", new UserValidator());
        UserService userService = new UserService(userRepository);
        Repository<Tuple<Long, Long>, FriendRequest> friendrequestRepository = new FriendRequestDBRepository("jdbc:postgresql://localhost:5432/social_network",
                "postgres", "IWuvJohn!1", new FriendRequestValidator());
        FriendRequestService friendRequestService = new FriendRequestService(friendrequestRepository);
        ObservableList<User> users, selectedUser;
        users = friend_request_table.getItems();
        selectedUser = friend_request_table.getSelectionModel().getSelectedItems();
        User loggedUser = userService.getOne(email).get();
        selectedUser.forEach(e->{
            users.remove(e);
            Tuple<Long, Long> id = new Tuple<>(e.getId(), loggedUser.getId());
            friendRequestService.deleteFriendRequest(id);
        });
    }

    private void acceptRequest(){
        Repository<Long, User> userRepository = new UserDBRepository("jdbc:postgresql://localhost:5432/social_network",
                "postgres", "IWuvJohn!1", new UserValidator());
        UserService userService = new UserService(userRepository);
        Repository<Tuple<Long, Long>, Friendship> friendshipRepository = new FriendshipDBRepository("jdbc:postgresql://localhost:5432/social_network",
                "postgres", "IWuvJohn!1", new FriendshipValidator());
        FriendshipService friendshipService = new FriendshipService(friendshipRepository);
        Repository<Tuple<Long, Long>, FriendRequest> friendrequestRepository = new FriendRequestDBRepository("jdbc:postgresql://localhost:5432/social_network",
                "postgres", "IWuvJohn!1", new FriendRequestValidator());
        FriendRequestService friendRequestService = new FriendRequestService(friendrequestRepository);
        ObservableList<User> users, selectedUser;
        users = friend_request_table.getItems();
        selectedUser = friend_request_table.getSelectionModel().getSelectedItems();
        User loggedUser = userService.getOne(email).get();
        selectedUser.forEach(e->{
            users.remove(e);
            Tuple<Long, Long> id = new Tuple<>(e.getId(), loggedUser.getId());
            friendRequestService.deleteFriendRequest(id);
            Friendship new_friendship = new Friendship();
            new_friendship.setId(id);
            friendshipService.addFriendship(new_friendship);
        });
    }

    public void setInfo(ObservableList<User> requests, String email, String firstName, String lastName){
        friend_request_table.setItems(requests);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
