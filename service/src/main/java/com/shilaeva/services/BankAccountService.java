package com.shilaeva.services;

import java.math.BigDecimal;

public interface BankAccountService {
    void createBankAccount(String login);
    BigDecimal getBankAccountBalance(String login);
    void putMoneyToTheBankAccount(String login, BigDecimal amount);
    void withdrawMoneyFromTheBankAccount(String login, BigDecimal amount);
    void transferMoneyBetweenBankAccounts(String fromLogin, String toLogin, BigDecimal amount);
}
