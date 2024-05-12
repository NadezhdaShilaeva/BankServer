package com.shilaeva.decorators;

import com.shilaeva.services.BankAccountService;

import java.math.BigDecimal;
import java.util.logging.Logger;

public class LoggingBankAccountServiceDecorator implements BankAccountService {
    private final BankAccountService bankAccountService;
    private final Logger logger;

    public LoggingBankAccountServiceDecorator(BankAccountService bankAccountService, Logger logger) {
        this.bankAccountService = bankAccountService;
        this.logger = logger;
    }

    public void createBankAccount(String login) {
        bankAccountService.createBankAccount(login);

        logger.info(String.format("User \"%s\" created a bank account", login));
    }

    public BigDecimal getBankAccountBalance(String login) {
        BigDecimal balance = bankAccountService.getBankAccountBalance(login);

        logger.info(String.format("User \"%s\" balance is %.2f$", login, balance));

        return balance;
    }

    public void putMoneyToTheBankAccount(String login, BigDecimal amount) {
        bankAccountService.putMoneyToTheBankAccount(login, amount);

        logger.info(String.format("User \"%s\" put %.2f$", login, amount));
    }

    public void withdrawMoneyFromTheBankAccount(String login, BigDecimal amount) {
        bankAccountService.withdrawMoneyFromTheBankAccount(login, amount);

        logger.info(String.format("User \"%s\" withdraw %.2f$", login, amount));
    }

    public void transferMoneyBetweenBankAccounts(String fromLogin, String toLogin, BigDecimal amount) {
        bankAccountService.transferMoneyBetweenBankAccounts(fromLogin, toLogin, amount);

        logger.info(String.format("User \"%s\" was sent %.2f$ to the user \"%s\"", fromLogin, amount, toLogin));
    }
}
