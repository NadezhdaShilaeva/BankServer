package com.shilaeva.handlers;

import com.shilaeva.adapters.BankAccountControllerAdapter;
import com.shilaeva.adapters.UserControllerAdapter;
import com.shilaeva.handlers.query.Query;
import com.shilaeva.handlers.query.Request;
import com.shilaeva.handlers.query.Response;
import com.shilaeva.sequrity.TokenService;

public class MapperHandler extends Handler {
    private final UserControllerAdapter userController;
    private final BankAccountControllerAdapter bankAccountController;
    private final TokenService tokenService;

    public MapperHandler(UserControllerAdapter userController,
                         BankAccountControllerAdapter bankAccountController,
                         TokenService tokenService) {
        this.userController = userController;
        this.bankAccountController = bankAccountController;
        this.tokenService = tokenService;
    }

    @Override
    public void handleQuery(Query query) {
        if (query instanceof Request request) {
            query = resolveMethod(request);
        }

        if (nextHandler != null) {
            nextHandler.handleQuery(query);
        }
    }

    private Query resolveMethod(Request request) {
        InvokerHandler invokerHandler;
        System.out.println(request.getMethod());

        switch (request.getPath()) {
            case "/signup" -> {
                invokerHandler = new InvokerHandler(userController::signup);
            }
            case "/signin" -> {
                invokerHandler = new InvokerHandler(userController::signin);
            }
            case "/signout" -> {
                invokerHandler = new InvokerHandler(userController::signout);
            }
            case "/createBankAccount" -> {
                invokerHandler = new InvokerHandler(bankAccountController::createBankAccount);
            }
            case "/put" -> {
                invokerHandler = new InvokerHandler(bankAccountController::putMoneyToTheBankAccount);
            }
            case "/withdraw" -> {
                invokerHandler = new InvokerHandler(bankAccountController::withdrawMoneyFromTheBankAccount);
            }
            case "/money" -> {
                System.out.println("Mapper /money");
                if (request.getBody() == null) {
                    invokerHandler = new InvokerHandler(bankAccountController::getBankAccountBalance);
                } else {
                    invokerHandler = new InvokerHandler(bankAccountController::transferMoneyBetweenBankAccounts);
                }
            }
            default -> {
                System.out.println("default");
                return Response.notFound(String.format("Cannot %s %s", request.getMethod(), request.getPath()));
            }
        }

        switch (request.getPath()) {
            case "/signup", "/signin" -> {
                invokerHandler.setNextHandler(this.nextHandler);
                this.setNextHandler(invokerHandler);
            }
            default -> {
                System.out.println("Mapper auth");
                AuthHandler authHandler = new AuthHandler(tokenService);

                authHandler.setNextHandler(invokerHandler);
                invokerHandler.setNextHandler(this.nextHandler);
                this.setNextHandler(authHandler);
            }
        }

        return request;
    }
}
