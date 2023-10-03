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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String args[]){
        System.out.println("ok");
        System.out.println("Hello world!");
        System.out.println("ok");
        System.out.println("Reading data from file");
        String username="postgres";
        String pasword="IWuvJohn!1";
        String url="jdbc:postgresql://localhost:5432/social_network";
        Repository<Long, User> userFileRepository3 =
                new UserDBRepository(url,username, pasword,  new UserValidator());
        Repository<Tuple<Long, Long>, Friendship> friendshipRepository =
                new FriendshipDBRepository(url, username, pasword, new FriendshipValidator());
        Repository<Tuple<Long, Long>, FriendRequest> friendRequestRepository =
                new FriendRequestDBRepository(url, username, pasword, new FriendRequestValidator());

        UserService userService = new UserService(userFileRepository3);
        FriendshipService friendshipService = new FriendshipService(friendshipRepository);
        FriendRequestService friendRequestService = new FriendRequestService(friendRequestRepository);

        List<User> users = new ArrayList<>();
        userFileRepository3.findAll().forEach(x-> users.add(x));
        System.out.println(users);
        List<User> friends = new ArrayList<>();
        //userService.getFriendsList(31L).forEach(e->friends.add(e));
        //System.out.println(friends);
        //users.get(1).setFriends(friends);
        User user = new User("Marian", "Alex");
        userFileRepository3.findOneByEmail("ronsk5@gmail.com").ifPresent(x->System.out.println(x));
        Optional<User> opt = userService.getOne("ronsk5@gmail.com");
        System.out.println(opt.get().getEmail());
        System.out.println(opt.get().getPassword());
        userService.getOne("ronsk5@gmail.com").ifPresent(x->System.out.println(x));
        System.out.println("yes");

    }
}
