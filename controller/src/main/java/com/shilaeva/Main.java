package com.shilaeva;

import com.shilaeva.adapters.BankAccountControllerAdapter;
import com.shilaeva.adapters.UserControllerAdapter;
import com.shilaeva.controllers.BankAccountController;
import com.shilaeva.controllers.UserController;
import com.shilaeva.dao.BankAccountDao;
import com.shilaeva.dao.UserDao;
import com.shilaeva.dao.implementations.BankAccountDaoImpl;
import com.shilaeva.dao.implementations.UserDaoImpl;
import com.shilaeva.handlers.Handler;
import com.shilaeva.handlers.MapperHandler;
import com.shilaeva.repositories.BankAccountRepository;
import com.shilaeva.repositories.UserRepository;
import com.shilaeva.repositories.implementations.BankAccountRepositoryImpl;
import com.shilaeva.repositories.implementations.UserRepositoryImpl;
import com.shilaeva.sequrity.TokenService;
import com.shilaeva.sequrity.implementations.TokenServiceImpl;
import com.shilaeva.server.Server;
import com.shilaeva.services.BankAccountService;
import com.shilaeva.services.UserService;
import com.shilaeva.services.implementations.BankAccountServiceImpl;
import com.shilaeva.services.implementations.UserServiceImpl;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        UserDao userDao = new UserDaoImpl();
        BankAccountDao bankAccountDao = new BankAccountDaoImpl();

        UserRepository userRepository = new UserRepositoryImpl(userDao);
        BankAccountRepository bankAccountRepository = new BankAccountRepositoryImpl(bankAccountDao);

        TokenService tokenService = new TokenServiceImpl();
        UserService userService = new UserServiceImpl(userRepository, tokenService);
        BankAccountService bankAccountService = new BankAccountServiceImpl(bankAccountRepository, userRepository);

        UserController userController = new UserController(userService);
        BankAccountController bankAccountController = new BankAccountController(bankAccountService);

        UserControllerAdapter userControllerAdapter = new UserControllerAdapter(userController);
        BankAccountControllerAdapter bankAccountControllerAdapter = new BankAccountControllerAdapter(bankAccountController);

        Handler mapperHandler = new MapperHandler(userControllerAdapter, bankAccountControllerAdapter, tokenService);

        Server server = new Server(8080, mapperHandler);
        server.start();
    }
}