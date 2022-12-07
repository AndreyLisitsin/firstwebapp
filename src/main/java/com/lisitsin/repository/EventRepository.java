package com.lisitsin.repository;

import com.lisitsin.model.Event;

import java.util.List;

public interface EventRepository extends RepositoryTemplate<Event, Long>{
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
