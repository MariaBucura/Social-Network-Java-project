package com.example.laborator_4;

import com.example.laborator_4.Domain.User;
import com.example.laborator_4.Domain.Validators.UserValidator;
import com.example.laborator_4.Repository.Repository;
import com.example.laborator_4.Repository.dbrepo.UserDBRepository;
import com.example.laborator_4.Services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DBUtils {
    public static void changeScene(ActionEvent event, String fxmlFile, String Title, String email, String firstName, String lastName, ObservableList<User> friends, ObservableList<User> requests, ObservableList<User> results, double width, double height){
        Parent root = null;
        if(email != null && firstName != null && lastName != null && friends == null && requests == null && results == null){
            try{
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root = loader.load();
                LoggedInController loggedInController = loader.getController();
                loggedInController.setUserInformation(email, firstName, lastName);
            }catch(IOException e){
                e.printStackTrace();
            }
        }else{
            try{
                root = FXMLLoader.load(DBUtils.class.getResource(fxmlFile));
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        if(friends != null){
            try{
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root = loader.load();
                FriendsController friendsController = loader.getController();
                friendsController.setInfo(friends, email, firstName, lastName);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        if(requests != null){
            try{
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root = loader.load();
                FriendRequestsController requestsController = loader.getController();
                requestsController.setInfo(requests, email, firstName, lastName);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        if(results != null){
            try{
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root = loader.load();
                SearchResultController resultController = loader.getController();
                resultController.setInfo(results, email, firstName, lastName);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(Title);
        stage.setScene(new Scene(root, width, height));
        stage.show();
    }

    public static void signUpUser(ActionEvent event, String email, String password, String firstName, String lastName){
        Repository<Long, User> userRepository = new UserDBRepository("jdbc:postgresql://localhost:5432/social_network",
                "postgres", "IWuvJohn!1", new UserValidator());
        UserService userService = new UserService(userRepository);
        Optional<User> opt = userService.getOne(email);
        if(opt.isPresent()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("This email has already been used!");
            alert.show();
        }else{
            User user = new User(firstName, lastName, email, password);
            userService.addUser(user);
            changeScene(event, "logged-in.fxml", "Welcome!",email, firstName, lastName, null, null, null,800, 520);
        }
    }

    public static void logInUser(ActionEvent event, String email, String password){
        Repository<Long, User> userRepository = new UserDBRepository("jdbc:postgresql://localhost:5432/social_network",
                "postgres", "IWuvJohn!1", new UserValidator());
        UserService userService = new UserService(userRepository);
        Optional<User> opt = userService.getOne(email);
        if(opt.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Provided credentials are incorect!");
            alert.show();
        }else{
            String retrievePassword = opt.get().getPassword();
            if(retrievePassword.equals(password)){
                changeScene(event, "logged-in.fxml", "Welcome!",email, opt.get().getFirstName(), opt.get().getLastName(), null, null, null, 800, 520);
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided credentials are incorect!");
                alert.show();
            }
        }
    }

    public static ObservableList<User> getFriendsList(String email){
        Repository<Long, User> userRepository = new UserDBRepository("jdbc:postgresql://localhost:5432/social_network",
                "postgres", "IWuvJohn!1", new UserValidator());
        UserService userService = new UserService(userRepository);
        Optional<User> opt = userService.getOne(email);
        ObservableList<User> friends = FXCollections.observableArrayList();
        userService.getFriendsList(opt.get().getId()).forEach(e->friends.add(e));
        return friends;
    }

    public static ObservableList<User> getRequests(String email){
        Repository<Long, User> userRepository = new UserDBRepository("jdbc:postgresql://localhost:5432/social_network",
                "postgres", "IWuvJohn!1", new UserValidator());
        UserService userService = new UserService(userRepository);
        Optional<User> opt = userService.getOne(email);
        ObservableList<User> friends = FXCollections.observableArrayList();
        userService.getFriendRequests(opt.get().getId()).forEach(e->friends.add(e));
        return friends;
    }

    public static ObservableList<User> getNameResults(String firstName, String lastName){
        Repository<Long, User> userRepository = new UserDBRepository("jdbc:postgresql://localhost:5432/social_network",
                "postgres", "IWuvJohn!1", new UserValidator());
        UserService userService = new UserService(userRepository);
        ObservableList<User> results = FXCollections.observableArrayList();
        userService.findAll(firstName, lastName).forEach(e->results.add(e));
        return results;
    }


}
