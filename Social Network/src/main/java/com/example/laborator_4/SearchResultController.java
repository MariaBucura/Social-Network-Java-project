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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class SearchResultController implements Initializable {
    private String firstName;

    private String lastName;

    private String email;


    @FXML
    private Button back_button;

    @FXML
    private Button add_button;

    @FXML
    private TableView<User> result_table;

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
        add_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sendFriendRequest();
            }
        });
    }

    public void setInfo(ObservableList<User> result, String email, String firstName, String lastName){
        result_table.setItems(result);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public void sendFriendRequest(){
        Repository<Long, User> userRepository = new UserDBRepository("jdbc:postgresql://localhost:5432/social_network",
                "postgres", "IWuvJohn!1", new UserValidator());
        UserService userService = new UserService(userRepository);
        Repository<Tuple<Long, Long>, FriendRequest> friendrequestRepository = new FriendRequestDBRepository("jdbc:postgresql://localhost:5432/social_network",
                "postgres", "IWuvJohn!1", new FriendRequestValidator());
        FriendRequestService friendRequestService = new FriendRequestService(friendrequestRepository);
        Repository<Tuple<Long, Long>, Friendship> friendshipRepository = new FriendshipDBRepository("jdbc:postgresql://localhost:5432/social_network",
                "postgres", "IWuvJohn!1", new FriendshipValidator());
        FriendshipService friendshipService = new FriendshipService(friendshipRepository);
        Optional<User> opt = userService.getOne(email);
        ObservableList<User> selectedUser;
        selectedUser = result_table.getSelectionModel().getSelectedItems();
        User user = selectedUser.get(0);
        Tuple<Long, Long> id = new Tuple<>(opt.get().getId(), user.getId());
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setId(id);
        Friendship friendship = new Friendship();
        friendship.setId(id);
        if(friendshipService.findOne(id).isEmpty()){
            if(friendRequestService.findOne(id).isEmpty()){
                friendRequestService.addFriendRequest(friendRequest);
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("");
                alert.show();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("");
            alert.show();
        }
    }
}
