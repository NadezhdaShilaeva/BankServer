package com.shilaeva.dao;

import com.shilaeva.entities.BankAccount;

import java.sql.SQLException;

public interface BankAccountDao {
    BankAccount getBankAccountById(long id) throws SQLException;
    BankAccount getBankAccountByUserId(long userId) throws SQLException;
    void insertBankAccount(BankAccount bankAccount) throws SQLException;
    void updateBankAccount(BankAccount bankAccount) throws SQLException;
    void deleteBankAccount(long id) throws SQLException;
    void createTable() throws SQLException;
    void deleteTable() throws SQLException;
}
