package com.lisitsin.utils;

import com.lisitsin.model.Event;
import com.lisitsin.model.MyFile;
import com.lisitsin.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class MyHibernateSessionFactory {
    private static MyHibernateSessionFactory factory = new MyHibernateSessionFactory();
    private static SessionFactory sessionFactory = new Configuration()
            .addAnnotatedClass(User.class)
            .addAnnotatedClass(Event.class)
            .addAnnotatedClass(MyFile.class)
            .buildSessionFactory();

    private MyHibernateSessionFactory(){}

    public Session startSession(){
        return sessionFactory.openSession();
    }

    public static MyHibernateSessionFactory getInstance(){
        return factory;
    }
}
