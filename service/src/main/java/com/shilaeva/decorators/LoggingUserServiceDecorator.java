package com.shilaeva.decorators;

import com.shilaeva.services.UserService;

import java.util.logging.Logger;

public class LoggingUserServiceDecorator implements UserService {
    private final UserService userService;
    private final Logger logger;

    public LoggingUserServiceDecorator(UserService userService, Logger logger) {
        this.userService = userService;
        this.logger = logger;
    }

    public void register(String login, String password) {
        userService.register(login, password);

        logger.info(String.format("User \"%s\" was registered", login));
    }

    public String login(String login, String password) {
        String token = userService.login(login, password);

        logger.info(String.format("User \"%s\" was authenticated", login));

        return token;
    }
}
