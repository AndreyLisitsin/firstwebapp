package com.lisitsin.repository;

import java.util.List;

public interface RepositoryTemplate <T, ID>{

    T getById(ID id);
    List<T> getAll();
    T save (T t);
    T update(T t);
    void remove(ID id);
}
