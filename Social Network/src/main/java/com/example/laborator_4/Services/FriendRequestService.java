package com.example.laborator_4.Services;


import com.example.laborator_4.Domain.FriendRequest;
import com.example.laborator_4.Domain.Tuple;
import com.example.laborator_4.Repository.Repository;
import com.example.laborator_4.utils.events.ChangeEventType;
import com.example.laborator_4.utils.events.FriendRequestEntityChangeEvent;
import com.example.laborator_4.utils.observer.Observable;
import com.example.laborator_4.utils.observer.Observer;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

public class FriendRequestService implements Observable<FriendRequestEntityChangeEvent> {
    private Repository<Tuple<Long, Long>, FriendRequest> repo;
    private List<Observer<FriendRequestEntityChangeEvent>> observers=new ArrayList<>();

    public FriendRequestService(Repository<Tuple<Long, Long>, FriendRequest> repo) {
        this.repo = repo;
    }


    public FriendRequest addFriendRequest(FriendRequest friendRequest) {
        if(repo.save(friendRequest).isEmpty()){
            FriendRequestEntityChangeEvent event = new FriendRequestEntityChangeEvent(ChangeEventType.ADD, friendRequest);
            notifyObservers(event);
            return null;
        }
        return friendRequest;
    }

    public FriendRequest deleteFriendRequest(Tuple<Long, Long> id){
        Optional<FriendRequest> friendRequest=repo.delete(id);
        if (friendRequest.isPresent()) {
            notifyObservers(new FriendRequestEntityChangeEvent(ChangeEventType.DELETE, friendRequest.get()));
            return friendRequest.get();}
        return null;
    }

    public Iterable<FriendRequest> getAll(){
        return repo.findAll();
    }

    public Optional<FriendRequest> findOne(Tuple<Long, Long> id){return repo.findOne(id);}



    @Override
    public void addObserver(Observer<FriendRequestEntityChangeEvent> e) {
        observers.add(e);

    }

    @Override
    public void removeObserver(Observer<FriendRequestEntityChangeEvent> e) {
        //observers.remove(e);
    }

    @Override
    public void notifyObservers(FriendRequestEntityChangeEvent t) {

        observers.stream().forEach(x->x.update(t));
    }
}
