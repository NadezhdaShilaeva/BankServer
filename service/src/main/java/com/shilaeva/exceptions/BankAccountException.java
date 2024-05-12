package com.shilaeva.exceptions;

import java.math.BigDecimal;

public class BankAccountException extends RuntimeException {
    private BankAccountException(String message) {
        super(message);
    }

    public static BankAccountException userAlreadyHasBankAccount(String userLogin) {
        return new BankAccountException(String.format("The client %s already has a bank account", userLogin));
    }

    public static BankAccountException userHasNotBankAccount(String userLogin) {
        return new BankAccountException(String.format("The user %s does not have a bank account yet", userLogin));
    }

    public static BankAccountException notEnoughMoney(long bankAccountId) {
        return new BankAccountException(
                String.format("There are not enough funds in the bank account with id %d", bankAccountId));
    }
}
