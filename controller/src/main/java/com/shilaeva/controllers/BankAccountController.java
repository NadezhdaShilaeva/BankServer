package com.shilaeva.controllers;

import com.shilaeva.handlers.query.Response;
import com.shilaeva.models.MoneyModel;
import com.shilaeva.models.TransferMoneyModel;
import com.shilaeva.services.BankAccountService;

import java.math.BigDecimal;

public class BankAccountController {
    private final BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    public Response<String> createBankAccount(String userLogin) {
        try {
            bankAccountService.createBankAccount(userLogin);

            return Response
                    .created(String.format("The bank account has been successfully created for the client %s",
                            userLogin));
        } catch (Exception e) {
            return Response.badRequest(e.getMessage());
        }
    }

    public Response<String> getBankAccountBalance(String userLogin) {
        try {
            BigDecimal balance = bankAccountService.getBankAccountBalance(userLogin);

            return Response.ok(String.format("The current bank account balance is %.2f", balance));
        } catch (Exception e) {
            return Response.badRequest(e.getMessage());
        }
    }

    public Response<String> putMoneyToTheBankAccount(String userLogin, MoneyModel moneyModel) {
        try {
            bankAccountService.putMoneyToTheBankAccount(userLogin, moneyModel.amount());

            return Response
                    .ok(String.format("The bank account of client %s has been successfully replenished by %.2f$",
                            userLogin, moneyModel.amount()));
        } catch (Exception e) {
            return Response.badRequest(e.getMessage());
        }
    }

    public Response<String> withdrawMoneyFromTheBankAccount(String userLogin, MoneyModel moneyModel) {
        try {
            bankAccountService.withdrawMoneyFromTheBankAccount(userLogin, moneyModel.amount());

            return Response
                    .ok(String.format("%.2f$ were successfully withdrawn from the bank account of client %s",
                            moneyModel.amount(), userLogin));
        } catch (Exception e) {
            return Response.badRequest(e.getMessage());
        }
    }

    public Response<String> transferMoneyBetweenBankAccounts(String userLogin, TransferMoneyModel transferMoneyModel) {
        try {
            bankAccountService.transferMoneyBetweenBankAccounts(
                    userLogin,
                    transferMoneyModel.to(),
                    transferMoneyModel.amount());

            return Response
                    .ok(String.format("%.2f$ were successfully transfer from the bank account of client %s to client %s",
                            transferMoneyModel.amount(), userLogin, transferMoneyModel.to()));
        } catch (Exception e) {
            return Response.badRequest(e.getMessage());
        }
    }
}
