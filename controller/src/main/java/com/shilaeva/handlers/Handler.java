package com.shilaeva.handlers;

import com.shilaeva.handlers.query.Query;

public abstract class Handler {
    protected Handler nextHandler;

    public void setNextHandler(Handler handler) {
        this.nextHandler = handler;
    }

    public abstract void handleQuery(Query query);
}
