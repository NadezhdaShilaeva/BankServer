package com.shilaeva.controllers;

import com.shilaeva.exceptions.AuthException;
import com.shilaeva.exceptions.UserException;
import com.shilaeva.handlers.query.Response;
import com.shilaeva.models.UserModel;
import com.shilaeva.services.UserService;

public class UserController {
    public UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public Response<?> signup(UserModel userModel) {
        try {
            userService.register(userModel.login(), userModel.password());

            return Response.created(String.format("User with login %s has been successfully registered",
                    userModel.login()));
        } catch (UserException e) {
            return Response.badRequest(e.getMessage());
        } catch (Exception e) {
            return Response.internalServerError(e.getMessage());
        }
    }

    public Response<?> signin(UserModel userModel) {
        try {
            String token = userService.login(userModel.login(), userModel.password());

            return Response.ok(String.format("Token: %s", token));
        } catch (AuthException e) {
            return Response.badRequest(e.getMessage());
        } catch (Exception e) {
            return Response.internalServerError(e.getMessage());
        }
    }
}
