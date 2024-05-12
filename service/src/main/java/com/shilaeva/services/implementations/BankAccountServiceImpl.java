package com.shilaeva.services.implementations;

import com.shilaeva.entities.BankAccount;
import com.shilaeva.entities.User;
import com.shilaeva.exceptions.BankAccountException;
import com.shilaeva.repositories.BankAccountRepository;
import com.shilaeva.repositories.UserRepository;
import com.shilaeva.services.BankAccountService;

import java.math.BigDecimal;

public class BankAccountServiceImpl implements BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;

    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository, UserRepository userRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.userRepository = userRepository;
    }

    public void createBankAccount(String login) {
        User user = userRepository.getUserByLogin(login);

        checkIfPossibleToCreateBankAccount(user);

        BankAccount bankAccount = new BankAccount(user.getId());
        bankAccountRepository.create(bankAccount);
    }

    public BigDecimal getBankAccountBalance(String login) {
        BankAccount bankAccount = getBankAccountByUserLogin(login);

        return bankAccount.getAmount();
    }

    public void putMoneyToTheBankAccount(String login, BigDecimal amount) {
        BankAccount bankAccount = getBankAccountByUserLogin(login);

        bankAccount.setAmount(bankAccount.getAmount().add(amount));
        bankAccountRepository.update(bankAccount);
    }

    public void withdrawMoneyFromTheBankAccount(String login, BigDecimal amount) {
        BankAccount bankAccount = getBankAccountByUserLogin(login);

        checkIfThereAreEnoughMoney(bankAccount, amount);

        bankAccount.setAmount(bankAccount.getAmount().subtract(amount));
        bankAccountRepository.update(bankAccount);
    }

    public void transferMoneyBetweenBankAccounts(String fromLogin, String toLogin, BigDecimal amount) {
        BankAccount fromBankAccount = getBankAccountByUserLogin(fromLogin);
        BankAccount toBankAccount = getBankAccountByUserLogin(toLogin);

        checkIfThereAreEnoughMoney(fromBankAccount, amount);

        fromBankAccount.setAmount(fromBankAccount.getAmount().subtract(amount));
        toBankAccount.setAmount(toBankAccount.getAmount().add(amount));

        bankAccountRepository.update(fromBankAccount);
        bankAccountRepository.update(toBankAccount);
    }

    private void checkIfPossibleToCreateBankAccount(User user) {
        if (bankAccountRepository.getByUserId(user.getId()) != null) {
            throw BankAccountException.userAlreadyHasBankAccount(user.getLogin());
        }
    }

    private void checkIfThereAreEnoughMoney(BankAccount bankAccount, BigDecimal amount) {
        if (bankAccount.getAmount().subtract(amount).compareTo(BigDecimal.ZERO) < 0) {
            throw BankAccountException.notEnoughMoney(bankAccount.getId());
        }
    }

    private BankAccount getBankAccountByUserLogin(String userLogin) {
        User user = userRepository.getUserByLogin(userLogin);
        BankAccount bankAccount = bankAccountRepository.getByUserId(user.getId());

        if (bankAccount == null) {
            throw BankAccountException.userHasNotBankAccount(userLogin);
        }

        return bankAccount;
    }
}
