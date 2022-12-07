package com.lisitsin.services.impl;

import com.lisitsin.model.User;
import com.lisitsin.repository.UserRepository;
import com.lisitsin.services.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getById(Long id) {
        return userRepository.getById(id);
    }

    @Override
    public List<User> getAll() {
        return userRepository.getAll();
    }

    @Override
    public User save(User user) {
        if ((user.getFirstname() != null && !user.getFirstname().trim().isEmpty()) &&
                (user.getLastname() != null && !user.getLastname().trim().isEmpty())){
            return userRepository.save(user);
        }
        return null;
    }

    @Override
    public User update(User user) {
        if ((user.getFirstname() != null && !user.getFirstname().trim().isEmpty()) &&
                (user.getLastname() != null && !user.getLastname().trim().isEmpty())){
            return userRepository.update(user);
        }
        return null;
    }

    @Override
    public void remove(Long id) {
        userRepository.remove(id);
    }
}
