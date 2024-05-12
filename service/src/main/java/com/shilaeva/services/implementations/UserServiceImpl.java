package com.shilaeva.services.implementations;

import com.shilaeva.entities.User;
import com.shilaeva.exceptions.AuthException;
import com.shilaeva.exceptions.UserException;
import com.shilaeva.repositories.UserRepository;
import com.shilaeva.sequrity.TokenService;
import com.shilaeva.services.UserService;
import org.mindrot.jbcrypt.BCrypt;

public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    TokenService tokenService;

    public UserServiceImpl(UserRepository userRepository, TokenService tokenService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    public void register(String login, String password) {
        System.out.println(userRepository.getUserByLogin(login));
        if (userRepository.getUserByLogin(login) != null) {
            throw UserException.userAlreadyExists(login);
        }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        User user = new User(login, hashedPassword);
        userRepository.create(user);
    }

    public String login(String login, String password) {
        User user = userRepository.getUserByLogin(login);

        if (user == null || !BCrypt.checkpw(password, user.getPassword())) {
            throw AuthException.userDataIsNotCorrect();
        }

        return tokenService.generateToken(login);
    }
}
