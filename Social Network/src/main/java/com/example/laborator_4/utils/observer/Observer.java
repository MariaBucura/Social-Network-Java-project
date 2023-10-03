package com.example.laborator_4.utils.observer;

import com.example.laborator_4.utils.events.Event;

public interface Observer<E extends Event> {
    void update(E e);
}
