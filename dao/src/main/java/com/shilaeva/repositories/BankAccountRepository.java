package com.shilaeva.repositories;

import com.shilaeva.entities.BankAccount;

public interface BankAccountRepository {
    BankAccount getById(long id);
    BankAccount getByUserId(long userId);
    void create(BankAccount bankAccount);
    void update(BankAccount bankAccount);
    void delete(long id);
}
