package com.shilaeva.controllers;

import com.shilaeva.exceptions.BankAccountException;
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

    public Response<?> createBankAccount(String userLogin) {
        try {
            bankAccountService.createBankAccount(userLogin);

            return Response
                    .created(String.format("The bank account has been successfully created for the client %s",
                            userLogin));
        } catch (BankAccountException e) {
            return Response.badRequest(e.getMessage());
        } catch (Exception e) {
            return Response.internalServerError(e.getMessage());
        }
    }

    public Response<?> getBankAccountBalance(String userLogin) {
        try {
            BigDecimal balance = bankAccountService.getBankAccountBalance(userLogin);

            return Response.ok(String.format("The current bank account balance is %.2f$", balance));
        } catch (BankAccountException e) {
            return Response.badRequest(e.getMessage());
        } catch (Exception e) {
            return Response.internalServerError(e.getMessage());
        }
    }

    public Response<?> putMoneyToTheBankAccount(String userLogin, MoneyModel moneyModel) {
        try {
            bankAccountService.putMoneyToTheBankAccount(userLogin, moneyModel.amount());

            return Response
                    .ok(String.format("The bank account of client %s has been successfully replenished by %.2f$",
                            userLogin, moneyModel.amount()));
        } catch (BankAccountException e) {
            return Response.badRequest(e.getMessage());
        } catch (Exception e) {
            return Response.internalServerError(e.getMessage());
        }
    }

    public Response<?> withdrawMoneyFromTheBankAccount(String userLogin, MoneyModel moneyModel) {
        try {
            bankAccountService.withdrawMoneyFromTheBankAccount(userLogin, moneyModel.amount());

            return Response
                    .ok(String.format("%.2f$ were successfully withdrawn from the bank account of client %s",
                            moneyModel.amount(), userLogin));
        } catch (BankAccountException e) {
            return Response.badRequest(e.getMessage());
        } catch (Exception e) {
            return Response.internalServerError(e.getMessage());
        }
    }

    public Response<?> transferMoneyBetweenBankAccounts(String userLogin, TransferMoneyModel transferMoneyModel) {
        try {
            bankAccountService.transferMoneyBetweenBankAccounts(
                    userLogin,
                    transferMoneyModel.to(),
                    transferMoneyModel.amount());

            return Response
                    .ok(String.format("%.2f$ were successfully transfer from the bank account of client %s to client %s",
                            transferMoneyModel.amount(), userLogin, transferMoneyModel.to()));
        } catch (BankAccountException e) {
            return Response.badRequest(e.getMessage());
        } catch (Exception e) {
            return Response.internalServerError(e.getMessage());
        }
    }
}
