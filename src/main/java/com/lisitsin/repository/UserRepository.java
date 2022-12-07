package com.lisitsin.repository;

import com.lisitsin.model.User;

import java.util.List;

public interface UserRepository extends RepositoryTemplate<User, Long>{

    @Override
    User getById(Long id);

    @Override
    List<User> getAll();

    @Override
    User save(User user);

    @Override
    User update(User user);

    @Override
    void remove(Long id);
}
