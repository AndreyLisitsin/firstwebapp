package com.lisitsin.services.impl;

import com.lisitsin.model.Event;
import com.lisitsin.repository.EventRepository;
import com.lisitsin.services.EventService;

import java.util.List;

public class EventServiceImpl implements EventService {

    private EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Event getById(Long id) {
        return eventRepository.getById(id);
    }

    @Override
    public List<Event> getAll() {
        return eventRepository.getAll();
    }

    @Override
    public Event save(Event event) {
        if(event.getUser() != null && (event.getName() != null && !event.getName().trim().isEmpty())){
            eventRepository.save(event);
            return event;
        }
        return null;
    }

    @Override
    public Event update(Event event) {
        if(event.getUser() != null && (event.getName() != null && !event.getName().trim().isEmpty())){
            eventRepository.update(event);
            return event;
        }
        return null;
    }

    @Override
    public void remove(Long id) {
        eventRepository.remove(id);
    }
}
