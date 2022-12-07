package com.lisitsin.services;

import com.lisitsin.model.Event;

import java.util.List;

public interface EventService extends ServiceForDbTemplate<Event, Long>{
    @Override
    Event getById(Long id);

    @Override
    List<Event> getAll();

    @Override
    Event save(Event event);

    @Override
    Event update(Event event);

    @Override
    void remove(Long id);
}
