package com.lisitsin.repository.Impl;

import com.lisitsin.model.MyFile;
import com.lisitsin.repository.FileRepository;
import com.lisitsin.utils.MyHibernateSessionFactory;
import org.hibernate.Session;

import java.util.List;

public class FileRepositoryImpl implements FileRepository {

    private MyHibernateSessionFactory factory = MyHibernateSessionFactory.getInstance();

    @Override
    public MyFile getById(Long id) {
        Session session = factory.startSession();
        MyFile myFile = session.get(MyFile.class, id);
        session.close();
        return myFile;
    }

    @Override
    public List<MyFile> getAll() {
        Session session = factory.startSession();
        List<MyFile> myFiles = session.createQuery("select f from MyFile f", MyFile.class).getResultList();
        session.close();
        return myFiles;
    }

    @Override
    public MyFile save(MyFile myFile) {
        Session session = factory.startSession();
        try{
            session.beginTransaction();
            session.save(myFile);
            session.getTransaction().commit();
        } catch (Exception e){
            session.getTransaction().rollback();
        }
        session.close();
        return myFile;
    }

    @Override
    public MyFile update(MyFile myFile) {
        Session session = factory.startSession();
        try{
            session.beginTransaction();
            session.update(myFile);
            session.getTransaction().commit();
        } catch (Exception e){
            session.getTransaction().rollback();
        }
        session.close();
        return myFile;
    }

    @Override
    public void remove(Long id) {
        Session session = factory.startSession();
        try{
            session.beginTransaction();
            session.createQuery("delete from MyFile f where f.id =: id").setParameter("id", id).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e){
            session.getTransaction().rollback();
        }
        session.close();
    }
}
