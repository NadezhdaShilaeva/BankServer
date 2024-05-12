package com.shilaeva.services;

public interface UserService {
    void register(String login, String password);
    String login(String login, String password);
}
