package com.shilaeva.handlers;

import com.shilaeva.handlers.query.Principal;
import com.shilaeva.handlers.query.Query;
import com.shilaeva.handlers.query.Request;
import com.shilaeva.handlers.query.Response;
import com.shilaeva.sequrity.TokenService;

public class AuthHandler extends Handler {
    private final TokenService tokenService;

    public AuthHandler(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public void handleQuery(Query query) {
        System.out.println("Auth query");
        if (query instanceof Request request) {
            query = authenticateByToken(request);
        }

        if (nextHandler != null) {
            nextHandler.handleQuery(query);
        }
    }

    private Query authenticateByToken(Request request) {
        try {
            System.out.println("authenticateByToken");
            String authorization = request.getHeaders().get("Authorization");
            if (authorization == null) {
                return Response.notAuthorized("The authentication token was not provided");
            }

            String token = authorization.split("Bearer ", 2)[1];
            System.out.println(token);
            String userLogin = tokenService.getUserFromToken(token);
            request.setPrincipal(new Principal(userLogin));

            return request;
        } catch (Exception e) {
            return Response.notAuthorized(e.getMessage());
        }
    }
}
