package com.example.laborator_4.Services;


import com.example.laborator_4.Domain.User;
import com.example.laborator_4.Repository.Repository;
import com.example.laborator_4.utils.events.ChangeEventType;
import com.example.laborator_4.utils.events.UserEntityChangeEvent;
import com.example.laborator_4.utils.observer.Observable;
import com.example.laborator_4.utils.observer.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserService implements Observable<UserEntityChangeEvent> {
    private Repository<Long, User> repo;
    private List<Observer<UserEntityChangeEvent>> observers=new ArrayList<>();

    public UserService(Repository<Long, User> repo) {
        this.repo = repo;
    }


    public User addUser(User user) {
        if(repo.save(user).isEmpty()){
            UserEntityChangeEvent event = new UserEntityChangeEvent(ChangeEventType.ADD, user);
            notifyObservers(event);
            return null;
        }
        return user;
    }

    public User deleteUser(Long id){
        Optional<User> user=repo.delete(id);
        if (user.isPresent()) {
            notifyObservers(new UserEntityChangeEvent(ChangeEventType.DELETE, user.get()));
            return user.get();}
        return null;
    }

    public Iterable<User> getAll(){
        return repo.findAll();
    }

    public Iterable<User> getFriendsList(Long id){return repo.findFriendsList(id);}

    public Iterable<User> getFriendRequests(Long id){return repo.findFriendsRequests(id);}

    public Optional<User> getOne(String email){return repo.findOneByEmail(email);}

    public Iterable<User> findAll(String firstName, String lastName){return repo.findAllByName(firstName, lastName);}

    @Override
    public void addObserver(Observer<UserEntityChangeEvent> e) {
        observers.add(e);

    }

    @Override
    public void removeObserver(Observer<UserEntityChangeEvent> e) {
        //observers.remove(e);
    }

    @Override
    public void notifyObservers(UserEntityChangeEvent t) {

        observers.stream().forEach(x->x.update(t));
    }


}
