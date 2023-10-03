package com.example.laborator_4.utils.events;

import com.example.laborator_4.Domain.FriendRequest;

public class FriendRequestEntityChangeEvent implements Event{
    private ChangeEventType type;
    private FriendRequest data, oldData;

    public FriendRequestEntityChangeEvent(ChangeEventType type, FriendRequest data) {
        this.type = type;
        this.data = data;
    }
    public FriendRequestEntityChangeEvent(ChangeEventType type, FriendRequest data, FriendRequest oldData) {
        this.type = type;
        this.data = data;
        this.oldData=oldData;
    }

    public ChangeEventType getType() {
        return type;
    }

    public FriendRequest getData() {
        return data;
    }

    public FriendRequest getOldData() {
        return oldData;
    }
}
