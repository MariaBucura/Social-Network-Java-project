package com.example.laborator_4.Services;


import com.example.laborator_4.Domain.Friendship;
import com.example.laborator_4.Domain.Tuple;
import com.example.laborator_4.Repository.Repository;
import com.example.laborator_4.utils.events.ChangeEventType;
import com.example.laborator_4.utils.events.FriendshipEntityChangeEvent;
import com.example.laborator_4.utils.observer.Observable;
import com.example.laborator_4.utils.observer.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FriendshipService implements Observable<FriendshipEntityChangeEvent> {
    private Repository<Tuple<Long, Long>, Friendship> repo;
    private List<Observer<FriendshipEntityChangeEvent>> observers=new ArrayList<>();

    public FriendshipService(Repository<Tuple<Long, Long>, Friendship> repo) {
        this.repo = repo;
    }


    public Friendship addFriendship(Friendship friendship) {
        if(repo.save(friendship).isEmpty()){
            FriendshipEntityChangeEvent event = new FriendshipEntityChangeEvent(ChangeEventType.ADD, friendship);
            notifyObservers(event);
            return null;
        }
        return friendship;
    }

    public Friendship deleteFriendship(Tuple<Long, Long> id){
        Optional<Friendship> friendship=repo.delete(id);
        if (friendship.isPresent()) {
            notifyObservers(new FriendshipEntityChangeEvent(ChangeEventType.DELETE, friendship.get()));
            return friendship.get();}
        return null;
    }

    public Iterable<Friendship> getAll(){
        return repo.findAll();
    }

    public Optional<Friendship> findOne(Tuple<Long, Long> id){return repo.findOne(id);}



    @Override
    public void addObserver(Observer<FriendshipEntityChangeEvent> e) {
        observers.add(e);

    }

    @Override
    public void removeObserver(Observer<FriendshipEntityChangeEvent> e) {
        //observers.remove(e);
    }

    @Override
    public void notifyObservers(FriendshipEntityChangeEvent t) {

        observers.stream().forEach(x->x.update(t));
    }
}
