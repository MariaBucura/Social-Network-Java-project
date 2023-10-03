package com.example.laborator_4.Domain;

import java.time.LocalDateTime;

public class FriendRequest extends Entity<Tuple<Long,Long>>{
    LocalDateTime date;


    public FriendRequest() {
    }

    /**
     *
     * @return the date when the friend request was sent
     */
    public LocalDateTime getDate() {
        return date;
    }
    public void seDate(LocalDateTime dateTime){this.date = dateTime;}
}
