package com.lisitsin.repository.Impl;

import com.lisitsin.model.User;
import com.lisitsin.repository.UserRepository;
import com.lisitsin.utils.MyHibernateSessionFactory;
import org.hibernate.Session;

import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    private MyHibernateSessionFactory factory = MyHibernateSessionFactory.getInstance();

    @Override
    public User getById(Long id) {
        Session session = factory.startSession();
        User user = session.createQuery("select u from User u " +
                "left join fetch u.events " +
                "where u.id =: id", User.class).setParameter("id", id).uniqueResult();
        session.close();
        return user;
    }

    @Override
    public List<User> getAll() {
        Session session = factory.startSession();
        List<User> users = session.createQuery("select u from User u " +
                "left join fetch u.events", User.class).getResultList();
        session.close();
        return users;
    }

    @Override
    public User save(User user) {
        try(Session session = factory.startSession()) {
            try {
                session.beginTransaction();
                session.save(user);
                session.getTransaction().commit();
                return user;
            } catch (Exception e) {
                session.getTransaction().rollback();
            }
        }
        return null;
    }

    @Override
    public User update(User user) {
        try(Session session = factory.startSession()) {
            try {
                session.beginTransaction();
                session.update(user);
                session.getTransaction().commit();
                return user;
            } catch (Exception e) {
                session.getTransaction().rollback();
            }
        }
        return null;
    }

    @Override
    public void remove(Long id) {
        try(Session session = factory.startSession()) {
            try {
                session.beginTransaction();
                session.createQuery("delete from User u where u.id =: id").setParameter("id", id).executeUpdate();
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
            }
        }
    }
}
