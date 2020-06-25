package com.epam.lab.repository;

import com.epam.lab.model.User;

public interface UserRepository {
    User findByName(String name);
    void register(User user);
}
