package com.shilaeva.handlers;

import com.shilaeva.handlers.query.Query;
import com.shilaeva.handlers.query.Request;
import com.shilaeva.handlers.query.Response;

import java.util.function.Function;

public class InvokerHandler extends Handler {
    private final Function<Request, Response<?>> controllerMethod;

    public InvokerHandler(Function<Request, Response<?>> controllerMethod) {
        this.controllerMethod = controllerMethod;
    }

    @Override
    public void handleQuery(Query query) {
        if (query instanceof Request request) {
            query = controllerMethod.apply(request);
        }

        if (nextHandler != null) {
            nextHandler.handleQuery(query);
        }
    }
}
