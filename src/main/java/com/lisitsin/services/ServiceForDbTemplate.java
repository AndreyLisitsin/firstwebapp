package com.lisitsin.services;

import java.util.List;

public interface ServiceForDbTemplate <T, ID>{

    T getById(ID id);

    List<T> getAll();

    T save (T t);

    T update(T t);

    void remove(ID id);
}
