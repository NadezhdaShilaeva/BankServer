package com.shilaeva;

import com.shilaeva.dao.implementations.BankAccountDaoImpl;
import com.shilaeva.dao.implementations.UserDaoImpl;
import com.shilaeva.entities.BankAccount;
import com.shilaeva.entities.User;
import com.shilaeva.repositories.BankAccountRepository;
import com.shilaeva.repositories.UserRepository;
import com.shilaeva.repositories.implementations.BankAccountRepositoryImpl;
import com.shilaeva.repositories.implementations.UserRepositoryImpl;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        UserDaoImpl userDao = new UserDaoImpl();
        BankAccountDaoImpl bankAccountDao = new BankAccountDaoImpl();
        System.out.println("create tables!!!");

        UserRepository userRepository = new UserRepositoryImpl(userDao);
        BankAccountRepository bankAccountRepository = new BankAccountRepositoryImpl(bankAccountDao);
        System.out.println("create repositories!!!");

        User user = new User("Nadezhda", "123456");
        //userRepository.create(user);

        User myUser = userRepository.getUserByLogin("Nadezhda");
        System.out.println(myUser.getId());
        System.out.println(myUser.getLogin());
        System.out.println(myUser.getPassword());

        BankAccount bankAccount = new BankAccount(myUser.getId());
        bankAccountRepository.create(bankAccount);

        BankAccount myBankAccount = bankAccountRepository.getByUserId(myUser.getId());
        System.out.println(myBankAccount.getId());
        System.out.println(myBankAccount.getUserId());
        System.out.println(myBankAccount.getAmount());

    }
}