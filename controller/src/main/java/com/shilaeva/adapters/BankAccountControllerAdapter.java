package com.shilaeva.adapters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shilaeva.controllers.BankAccountController;
import com.shilaeva.exceptions.AdapterException;
import com.shilaeva.handlers.query.Request;
import com.shilaeva.handlers.query.Response;
import com.shilaeva.models.MoneyModel;
import com.shilaeva.models.TransferMoneyModel;

public class BankAccountControllerAdapter {
    private final BankAccountController bankAccountController;

    public BankAccountControllerAdapter(BankAccountController bankAccountController) {
        this.bankAccountController = bankAccountController;
    }

    public Response<String> createBankAccount(Request request) {
        return bankAccountController.createBankAccount(request.getPrincipal().login());
    }

    public Response<String> putMoneyToTheBankAccount(Request request) {
        try {
            MoneyModel moneyModel = new ObjectMapper().readValue(request.getBody(), MoneyModel.class);
            return bankAccountController.putMoneyToTheBankAccount(
                    request.getPrincipal().login(),
                    moneyModel);
        } catch (JsonProcessingException e) {
            throw AdapterException.errorParsingRequest(e.getMessage());
        }
    }

    public Response<String> withdrawMoneyFromTheBankAccount(Request request) {
        try {
            MoneyModel moneyModel = new ObjectMapper().readValue(request.getBody(), MoneyModel.class);
            return bankAccountController.withdrawMoneyFromTheBankAccount(
                    request.getPrincipal().login(),
                    moneyModel);
        } catch (JsonProcessingException e) {
            throw AdapterException.errorParsingRequest(e.getMessage());
        }
    }

    public Response<String> getBankAccountBalance(Request request) {
        return bankAccountController.getBankAccountBalance(request.getPrincipal().login());
    }

    public Response<String> transferMoneyBetweenBankAccounts(Request request) {
        try {
            TransferMoneyModel transferMoneyModel = new ObjectMapper()
                    .readValue(request.getBody(), TransferMoneyModel.class);
            return bankAccountController.transferMoneyBetweenBankAccounts(
                    request.getPrincipal().login(),
                    transferMoneyModel);
        } catch (JsonProcessingException e) {
            throw AdapterException.errorParsingRequest(e.getMessage());
        }
    }
}
