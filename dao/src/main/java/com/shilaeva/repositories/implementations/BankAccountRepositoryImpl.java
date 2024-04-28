package com.shilaeva.repositories.implementations;

import com.shilaeva.dao.BankAccountDao;
import com.shilaeva.entities.BankAccount;
import com.shilaeva.repositories.BankAccountRepository;

public class BankAccountRepositoryImpl implements BankAccountRepository {
    private final BankAccountDao bankAccountDao;

    public BankAccountRepositoryImpl(BankAccountDao bankAccountDao) {
        this.bankAccountDao = bankAccountDao;
        createTable();
    }

    @Override
    public BankAccount getById(long id) {
        try {
            return bankAccountDao.getBankAccountById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public BankAccount getByUserId(long userId) {
        try {
            return bankAccountDao.getBankAccountByUserId(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void create(BankAccount bankAccount) {
        try {
            bankAccountDao.insertBankAccount(bankAccount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(BankAccount bankAccount) {
        try {
            bankAccountDao.updateBankAccount(bankAccount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {
        try {
            bankAccountDao.deleteBankAccount(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createTable() {
        try {
            bankAccountDao.createTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
