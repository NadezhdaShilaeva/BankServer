package com.shilaeva.adapters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shilaeva.controllers.UserController;
import com.shilaeva.handlers.query.Request;
import com.shilaeva.handlers.query.Response;
import com.shilaeva.models.UserModel;

public class UserControllerAdapter {
    private final UserController userController;

    public UserControllerAdapter(UserController userController) {
        this.userController = userController;
    }

    public Response<?> signup(Request request) {
        try {
            UserModel userModel = new ObjectMapper().readValue(request.getBody(), UserModel.class);
            return userController.signup(userModel);
        } catch (JsonProcessingException e) {
            return Response.badRequest(e.getMessage());
        }
    }

    public Response<?> signin(Request request) {
        try {
            UserModel userModel = new ObjectMapper().readValue(request.getBody(), UserModel.class);
            return userController.signin(userModel);
        } catch (JsonProcessingException e) {
            return Response.badRequest(e.getMessage());
        }
    }
}
