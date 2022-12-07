package com.lisitsin.repository.Impl;

import com.lisitsin.model.Event;
import com.lisitsin.repository.EventRepository;
import com.lisitsin.utils.MyHibernateSessionFactory;
import org.hibernate.Session;

import java.util.List;

public class EventRepositoryImpl implements EventRepository {
    private MyHibernateSessionFactory factory = MyHibernateSessionFactory.getInstance();

    @Override
    public Event getById(Long id) {
        Session session = factory.startSession();
        Event event = session.createQuery("select e from Event e " +
                "left join fetch e.myFiles " +
                "where e.id =: id", Event.class).setParameter("id", id).uniqueResult();
        session.close();
        return event;
    }

    @Override
    public List<Event> getAll() {
        Session session = factory.startSession();
        List<Event> events = session.createQuery("select e from Event e " +
                "left join fetch e.myFiles", Event.class).getResultList();
        session.close();
        return events;
    }

    @Override
    public Event save(Event event) {
        Session session = factory.startSession();
        try{
            session.beginTransaction();
            session.save(event);
            session.getTransaction().commit();
        } catch (Exception e){
            session.getTransaction().rollback();
        }
        session.close();
        return event;
    }

    @Override
    public Event update(Event event) {
        Session session = factory.startSession();
        try{
            session.beginTransaction();
            session.update(event);
            session.getTransaction().commit();
        } catch (Exception e){
            session.getTransaction().rollback();
        }
        session.close();
        return event;
    }

    @Override
    public void remove(Long id) {
        Session session = factory.startSession();
        try{
            session.beginTransaction();
            session.createQuery("delete from Event e where e.id =: id").setParameter("id", id).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e){
            session.getTransaction().rollback();
        }
        session.close();

    }
}
