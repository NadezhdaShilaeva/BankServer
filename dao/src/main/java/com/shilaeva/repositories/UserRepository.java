package com.shilaeva.repositories;

import com.shilaeva.entities.User;

public interface UserRepository {
    User getUserById(long id);
    User getUserByLogin(String login);
    void create(User user);
    void update(User user);
    void delete(long id);
}
